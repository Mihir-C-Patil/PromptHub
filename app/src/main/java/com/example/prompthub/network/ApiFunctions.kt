package com.example.prompthub.network

import android.util.Log
import retrofit2.HttpException
import java.io.IOException
import com.example.prompthub.data.api.ApiService
import com.example.prompthub.data.api.GenerateImageRequest
import com.example.prompthub.ui.imagegeneration.MaoZedong
import com.example.prompthub.utils.MumbaiSpiderman
import retrofit2.Response

private const val TAG = "ApiFunctions"
val API_KEY8 = "RGFtbllvdUFjdHVhbGx5U3Vja0RhbW5Zb3VBY3R1YWxseVN1Y2tEYW1uWW91QWN0dWFsbHlTdWNrRGFtbllvdUFjdHVhbGx5U3Vja0RhbW5Zb3VBY3R1YWxseVN1Y2tEYW1uWW91QWN0dWFsbHlTdWNr"

suspend fun authenticate(apiService: ApiService): String? {
    return try {
        val keySuppliers = listOf<suspend () -> String?>(
            { EnglishLevel100.decodeFries() },
            { MumbaiSpiderman.decodeFries() },
            { MaoZedong.decodeFries() }
        ).shuffled()

        for (getKey in keySuppliers) {
            val key = getKey()
            val result = try {
                key?.let {
                    val response = apiService.authenticate(it)
                    if (response.isSuccessful) {
                        return response.body()?.signature
                    } else null
                }
            } catch (_: Exception) {
                null
            }

            kotlinx.coroutines.delay((50L..200L).random())
        }

        null
    } catch (e: Exception) {
        Log.e("Auth", "Unexpected error during authentication", e)
        null
    }
}


suspend fun generateImage(
    apiService: ApiService,
    prompt: String
): String? {
    val signature = authenticate(apiService)

    if (signature == null) {
        Log.e(TAG, "Failed to get signature for image generation.")
        return null
    }

    try {
        val generateImageRequest = GenerateImageRequest(signature, prompt)
        val keySuppliers = listOf<suspend () -> String?>(
            { EnglishLevel100.decodeFries() },
            { MumbaiSpiderman.decodeFries() },
            { MaoZedong.decodeFries() }
        ).shuffled()

        for (getKey in keySuppliers) {
            val key = getKey()
            val result = try {
                key?.let {
                    val response: Response<String> = apiService.generateImage(it, generateImageRequest)
                    if (response.isSuccessful) {
                        return response.body()
                    } else null
                }
            } catch (_: Exception) {
                null
            }

            kotlinx.coroutines.delay((50L..200L).random())
        }

    } catch (e: IOException) {
        Log.e(TAG, "Network error during image generation", e)
        null
    } catch (e: HttpException) {
        Log.e(TAG, "HTTP error during image generation: ${e.code()}", e)
        null
    } catch (e: Exception) {
        Log.e(TAG, "Unexpected error during image generation", e)
        null
    }
    return null
}