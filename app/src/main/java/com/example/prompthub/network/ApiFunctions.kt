package com.example.prompthub.network

import android.util.Log
import retrofit2.HttpException
import java.io.IOException
import com.example.prompthub.data.api.ApiService
import com.example.prompthub.data.api.GenerateImageRequest
import retrofit2.Response

private const val TAG = "ApiFunctions"

suspend fun authenticate(
    apiService: ApiService,
    authHeader: String
): String? {
    return try {
        val response = apiService.authenticate(authHeader)
        if (response.isSuccessful) {
            response.body()?.signature
        } else {
            Log.e(TAG, "Authentication failed: ${response.code()} - ${response.errorBody()}")
            null
        }
    } catch (e: IOException) {
        Log.e(TAG, "Network error during authentication", e)
        null
    } catch (e: HttpException) {
        Log.e(TAG, "HTTP error during authentication: ${e.code()}", e)
        null
    } catch (e: Exception) {
        Log.e(TAG, "Unexpected error during authentication", e)
        null
    }
}


suspend fun generateImage(
    apiService: ApiService,
    authHeader: String,
    prompt: String
): String? {
    val signature = authenticate(apiService, authHeader)

    if (signature == null) {
        Log.e(TAG, "Failed to get signature for image generation.")
        return null
    }

    return try {
        val generateImageRequest = GenerateImageRequest(signature, prompt)
        val response: Response<String> = apiService.generateImage(authHeader, generateImageRequest)
        Log.d(TAG, "Image generation response: $response")

        if (response.isSuccessful) {
            response.body()
        } else {
            Log.e(
                TAG,
                "Image generation failed: ${response.code()} - ${response.errorBody()?.string()}"
            )
            null
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
}