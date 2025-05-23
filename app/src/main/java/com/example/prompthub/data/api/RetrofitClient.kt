package com.example.prompthub.data.api

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.cert.X509Certificate
import java.security.cert.CertificateException

object RetrofitClient {
    private const val TAG = "RetrofitClient"
    private const val BASE_URL = "https://ai.elliottwen.info/"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    // 🔒 Certificate pin (replace with actual hash)
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

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
