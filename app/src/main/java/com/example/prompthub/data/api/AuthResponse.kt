package com.example.prompthub.data.api

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthResponse(
    val signature: String
)
