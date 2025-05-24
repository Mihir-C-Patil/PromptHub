package com.example.prompthub.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

val API_KEY = "TmljZVRyeUxpbEJyb05pY2VUcnlMaWxCcm9OaWNlVHJ5TGlsQnJvTmljZVRyeUxpbEJyb05pY2VUcnlMaWxCcm9OaWNlVHJ5TGlsQnJvTmljZVRyeUxpbEJybw=="

interface ApiService {
    @POST("/auth")
    suspend fun authenticate(
        @Header("Authorization") authorization: String
    ): Response<AuthResponse>

    @POST("/generate_image")
    suspend fun generateImage(
        @Header("Authorization") authorization: String,
        @Body request: GenerateImageRequest
    ): Response<String>
}