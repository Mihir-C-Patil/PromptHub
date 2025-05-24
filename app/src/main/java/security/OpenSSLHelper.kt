package com.example.prompthub.security

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import java.io.File
import java.security.MessageDigest
import java.util.zip.ZipFile

class OpenSSLHelper {
    companion object {
        init {
            System.loadLibrary("native-lib")  // Your CMake target name
        }
    }

    external fun getOpenSSLVersion(): String
    external fun sha256(input: ByteArray): ByteArray
}

fun logOpenSSLInfo() {
    try {
        val helper = OpenSSLHelper()

        // 1. Print OpenSSL version
        val sslVersion = helper.getOpenSSLVersion()
        Log.d("OpenSSL", "Version: $sslVersion")

        // 2. Print test hash
        val testString = "Hello OpenSSL"
        val hash = helper.sha256(testString.toByteArray())
        Log.d("OpenSSL", "SHA-256 of '$testString': ${hash.toHex()}")

    } catch (e: Exception) {
        Log.e("OpenSSL", "Error: ${e.message}")
    }
}

// Extension function for byte array to hex
fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }

class TamperCheck {
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    fun verifyApkHash(context: Context): Boolean {
        return try {
            nativeVerifyApkHash(context.assets)
        } catch (e: Exception) {
            false
        }
    }

    private external fun nativeVerifyApkHash(assetManager: AssetManager): Boolean
}

fun logTamperCheckInfo(context: Context) {
    try {
        val tamperCheck = TamperCheck()
        
        // Verify APK hash and log the result
        val isUntampered = tamperCheck.verifyApkHash(context)
        //Log.d("TamperCheck", "APK Hash Verification Result: ${if (isUntampered) "Untampered" else "Tampered"}")
        
    } catch (e: Exception) {
        //Log.e("TamperCheck", "Error during APK hash verification: ${e.message}")
    }
}

class TamperCheck2 {

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    // Computes SHA-256 of core APK files
    // Same hash computation as in build.gradle
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
        } catch (e: Exception) {
            Log.e("TamperCheck2", "APK verification failed", e)
            false
        }
    }

    // Calls native code to verify the hash securely
    private external fun verifyApkHash2(computedHash: String): Boolean
}

fun logTamperCheckInfo2(context: Context) : Boolean {
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
            // Take appropriate action (e.g., show warning, disable features, etc.)
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