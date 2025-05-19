package com.example.prompthub.security

import android.content.Context
import android.util.Log

class OpenSSLHelper {
    companion object {
        init {
            System.loadLibrary("native-lib")  // Your CMake target name
            System.loadLibrary("ssl")
            System.loadLibrary("crypto")
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

    external fun verifyApkHash(apkPath: String): Boolean

    fun isAppUntampered(context: Context): Boolean {
        return verifyApkHash(context.packageResourcePath)
    }
}