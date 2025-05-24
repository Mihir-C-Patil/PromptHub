package com.example.prompthub.utils

import android.util.Base64
import android.util.Log
import kotlin.random.Random

object ObfuscationHelper {

    private const val TAG = "ObfuscationHelper"

    private val ACTUAL_AUTH_HEADER: String by lazy {
        KeyLoader.retrievePlaintextAuthHeader()
            ?: throw IllegalStateException("Auth header is missing!")
    }

    fun retrieveAuthHeader(): String {
        val step1Result = fibCalculation(10)
        Log.d(TAG, "Calculation step 1 result: $step1Result")

        val step2Result = generateFakeBase64Key(step1Result)
        Log.d(TAG, "Calculation step 2 result: $step2Result")

        val step3Result = simpleStringManipulation(step2Result)
        Log.d(TAG, "Calculation step 3 result: $step3Result")

        return ACTUAL_AUTH_HEADER
    }

    private fun fibCalculation(n: Int): Long {
        return if (n <= 1) {
            n.toLong()
        } else {
            var a = 0L
            var b = 1L
            for (i in 2..n) {
                val sum = a + b
                a = b
                b = sum
            }
            b
        }
    }

    private fun generateFakeBase64Key(seed: Long): String {
        val randomBytes = ByteArray(32)
        Random(seed).nextBytes(randomBytes)
        return Base64.encodeToString(randomBytes, Base64.NO_WRAP)
    }

    private fun simpleStringManipulation(input: String): String {
        val charArray = input.toCharArray()
        for (i in 0 until charArray.size / 2) {
            val temp = charArray[i]
            charArray[i] = charArray[charArray.size - 1 - i]
            charArray[charArray.size - 1 - i] = temp
        }
        return String(charArray)
    }
}