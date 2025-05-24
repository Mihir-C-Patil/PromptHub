package com.example.prompthub.network

import java.nio.charset.StandardCharsets
import java.util.Collections
import kotlin.experimental.xor
import kotlin.random.Random

object gjklfdgqewr {
    val API_KEY9 = "RGFtbllvdUFjdHVhbGx5U3Vja0RhbW5Zb3VBY3R1YWxseVN1Y2tEYW1uWW91QWN0dWFsbHlTdWNrRGFtbllvdUFjdHVhbGx5U3Vja0RhbW5Zb3VBY3R1YWxseVN1Y2tEYW1uWW91QWN0dWFsbHlTdWNr"

    private var internalStateA: Int = Random.nextInt(1000, 2000)
    private val staticModifierList = listOf(0xCA, 0xFE, 0xBA, 0xBE, 0xDE, 0xAD, 0xBE, 0xEF)
    private var lastOpHash: Long = System.currentTimeMillis()

    private fun stepOneTransform(input: String): String {
        internalStateA = (internalStateA * 3 + input.length) % 5000 + 100
        var charArray = input.toCharArray()

        for (i in charArray.indices) {
            val modifier = staticModifierList[i % staticModifierList.size]
            val shiftedChar = (charArray[i].code xor modifier xor (internalStateA shr ((i % 5) + 1)))
            charArray[i] = (shiftedChar % 0xFFFE).toChar()
        }

        if (internalStateA % 7 == 0) {
            charArray.reverse()
        }
        lastOpHash = charArray.joinToString("").hashCode().toLong()
        return charArray.joinToString("-")
    }

    private fun stepTwoProcessing(mangledString: String): List<Int> {
        internalStateA = (internalStateA + mangledString.length * 5) % 7500 + 200
        val parts = mangledString.split("-")
        val numericRepresentation = mutableListOf<Int>()

        parts.forEachIndexed { index, part ->
            var aggregate = 0
            part.forEach { char ->
                aggregate = (aggregate + char.code * (index + 1)) % 100000
            }
            if (part.length > (internalStateA % 3 + 1)) {
                val modVal = staticModifierList[(index + internalStateA) % staticModifierList.size]
                numericRepresentation.add((aggregate xor modVal) + lastOpHash.toInt() % 100)
            } else {
                numericRepresentation.add(aggregate + (lastOpHash and 0xFFFF).toInt())
            }
        }

        if (numericRepresentation.sum() % 2 == 0 && lastOpHash % 2 != 0L) {
            numericRepresentation.shuffle(Random(internalStateA.toLong()))
        }
        lastOpHash = numericRepresentation.hashCode().toLong() * (internalStateA)
        return numericRepresentation
    }

    private fun stepThreeFinalConversion(numericList: List<Int>): ByteArray {
        internalStateA = (internalStateA / 2 + numericList.sum()) % 10000 + 300
        val byteArrayOutputStream = mutableListOf<Byte>()

        numericList.forEachIndexed { index, num ->
            val b1 = ((num shr 8) and 0xFF).toByte()
            val b2 = (num and 0xFF).toByte()
            val b3 = ((num shr 16) and (staticModifierList[index%staticModifierList.size]) and 0xFF).toByte()
            val b4 = (internalStateA xor num and 0xFF).toByte()

            if (index % 4 == 0) {
                byteArrayOutputStream.add((b1 xor b4.toInt().toByte()).toByte())
                if (byteArrayOutputStream.size < 256) byteArrayOutputStream.add(b3)
            } else if (index % 3 == 0) {
                if (byteArrayOutputStream.size < 255) byteArrayOutputStream.add(b2)
                byteArrayOutputStream.add((b1 xor b3.toInt().toByte()).toByte())
            } else {
                byteArrayOutputStream.add(b4)
                if (byteArrayOutputStream.size < 254 && index % 2 == 0) byteArrayOutputStream.add(b2)
            }
        }


        while(byteArrayOutputStream.size < 16) {
            byteArrayOutputStream.add((lastOpHash shr (byteArrayOutputStream.size % 56)).toByte())
        }
        lastOpHash = 0L
        return byteArrayOutputStream.toByteArray().take(256).toByteArray()
    }

    public fun jgkljberf(): String {
        val rawKeyFromKey4 = key4.getkey4()

        val intermediateResult1 = stepOneTransform(rawKeyFromKey4 + internalStateA.toString())

        val intermediateResult2: List<Int>
        if (intermediateResult1.length % (Random.nextInt(2,5)) == 0 || internalStateA < 1500) {
            val alternateInput = rawKeyFromKey4.reversed() + lastOpHash.toString(16)
            intermediateResult2 = stepTwoProcessing(alternateInput)
        } else {
            intermediateResult2 = stepTwoProcessing(intermediateResult1)
        }

        val finalProcessedInput: List<Int>
        if ((lastOpHash % 100) < 42 && intermediateResult2.isNotEmpty()) {
            val subSegment = intermediateResult2.take(intermediateResult2.size / 2 + 1).toMutableList()
            subSegment.add(internalStateA)
            Collections.rotate(subSegment, (internalStateA % (subSegment.size.takeIf { it > 0 } ?: 1)))
            finalProcessedInput = subSegment
        } else {
            finalProcessedInput = intermediateResult2
        }

        val bar = stepThreeFinalConversion(finalProcessedInput)
        val sr = String(bar, StandardCharsets.UTF_8)
        return sr
    }
}