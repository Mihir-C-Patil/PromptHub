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
import java.security.cert.CertificateException

object RetrofitClient {
    private const val TAG = "RetrofitClient"
    private const val BASE_URL = "https://ai.elliottwen.info/"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private class CustomTrustManager : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            throw CertificateException("Client certificates not supported")
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            if (chain.isEmpty()) {
                throw CertificateException("No certificates in chain")
            }

            val endEntityCert = chain[0]
            
            if (!endEntityCert.subjectDN.name.contains("elliottwen.info")) {
                throw CertificateException("Invalid certificate subject: ${endEntityCert.subjectDN}")
            }

            val now = System.currentTimeMillis()
            if (now > endEntityCert.notAfter.time) {
                throw CertificateException("Certificate expired on ${endEntityCert.notAfter}")
            }
            if (now < endEntityCert.notBefore.time) {
                throw CertificateException("Certificate not valid until ${endEntityCert.notBefore}")
            }

            Log.d(TAG, "Certificate verification successful:")
            Log.d(TAG, "Subject: ${endEntityCert.subjectDN}")
            Log.d(TAG, "Issuer: ${endEntityCert.issuerDN}")
            Log.d(TAG, "Valid until: ${endEntityCert.notAfter}")
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return emptyArray()
        }
    }

    private val trustManager = CustomTrustManager()

    private val sslContext = SSLContext.getInstance("TLS").apply {
        init(null, arrayOf<TrustManager>(trustManager), null)
        Log.d(TAG, "SSL Context initialized with strict certificate verification")
    }

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
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .sslSocketFactory(sslContext.socketFactory, trustManager)
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
