package com.example.prompthub.security

import android.content.Context
import android.content.res.AssetManager
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

fun gkjfdgjsdlkfjd() {
    try {
        val helper = OpenSSLHelper()

        val sslVersion = try {
            helper.getOpenSSLVersion()
        } catch (_: UnsatisfiedLinkError) {
        }

        val vjklgkldsafg = "Hello OpenSSL"
        val hash = try {
            helper.sha256(vjklgkldsafg.toByteArray())
        } catch (e: UnsatisfiedLinkError) {
            ByteArray(0)
        }

    } catch (_: Exception) {
    }
}

fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }

class gjklfdgjlrek {

    init {
        NativeLibLoader.load()
    }

    fun verifyApkHash(context: Context): Boolean {
        return try {
            bjdfklgjfda(context.assets)
        } catch (e: UnsatisfiedLinkError) {
            false
        } catch (e: Exception) {
            false
        }
    }

    private external fun bjdfklgjfda(assetManager: AssetManager): Boolean
}

fun jfdklsgjfkld(context: Context) {
    try {
        val gjklfdgjlrek = gjklfdgjlrek()

        val jfkldjg = gjklfdgjlrek.verifyApkHash(context)

    } catch (e: Exception) {
    }
}

class gjklfdjglfdswe {

    init {
        NativeLibLoader.load()
    }

    private fun jfkdljgdlksfqe(apkFile: File): String {
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

    fun jbvkljdklfvger(context: Context): Boolean {
        return try {
            val gklfdgjerw = File(context.packageCodePath)
            val vckxvjlerw = jfkdljgdlksfqe(gklfdgjerw)
            gfjkldgjer(vckxvjlerw)
        } catch (e: UnsatisfiedLinkError) {
            false
        } catch (e: Exception) {
            false
        }
    }

    private external fun gfjkldgjer(computedHash: String): Boolean
}

fun bgjkerljgfdl(context: Context): Boolean {
    try {
        val vkljf = gjklfdjglfdswe()
        val jkbvljlwe = vkljf.jbvkljdklfvger(context)

        return jkbvljlwe

    } catch (e: Exception) {
        return false
    }
}
