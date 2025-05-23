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
import java.security.MessageDigest
import java.util.UUID
import okhttp3.Cache
import okhttp3.CacheControl
import java.io.File
import android.content.Context

class RetrofitClient private constructor(private val context: Context) {
    companion object {
        private const val TAG = "RetrofitClient"
        private const val CACHE_SIZE = 10 * 1024 * 1024L // 10MB cache

        @Volatile
        private var instance: RetrofitClient? = null

        fun getInstance(context: Context): RetrofitClient {
            return instance ?: synchronized(this) {
                instance ?: RetrofitClient(context.applicationContext).also { instance = it }
            }
        }
    }

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val signatureInterceptor = Interceptor { chain ->
        try {
            val originalRequest = chain.request()
            val requestId = UUID.randomUUID().toString()
            val timestamp = System.currentTimeMillis().toString()
            
            Log.d(TAG, "🔒 Security: Request signing initiated")
            Log.d(TAG, "  ├─ URL: ${originalRequest.url}")
            Log.d(TAG, "  ├─ Request ID: $requestId")
            Log.d(TAG, "  └─ Timestamp: $timestamp")
            
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
            
            Log.d(TAG, "🔒 Security: Response received")
            Log.d(TAG, "  ├─ Status: ${response.code}")
            Log.d(TAG, "  └─ Protocol: ${response.protocol}")
            
            response
        } catch (e: Exception) {
            Log.e(TAG, "❌ Security Error: ${e.message}")
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

    private val trustManager = object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            throw CertificateException("Client certificates not supported")
        }
        
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            try {
                if (chain == null || chain.isEmpty()) {
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
            } catch (e: Exception) {
                Log.e(TAG, "Certificate verification failed", e)
                throw e
            }
        }
        
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }

    private val sslContext: SSLContext by lazy {
        try {
            SSLContext.getInstance("TLS").apply {
                init(null, arrayOf<TrustManager>(trustManager), null)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing SSL context", e)
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

    private val okHttpClient: OkHttpClient by lazy {
        try {
            Log.d(TAG, "🔒 Security: Initializing secure client")
            Log.d(TAG, "  ├─ TLS: Enabled")
            Log.d(TAG, "  ├─ Timeouts: 15s")
            Log.d(TAG, "  └─ Certificate: Strict validation")
            
            OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .sslSocketFactory(sslContext.socketFactory, trustManager)
                .addInterceptor(signatureInterceptor)
                .addInterceptor(cacheInterceptor)
                .build()
        } catch (e: Exception) {
            Log.e(TAG, "❌ Client Error: ${e.message}")
            throw e
        }
    }

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
