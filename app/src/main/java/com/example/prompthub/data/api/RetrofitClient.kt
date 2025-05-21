package com.example.prompthub.data.api

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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

object RetrofitClient {
    private const val TAG = "RetrofitClient"
    private const val BASE_URL = "https://ai.elliottwen.info/"
    private const val MAX_RETRIES = 3

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    // SSL Configuration
    private val trustManager = object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            Log.d(TAG, "Client certificate check: $authType")
        }
        
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            Log.d(TAG, "Server certificate check: $authType")
            chain?.forEach { cert ->
                Log.d(TAG, "Certificate: ${cert.subjectDN}")
                Log.d(TAG, "Issuer: ${cert.issuerDN}")
                Log.d(TAG, "Valid until: ${cert.notAfter}")
            }
        }
        
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }

    private val sslContext = SSLContext.getInstance("TLS").apply {
        init(null, arrayOf<TrustManager>(trustManager), null)
        Log.d(TAG, "SSL Context initialized with protocol: ${protocol}")
    }

    private val sslLogger = Interceptor { chain ->
        val request = chain.request()
        val response = chain.proceed(request)
        
        // Log SSL/TLS information
        val handshake = response.handshake
        if (handshake != null) {
            Log.d(TAG, "SSL/TLS Connection Status:")
            Log.d(TAG, "Protocol: ${handshake.tlsVersion}")
            Log.d(TAG, "Cipher Suite: ${handshake.cipherSuite}")
            Log.d(TAG, "Connection: ${if (request.isHttps) "Secure (HTTPS)" else "Insecure (HTTP)"}")
        }
        
        response
    }

    private val retryInterceptor = Interceptor { chain ->
        var retryCount = 0
        var response: Response? = null
        
        while (retryCount < MAX_RETRIES && (response == null || !response.isSuccessful)) {
            try {
                response = chain.proceed(chain.request())
                if (response.isSuccessful) {
                    return@Interceptor response
                }
            } catch (e: Exception) {
                Log.e(TAG, "Request failed: ${e.message}")
            }
            retryCount++
            if (retryCount < MAX_RETRIES) {
                Thread.sleep(1000L * retryCount) // Exponential backoff
            }
        }
        response ?: throw Exception("Failed after $MAX_RETRIES retries")
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
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .sslSocketFactory(sslContext.socketFactory, trustManager)
        .addInterceptor(sslLogger)
        .addInterceptor(retryInterceptor)
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
