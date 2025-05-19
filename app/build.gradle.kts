import com.android.build.api.dsl.Packaging
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import org.gradle.api.Project
import org.gradle.api.file.RegularFile
import org.gradle.api.tasks.Exec
import java.io.ByteArrayOutputStream

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

    fun Packaging.() {
        resources.pickFirsts.add("**/libssl.so")
        resources.pickFirsts.add("**/libssl.so")
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
            storeFile = file("your-key.jks")
            storePassword = "your-store-password"
            keyAlias = "your-alias"
            keyPassword = "your-key-password"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    applicationVariants.all {
        val variant = this
        if (variant.buildType.name == "release") {
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
                        val hashStream = ByteArrayOutputStream()

                        project.exec {
                            if (System.getProperty("os.name", "").contains("Windows", true)) {
                                commandLine(
                                    "cmd", "/c",
                                    "certutil -hashfile \"${outputFile.absolutePath}\" SHA256 | find /v \"hashfile\" | find /v \"CertUtil\""
                                )
                            } else {
                                commandLine(
                                    "sh", "-c",
                                    "openssl dgst -sha256 \"${outputFile.absolutePath}\" | awk '{print \$2}'"
                                )
                            }
                            standardOutput = hashStream
                        }

                        // Process output to get just the hash
                        val rawOutput = hashStream.toString().trim()
                        val hash = if (System.getProperty("os.name", "").contains("Windows", true)) {
                            // For Windows: Take last line and remove any non-hex characters
                            rawOutput.lines().last()
                                .replace("[^a-fA-F0-9]".toRegex(), "")
                        } else {
                            // For Unix: Already clean output
                            rawOutput
                        }

                        headerDir.resolve("expected_hash.h").writeText("""
                            // Auto-generated - DO NOT EDIT
                            #pragma once
                            const char* EXPECTED_APK_HASH = "$hash";
                        """.trimIndent())

                        logger.lifecycle("Generated hash for ${apkFile.name}")
                    }
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

    // OkHttp dependency
    implementation(libs.okhttp)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.glide) // Use latest Glide version
    annotationProcessor(libs.compiler)  // Required for Glide's annotation processor

    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)

    // Coroutines for asynchronous operations
    implementation(libs.kotlinx.coroutines.android)

    // Image Loading (Coil)
    implementation(libs.coil)
}
