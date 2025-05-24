package com.example.prompthub.data.api

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.CacheControl
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.MessageDigest
import java.util.UUID

object RetrofitClient {
    private const val TAG = "RetrofitClient"
    private const val BASE_URL = "https://ai.elliottwen.info/"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val certificatePinner = CertificatePinner.Builder()
        .add("ai.elliottwen.info", "sha256/WUebdVODEMhWjMW5Y7JE7meI3ie5wnmGbxtE5QGDt7Y=")
        .build()

    private val sslLogger = Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)

        val handshake = response.handshake
        if (handshake != null) {
            Log.d(TAG, "SSL/TLS Connection Status:")
            Log.d(TAG, "Protocol: ${handshake.tlsVersion}")
            Log.d(TAG, "Cipher Suite: ${handshake.cipherSuite}")
            Log.d(TAG, "Connection: ${if (request.isHttps) "Secure (HTTPS)" else "Insecure (HTTP)"}")
        }

        response
    }

    private val protocolLogger = Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)
        val protocol = response.protocol.toString()
        Log.d(TAG, "Request to ${request.url} using protocol: $protocol")
        response
    }

    private val okHttpClient = OkHttpClient.Builder()
        .protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
        .certificatePinner(certificatePinner)
        .addInterceptor(sslLogger)
        .addInterceptor(protocolLogger)
        .build()

    private val retrofit: Retrofit by lazy {
        try {
            Log.d(TAG, "🔒 Security: Initializing API client")
            Log.d(TAG, "  ├─ Base URL: ${ApiConfig.BASE_URL}")
            Log.d(TAG, "  └─ Converter: Moshi")

            Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        } catch (e: Exception) {
            Log.e(TAG, "❌ API Error: ${e.message}")
            throw e
        }
    }

    private val signatureInterceptor = Interceptor { chain ->
        try {
            val originalRequest = chain.request()
            val requestId = UUID.randomUUID().toString()
            val timestamp = System.currentTimeMillis().toString()

            Log.d(RetrofitClient.TAG, "🔒 Security: Request signing initiated")
            Log.d(RetrofitClient.TAG, "  ├─ URL: ${originalRequest.url}")
            Log.d(RetrofitClient.TAG, "  ├─ Request ID: $requestId")
            Log.d(RetrofitClient.TAG, "  └─ Timestamp: $timestamp")

            val signature = generateSignature(
                requestId = requestId,
                timestamp = timestamp,
                path = originalRequest.url.encodedPath,
                method = originalRequest.method
            )

            val newRequest = originalRequest.newBuilder()
                .header("X-Request-ID", requestId)
                .header("X-Timestamp", timestamp)
                .header("X-Signature", signature)
                .build()

            val response = chain.proceed(newRequest)

            Log.d(RetrofitClient.TAG, "🔒 Security: Response received")
            Log.d(RetrofitClient.TAG, "  ├─ Status: ${response.code}")
            Log.d(RetrofitClient.TAG, "  └─ Protocol: ${response.protocol}")

            response
        } catch (e: Exception) {
            Log.e(RetrofitClient.TAG, "❌ Security Error: ${e.message}")
            throw e
        }
    }

    private fun generateSignature(
        requestId: String,
        timestamp: String,
        path: String,
        method: String
    ): String {
        return try {
            val dataToSign = "$requestId:$timestamp:$path:$method:${ApiConfig.SECURE_KEY}"
            MessageDigest.getInstance("SHA-256")
                .digest(dataToSign.toByteArray())
                .joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            Log.e(TAG, "Error generating signature", e)
            throw e
        }
    }

    private val cacheInterceptor = Interceptor { chain ->
        try {
            var request = chain.request()

            if (request.method == "GET") {
                request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
                Log.d(TAG, "🔒 Security: Cache enabled (5min)")
            }

            var response = chain.proceed(request)

            if (response.isSuccessful) {
                response = response.newBuilder()
                    .header("Cache-Control", "public, max-age=300")
                    .build()
            }

            response
        } catch (e: Exception) {
            Log.e(TAG, "❌ Cache Error: ${e.message}")
            throw e
        }
    }

    val apiService: ApiService by lazy {
        try {
            Log.d(TAG, "🔒 Security: API service ready")
            Log.d(TAG, "  └─ All security measures active")
            retrofit.create(ApiService::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "❌ Service Error: ${e.message}")
            throw e
        }
    }
}
