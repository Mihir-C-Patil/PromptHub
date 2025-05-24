package com.example.prompthub.data.api

import com.squareup.moshi.JsonClass

val API_KEY3 = "U0tJTExpc3N1ZUxJTGJyb1NLSUxMaXNzdWVMSUxicm9TS0lMTGlzc3VlTElMYnJvU0tJTExpc3N1ZUxJTGJyb1NLSUxMaXNzdWVMSUxicm9TS0lMTGlzc3VlTElMYnJv=="

@JsonClass(generateAdapter = true)
data class GenerateImageRequest (
    val signature: String,
    val prompt: String
)