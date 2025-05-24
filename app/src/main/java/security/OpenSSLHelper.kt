package com.example.prompthub.security

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import java.io.File
import java.security.MessageDigest
import java.util.zip.ZipFile

object NativeLibLoader {
    private var loaded = false

    fun load() {
        if (!loaded) {
            try {
                System.loadLibrary("native-lib")
                loaded = true
                Log.d("NativeLibLoader", "native-lib loaded successfully")
            } catch (e: UnsatisfiedLinkError) {
                Log.e("NativeLibLoader", "Failed to load native-lib", e)
            } catch (e: Exception) {
                Log.e("NativeLibLoader", "Unexpected error loading native-lib", e)
            }
        }
    }
}

class OpenSSLHelper {

    init {
        NativeLibLoader.load()
    }

    external fun getOpenSSLVersion(): String
    external fun sha256(input: ByteArray): ByteArray
}

fun logOpenSSLInfo() {
    try {
        val helper = OpenSSLHelper()

        val sslVersion = try {
            helper.getOpenSSLVersion()
        } catch (e: UnsatisfiedLinkError) {
            Log.e("OpenSSL", "Native lib not loaded or method missing", e)
            "Unknown"
        }

        Log.d("OpenSSL", "Version: $sslVersion")

        val testString = "Hello OpenSSL"
        val hash = try {
            helper.sha256(testString.toByteArray())
        } catch (e: UnsatisfiedLinkError) {
            Log.e("OpenSSL", "Native lib not loaded or method missing", e)
            ByteArray(0)
        }

        if (hash.isNotEmpty()) {
            Log.d("OpenSSL", "SHA-256 of '$testString': ${hash.toHex()}")
        } else {
            Log.w("OpenSSL", "SHA-256 computation skipped due to native lib issue")
        }

    } catch (e: Exception) {
        Log.e("OpenSSL", "Error: ${e.message}")
    }
}

fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }

class TamperCheck {

    init {
        NativeLibLoader.load()
    }

    fun verifyApkHash(context: Context): Boolean {
        return try {
            nativeVerifyApkHash(context.assets)
        } catch (e: UnsatisfiedLinkError) {
            Log.e("TamperCheck", "Native lib not loaded or method missing", e)
            false
        } catch (e: Exception) {
            Log.e("TamperCheck", "Error during APK hash verification", e)
            false
        }
    }

    private external fun nativeVerifyApkHash(assetManager: AssetManager): Boolean
}

fun logTamperCheckInfo(context: Context) {
    try {
        val tamperCheck = TamperCheck()

        val isUntampered = tamperCheck.verifyApkHash(context)

    } catch (e: Exception) {
    }
}

class TamperCheck2 {

    init {
        NativeLibLoader.load()
    }

    private fun computeBuildTimeHash(apkFile: File): String {
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

    fun verifyApkIntegrity(context: Context): Boolean {
        return try {
            val apkFile = File(context.packageCodePath)
            val runtimeHash = computeBuildTimeHash(apkFile)
            verifyApkHash2(runtimeHash)
        } catch (e: UnsatisfiedLinkError) {
            Log.e("TamperCheck2", "Native lib not loaded or method missing", e)
            false
        } catch (e: Exception) {
            Log.e("TamperCheck2", "APK verification failed", e)
            false
        }
    }

    private external fun verifyApkHash2(computedHash: String): Boolean
}

fun logTamperCheckInfo2(context: Context): Boolean {
    try {
        val helper = TamperCheck2()
        val isUntampered = helper.verifyApkIntegrity(context)

        Log.d("TamperCheck2", """
        APK Integrity Check Results:
        ---------------------------
        Verification Status: ${if (isUntampered) "Untampered" else "Tampered"}
        Verification Method: Core APK files hash check
        """.trimIndent())

        if (!isUntampered) {
            Log.w("TamperCheck2", "Security alert: APK integrity compromised!")
        }

        return isUntampered

    } catch (e: Exception) {
        Log.e("TamperCheck2", """
        APK Integrity Check Failed:
        --------------------------
        Error: ${e.message}
        Stacktrace: ${e.stackTraceToString()}
        """.trimIndent())
        return false
    }
}
