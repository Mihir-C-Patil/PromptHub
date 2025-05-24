package com.example.prompthub.utils

object test {
    val API_KEY18 = "WW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2Vy=="
    private val base64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    private val base64Inv = IntArray(256) { -1 }.apply {
        for (i in base64Chars.indices) {
            this[base64Chars[i].code] = i
        }
    }

    fun decode(input: String): ByteArray {
        val cleanInput = input.trim().replace("=", "")
        val output = ByteArray((cleanInput.length * 6) / 8)
        var outIndex = 0

        var buffer = 0
        var bitsCollected = 0

        for (char in cleanInput) {
            val value = base64Inv[char.code]
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
