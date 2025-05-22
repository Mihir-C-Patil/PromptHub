package com.example.prompthub.utils

import com.example.prompthub.utils.base64
import android.util.Log
import java.nio.charset.StandardCharsets

object KeyLoader {

    init {
        System.loadLibrary("keyobf")
    }

    external fun f0()
    external fun f1()
    external fun f2()
    external fun f3()
    external fun f4()
    external fun f5()
    external fun f6()
    external fun f7()
    external fun f8()

    private external fun getObfuscatedAuthHeader(): String

    fun retrievePlaintextAuthHeader(): String? {
        return try {
            f0()
            f1()
            f2()
            f3()
            f4()
            f5()
            f6()
            f7()
            f8()

            val base64EncodedKey = getObfuscatedAuthHeader()

            if (base64EncodedKey.isEmpty()) {
                return null
            }
            if (base64EncodedKey.startsWith("Error:")) {
                return null
            }

            val decodedBytes = base64.decode(base64EncodedKey)
            val plaintextHeader = String(decodedBytes, StandardCharsets.UTF_8)

            plaintextHeader
        } catch (e: UnsatisfiedLinkError) {
            Log.e("KeyLoader", "Native method not found. Ensure library is loaded, method names match, and NDK is configured.", e)
            null
        }
        catch (e: Exception) {
            Log.e("KeyLoader", "An unexpected error occurred during key retrieval.", e)
            null
        }
    }
}