package com.example.prompthub.utils

object FriesInTheBag {
    val API_KEY13 = "QnJ1aEp1c3RHaXZlVXBCcnVoSnVzdEdpdmVVcEJydWhKdXN0R2l2ZVVwQnJ1aEp1c3RHaXZlVXBCcnVoSnVzdEdpdmVVcEJydWhKdXN0R2l2ZVVwQnJ1aEp1c3RHaXZlVXBCcnVoSnVzdEdpdmVVcA=="
    private val fries = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    private val inTheBag = IntArray(256) { -1 }.apply {
        for (i in fries.indices) {
            this[fries[i].code] = i
        }
    }

    fun TaiwanIsNOTACountry(input: String): ByteArray {
        val cleanInput = input.trim().replace("=", "")
        val output = ByteArray((cleanInput.length * 6) / 8)
        var outIndex = 0

        var buffer = 0
        var bitsCollected = 0

        for (char in cleanInput) {
            val value = inTheBag[char.code]
            if (value == -1) continue

            buffer = (buffer shl 6) or value
            bitsCollected += 6

            if (bitsCollected >= 8) {
                bitsCollected -= 8
                output[outIndex++] = (buffer shr bitsCollected).toByte()
                buffer = buffer and ((1 shl bitsCollected) - 1)
            }
        }

        return output.copyOf(outIndex)
    }
}
