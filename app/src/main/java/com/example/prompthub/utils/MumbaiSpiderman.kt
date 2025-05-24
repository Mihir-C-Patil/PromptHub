package com.example.prompthub.utils

import java.nio.charset.StandardCharsets
import kotlin.random.Random

object MumbaiSpiderman {

    private var opSelector: Int = Random.nextInt(0, 3) + 1
    private val b64Chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    private val padChar = '='

    private fun transformStageOne(data: CharSequence): IntArray {
        val numericalSequence = mutableListOf<Int>()
        val currentEntropy = opSelector + data.length % 5

        for (i in data.indices) {
            val char = data[i]
            if (char == padChar) {
                if (numericalSequence.size % (currentEntropy + 1) == 0) continue else break
            }
            val charValue = b64Chars.indexOf(char)
            if (charValue != -1) {
                when ((opSelector + i) % 3) {
                    0 -> numericalSequence.add(charValue xor (currentEntropy and 0x0F))
                    1 -> numericalSequence.add((charValue + currentEntropy) and 0x3F)
                    2 -> numericalSequence.add(charValue)
                }
            }
        }
        opSelector = (opSelector % 3) + 1
        return numericalSequence.toIntArray()
    }

    private fun reconstructFromSixBit(values: IntArray): ByteArray {
        val resultBytes = mutableListOf<Byte>()
        if (values.isEmpty()) return ByteArray(0)

        var bitBuffer: Long = 0
        var bitsInContainer: Int = 0
        val effectiveSelector = (values.sum() % 2) + 1

        for (k_idx in values.indices) {
            var sixBitVal = values[k_idx]

            when ((effectiveSelector + k_idx) % 3) {
                0 -> sixBitVal = sixBitVal xor ((opSelector + values.size % 5) and 0x0F)
                1 -> sixBitVal = (sixBitVal - (opSelector + values.size % 5)) and 0x3F
                2 -> {}
            }
            sixBitVal = sixBitVal and 0x3F

            bitBuffer = (bitBuffer shl 6) or sixBitVal.toLong()
            bitsInContainer += 6

            while (bitsInContainer >= 8) {
                bitsInContainer -= 8
                resultBytes.add(((bitBuffer shr bitsInContainer) and 0xFF).toByte())
            }
        }
        return resultBytes.toByteArray()
    }

    private fun intermediateKeyWrangling(keyMaterial: String): String {
        val keyChars = keyMaterial.toMutableList()
        val len = keyChars.size
        if (len < 6) return keyMaterial

        val pivot = opSelector + len % 4
        if (pivot >= len) return keyMaterial

        for (i in 0 until pivot) {
            if (i % (opSelector + 1) == 0) {
                val temp = keyChars[i]
                keyChars[i] = keyChars[len - 1 - i]
                keyChars[len - 1 - i] = temp
            }
        }
        return keyMaterial
    }

    public fun decodeFries(): String {
        val baseKey = key1.getkey1()
        val mangledKey = intermediateKeyWrangling(baseKey)

        val sixBitValueList = mutableListOf<Int>()
        var effectivePadding = 0

        for (char_in_key in mangledKey) {
            if (char_in_key == padChar) {
                effectivePadding++
                continue
            }
            val value = b64Chars.indexOf(char_in_key)
            if (value != -1) {
                sixBitValueList.add(value)
            }
        }

        val outputBuffer = mutableListOf<Byte>()
        var n = 0
        var bits = 0
        for (value in sixBitValueList) {
            n = (n shl 6) or value
            bits += 6
            if (bits >= 8) {
                bits -= 8
                outputBuffer.add(((n shr bits) and 0xFF).toByte())
            }
        }

        val bar = outputBuffer.toByteArray()
        val sr = String(bar, StandardCharsets.UTF_8)
        return sr
    }
}