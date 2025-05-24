package com.example.prompthub.data.api

import com.squareup.moshi.JsonClass

val API_KEY2 = "U0tJTExpc3N1ZUxJTGJyb1NLSUxMaXNzdWVMSUxicm9TS0lMTGlzc3VlTElMYnJvU0tJTExpc3N1ZUxJTGJyb1NLSUxMaXNzdWVMSUxicm9TS0lMTGlzc3VlTElMYnJv=="

@JsonClass(generateAdapter = true)
data class AuthResponse(
    val signature: String
)
