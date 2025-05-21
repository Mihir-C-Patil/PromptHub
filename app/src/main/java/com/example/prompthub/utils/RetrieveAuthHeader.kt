package com.example.prompthub.utils // Ensure this matches your C JNI function names

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

    // --- Declare the native function to retrieve the Base64 encoded key ---
    private external fun getObfuscatedAuthHeader(): String // Renamed to avoid confusion with the public method

    /**
     * Retrieves the plaintext authentication header.
     *
     * This function calls the native C code to:
     * 1. Initialize the obfuscated key storage by calling f0-f8.
     * 2. Reconstruct the Base64 encoded key string via getObfuscatedAuthHeaderInternal.
     * Then, this Kotlin function decodes the Base64 string to get the plaintext header.
     *
     * @return The plaintext authentication header string, or null if an error occurs.
     */
    fun retrievePlaintextAuthHeader(): String? {
        return try {
            // Step 1: Populate the C-side storage by calling f0-f8
            // The order might matter depending on your obfuscation logic.
            Log.d("KeyLoader", "Initializing native key storage...")
            f0()
            f1()
            f2()
            f3()
            f4()
            f5()
            f6()
            f7()
            f8()
            Log.d("KeyLoader", "Native key storage initialized.")

            // Step 2: Retrieve the assembled Base64 key from C
            val base64EncodedKey = getObfuscatedAuthHeader()

            if (base64EncodedKey.isEmpty()) {
                Log.e("KeyLoader", "Retrieved Base64 key from native code is empty.")
                return null
            }
            if (base64EncodedKey.startsWith("Error:")) { // Check for errors from C
                Log.e("KeyLoader", "Native code error: $base64EncodedKey")
                return null
            }


            Log.d("KeyLoader", "Base64 Encoded Key from C: $base64EncodedKey")

            val decodedBytes = base64.decode(base64EncodedKey)
            val plaintextHeader = String(decodedBytes, StandardCharsets.UTF_8)

            Log.d("KeyLoader", "Plaintext Auth Header: $plaintextHeader")
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