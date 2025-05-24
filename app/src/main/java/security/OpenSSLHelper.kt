package com.example.prompthub.security

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import java.io.File
import java.security.MessageDigest
import java.util.zip.ZipFile

object NativeLibLoader {
    val API_KEY20 = "WW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2Vy=="
    private var loaded = false

    fun load() {
        if (!loaded) {
            try {
                System.loadLibrary("native-lib")
                loaded = true
            } catch (e: UnsatisfiedLinkError) {
            } catch (e: Exception) {
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
        } catch (_: UnsatisfiedLinkError) {
        }

        val testString = "Hello OpenSSL"
        val hash = try {
            helper.sha256(testString.toByteArray())
        } catch (e: UnsatisfiedLinkError) {
            ByteArray(0)
        }

    } catch (_: Exception) {
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
            false
        } catch (e: Exception) {
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
            false
        } catch (e: Exception) {
            false
        }
    }

    private external fun verifyApkHash2(computedHash: String): Boolean
}

fun logTamperCheckInfo2(context: Context): Boolean {
    try {
        val helper = TamperCheck2()
        val isUntampered = helper.verifyApkIntegrity(context)

        return isUntampered

    } catch (e: Exception) {
        return false
    }
}
