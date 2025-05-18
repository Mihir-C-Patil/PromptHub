import com.android.build.api.dsl.Packaging
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import org.gradle.api.tasks.Internal

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

abstract class PrintSigningCertSha256Task @Inject constructor(
    private val execOperations: ExecOperations
) : DefaultTask() {

    @TaskAction
    fun printSha256() {
        val signingConfig = project.extensions
            .findByName("android")
            ?.let { it as? com.android.build.gradle.AppExtension }
            ?.signingConfigs
            ?.findByName("release")

        if (signingConfig == null) {
            println("❌ Release signing config not found.")
            return
        }

        val keystoreFile = signingConfig.storeFile
        val keyAlias = signingConfig.keyAlias
        val keyPassword = signingConfig.storePassword

        if (keystoreFile == null || keyAlias == null || keyPassword == null) {
            println("❌ Keystore information is incomplete.")
            return
        }

        val outputStream = ByteArrayOutputStream()

        execOperations.exec {
            commandLine(
                "keytool", "-list", "-v",
                "-keystore", keystoreFile.absolutePath,
                "-alias", keyAlias,
                "-storepass", keyPassword
            )
            standardOutput = outputStream
        }

        val output = outputStream.toString()
        val regex = Regex("SHA256:\\s*([A-Fa-f0-9:]+)")
        val match = regex.find(output)

        if (match != null) {
            val sha256 = match.groupValues[1].replace(":", "").uppercase()
            println("\n✅ SHA-256 fingerprint: $sha256\n")
        } else {
            println("\n❌ Could not extract SHA-256\n")
        }
    }
}

tasks.register("printSigningCertSha256", PrintSigningCertSha256Task::class)