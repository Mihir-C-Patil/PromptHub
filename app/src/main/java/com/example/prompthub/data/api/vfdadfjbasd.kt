package com.example.prompthub.data.api

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

object vfdadfjbasd {
    val API_KEY7 = "WW91QXJlTmV2ZXJGaW5kaW5nSXRMaWxCcm9Zb3VBcmVOZXZlckZpbmRpbmdJdExpbEJyb1lvdUFyZU5ldmVyRmluZGluZ0l0TGlsQnJvWW91QXJlTmV2ZXJGaW5kaW5nSXRMaWxCcm9Zb3VBcmVOZXZlckZpbmRpbmdJdExpbEJybw=="
    private const val TAG = "RetrofitClient"
    private const val BASE_URL = "https://ai.elliottwen.info/"

    private val vadfsjkldsa = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val bgfsdg = CertificatePinner.Builder()
        .add("ai.elliottwen.info", "sha256/WUebdVODEMhWjMW5Y7JE7meI3ie5wnmGbxtE5QGDt7Y=")
        .build()

    private val sslLogger = Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)

        val handshake = response.handshake
        response
    }

    private val protocolLogger = Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)
        val protocol = response.protocol.toString()
        response
    }

    private val okHttpClient = OkHttpClient.Builder()
        .protocols(listOf(Protocol.HTTP_2, Protocol.HTTP_1_1))
        .certificatePinner(bgfsdg)
        .addInterceptor(sslLogger)
        .addInterceptor(protocolLogger)
        .build()

    private val retrofit: Retrofit by lazy {
        try {

            Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(vadfsjkldsa))
                .build()
        } catch (e: Exception) {
            throw e
        }
    }

    private val vgjklergjdf = Interceptor { chain ->
        try {
            val originalRequest = chain.request()
            val requestId = UUID.randomUUID().toString()
            val timestamp = System.currentTimeMillis().toString()

            val signature = vjflkhgfdgad(
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


            response
        } catch (e: Exception) {
            throw e
        }
    }

    private fun vjflkhgfdgad(
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
            throw e
        }
    }

    private val vjklasdfgjfa = Interceptor { chain ->
        try {
            var request = chain.request()

            if (request.method == "GET") {
                request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            }

            var response = chain.proceed(request)

            if (response.isSuccessful) {
                response = response.newBuilder()
                    .header("Cache-Control", "public, max-age=300")
                    .build()
            }

            response
        } catch (e: Exception) {
            throw e
        }
    }

    val vfajdklsg: ApiService by lazy {
        try {
            retrofit.create(ApiService::class.java)
        } catch (e: Exception) {
            throw e
        }
    }
}
