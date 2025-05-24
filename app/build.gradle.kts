import com.android.build.api.dsl.Packaging
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import org.gradle.api.Project
import org.gradle.api.file.RegularFile
import org.gradle.api.tasks.Exec
import java.io.ByteArrayOutputStream
import java.security.MessageDigest
import java.util.zip.ZipFile

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.prompthub"
    compileSdk = 35

    ndkVersion = "25.1.8937393"

    defaultConfig {
        applicationId = "com.example.prompthub"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters.addAll(listOf("x86", "x86_64", "armeabi-v7a", "arm64-v8a"))
        }

    }

    packaging {
        resources {
            resources.pickFirsts.add("**/libssl.so")
            resources.pickFirsts.add("**/libcrypto.so")
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    buildFeatures {
        prefab = true
    }

    signingConfigs {
        create("release") {
            storeFile = file("my-release-key.jks")
            storePassword = "123456"
            keyAlias = "my-key-alias"
            keyPassword = "123456"
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
        }
    }

    buildTypes {


        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    fun computeApkHash(apkFile: File): String {
        val md = MessageDigest.getInstance("SHA-256")
        ZipFile(apkFile).use { zip ->
            zip.entries().toList()
                .filter { it.name.matches(Regex("classes.dex|resources.arsc|AndroidManifest.xml|res/.*")) }
                .sortedBy { it.name }
                .forEach { entry ->
                    zip.getInputStream(entry).use { input ->
                        val buffer = ByteArray(8192)
                        var bytesRead: Int
                        while (input.read(buffer).also { bytesRead = it } != -1) {
                            md.update(buffer, 0, bytesRead)
                        }
                    }
                }
        }
        return md.digest().joinToString("") { "%02x".format(it) }
    }

    applicationVariants.all {
        val variant = this
        variant.outputs.forEach { output ->
            val outputFile = output.outputFile
            if ((outputFile != null) && outputFile.name.endsWith(".apk")) {
                val assembleTask = variant.assembleProvider.get()
                assembleTask.doLast {
                    val apkFile = outputFile

                    if (!apkFile.exists()) {
                        logger.error("APK not found at: ${apkFile.absolutePath}")
                        return@doLast
                    }

                    val headerDir = projectDir.resolve("src/main/cpp/generated/").apply {
                        mkdirs()
                    }

                    val hash = computeApkHash(apkFile)

                    val parts = List(5) { i ->
                        val start = i * (hash.length / 5)
                        val end = if (i == 4) hash.length else (i + 1) * (hash.length / 5)
                        hash.substring(start, end)
                    }

                    headerDir.resolve("expected_hash.h").writeText(
                        """
                    // Auto-generated - DO NOT EDIT
                    #pragma once
                    #include "obfuscate.h"
                    
                    // Obfuscated hash parts
                    static std::array<std::string, 5> HASH_PARTS = {
                            OBF("${parts[0]}"),
                            OBF("${parts[1]}"),
                            OBF("${parts[2]}"),
                            OBF("${parts[3]}"),
                            OBF("${parts[4]}")
                    };
                    
                    // Runtime hash reconstruction
                    inline const char* getExpectedHash() {
                        static char fullHash[65] = {0}; // SHA-256 hash is 64 chars + '\0'
                        static bool initialized = false;
                    
                        if (!initialized) {
                            std::string result;
                            for (const auto& part : HASH_PARTS) {
                                result += part;
                            }
                            std::strncpy(fullHash, result.c_str(), sizeof(fullHash) - 1);
                            fullHash[64] = '\0'; // Ensure null-termination
                            initialized = true;
                        }
                    
                        return fullHash;
                    }
                    """.trimIndent()
                    )

                    logger.lifecycle("APK hash header generated at ${headerDir.resolve("expected_hash.h")}")
                }
            }
        }
    }
}


dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)


    implementation(libs.okhttp)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.glide)
    annotationProcessor(libs.compiler)

    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.coil)
}
