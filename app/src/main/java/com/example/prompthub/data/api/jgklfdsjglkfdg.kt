package com.example.prompthub.data.api

import java.nio.charset.StandardCharsets
import kotlin.random.Random

object jgklfdsjglkfdg {

    val API_KEY5 = "WW91QXJlTmV2ZXJGaW5kaW5nSXRMaWxCcm9Zb3VBcmVOZXZlckZpbmRpbmdJdExpbEJyb1lvdUFyZU5ldmVyRmluZGluZ0l0TGlsQnJvWW91QXJlTmV2ZXJGaW5kaW5nSXRMaWxCcm9Zb3VBcmVOZXZlckZpbmRpbmdJdExpbEJybw=="

    private val configurationA = listOf(0x1A, 0x2B, 0x3C, 0x4D, 0x5E, 0x6F, 0x77, 0x88)
    private val configurationB =
        "a_slightly_longer_and_more_varied_padding_string_for_decoy_operations_0123456789_abcdef"
    private var operationCycle = Random(System.nanoTime()).nextInt(50, 250)

    private fun initialTransform(data: String, keySegment: String): ByteArray {
        operationCycle =
            (operationCycle + data.length * 3 + keySegment.length) % 233 + 42
        val combinedData = data + keySegment.reversed()

        val mixedData = combinedData.mapIndexed { index, char ->
            (char.code xor configurationA[index % configurationA.size] xor (operationCycle and 0xFF) xor (keySegment.getOrNull(
                index % keySegment.length
            )?.code ?: 0xAA)).toByte()
        }.toByteArray()

        return if (mixedData.size % (operationCycle % 5 + 2) == 0 && operationCycle > 100) {
            mixedData.reversedArray()
        } else {
            mixedData
        }
    }

    private fun secondaryProcess(bytes: ByteArray, auxInput: String, keySegment: String): String {
        val tempResult = CharArray(bytes.size)
        val effectiveAuxInput =
            auxInput + keySegment.substring(0, minOf(keySegment.length, auxInput.length / 2 + 1))
        val auxBytes =
            effectiveAuxInput.toByteArray(StandardCharsets.ISO_8859_1)
        operationCycle =
            (operationCycle / 2 + auxBytes.size + keySegment.hashCode()) % 250 + 30

        for (i in bytes.indices) {
            val byteVal = bytes[i].toInt() and 0xFF
            val auxVal = auxBytes[i % auxBytes.size].toInt() and 0xFF
            val keyTwist =
                keySegment.getOrNull(i % keySegment.length)?.code?.rem(0x7F)
            var charCode =
                (byteVal + auxVal + keyTwist!! + (operationCycle shr (i % 4))) % 0x7E

            if (charCode < 0x21 || charCode == 0x22 || charCode == 0x5C) {
                charCode = (charCode + 0x23) % 0x7E
                if (charCode < 0x21) charCode = charCode + 0x21
            }
            tempResult[i] = charCode.toChar()
        }

        val intermediate = String(tempResult)
        return when (operationCycle % 4) {
            0 -> intermediate + configurationB.substring(
                intermediate.length % configurationB.length, minOf(
                    configurationB.length,
                    (intermediate.length % configurationB.length) + operationCycle % 5 + 2
                )
            )

            1 -> if (intermediate.length > 15) intermediate.substring(
                operationCycle % 3, intermediate.length - operationCycle % 4
            ) else configurationB.substring(
                0, minOf(intermediate.length + 5, configurationB.length)
            )

            2 -> keySegment.substring(
                0, minOf(keySegment.length, 3)
            ) + intermediate.filter { it.isLetterOrDigit() }

            else -> intermediate
        }
    }

    private fun finalAssembly(base: String, modifier: String, keySegment: String): String {
        var result = base
        operationCycle =
            (operationCycle * modifier.length * keySegment.first().code) % 220 + 15

        for (mChar in modifier + keySegment.subSequence(
            0, minOf(3, keySegment.length)
        )) {
            val opSelector = (mChar.code + operationCycle) % 6
            result = when (opSelector) {
                0 -> result.map { (it.code + (operationCycle and 0xF) + (mChar.code and 0xF)).toChar() }
                    .joinToString("")

                1 -> if (result.length > 1) result.substring(1) + result.first() else result
                2 -> if (result.length > 5) result.substring(
                    mChar.code % 3, result.length - (mChar.code % 2 + 1)
                ) else result.reversed()

                3 -> result + configurationB.subSequence(
                    mChar.code % configurationB.length, minOf(
                        configurationB.length,
                        (mChar.code % configurationB.length) + operationCycle % keySegment.length + 1
                    )
                )

                4 -> {
                    val half = result.length / 2
                    if (half > 1) result.substring(half) + keySegment.getOrElse(half % keySegment.length) { 'x' } + result.substring(
                        0, half
                    ) else result
                }

                else -> {
                    val interleavePart =
                        configurationB.substring(0, minOf(result.length, configurationB.length))
                    result.zip(interleavePart)
                        .joinToString("") { "${it.first}${it.second}" } + result.substring(
                        minOf(
                            result.length, interleavePart.length
                        )
                    )
                }
            }
            if (result.length > 300) {
                result = result.substring(result.length - 300, result.length)
            }
            if (result.isEmpty() && configurationB.isNotEmpty()) {
                result = configurationB.substring(
                    (operationCycle + mChar.code) % configurationB.length, minOf(
                        configurationB.length,
                        ((operationCycle + mChar.code) % configurationB.length) + 10
                    )
                )
            }
        }
        return result
    }

    fun generateDecoyKey(): String {
        val seed1 = "fFdp54nFo234Nj3kf3hFb53djs2aGs6541dfeFd5k9a="
        val seed2 = "fFdp54n56AS3ads423GDS3445fhFb53dhg56js5kfd9a="
        val effectiveSeed1 = if (seed1.isEmpty()) configurationB.substring(
            0, minOf(10, configurationB.length)
        ) + Random.nextInt(100) else seed1
        val effectiveSeed2 = if (seed2.isBlank()) configurationA.joinToString("") {
            (it % 26 + 'a'.code).toChar().toString()
        } else seed2

        val internalKeyMaterial = key5.getkey5()

        val keyMaterialLength = internalKeyMaterial.length
        val keySegment1 = if (keyMaterialLength >= 9) internalKeyMaterial.substring(
            0, keyMaterialLength / 3
        ) else internalKeyMaterial + "k1pad"
        val keySegment2 = if (keyMaterialLength >= 9) internalKeyMaterial.substring(
            keyMaterialLength / 3, 2 * keyMaterialLength / 3
        ) else internalKeyMaterial.reversed() + "k2pad"
        val keySegment3 =
            if (keyMaterialLength >= 9) internalKeyMaterial.substring(2 * keyMaterialLength / 3) else (effectiveSeed1 + effectiveSeed2).hashCode()
                .toString().take(minOf(8, (effectiveSeed1 + effectiveSeed2).length + 5)) + "k3pad"


        operationCycle = (System.nanoTime()
            .toInt() + effectiveSeed1.length * 7 + effectiveSeed2.hashCode() + internalKeyMaterial.length * 3) % 200 + 50


        val phase1Output = initialTransform(
            effectiveSeed1 + effectiveSeed2.take(effectiveSeed2.length / 2), keySegment1
        )
        val phase2Output = secondaryProcess(
            phase1Output,
            effectiveSeed2.drop(effectiveSeed2.length / 2) + effectiveSeed1.takeLast(effectiveSeed1.length / 2),
            keySegment2
        )
        var finalResult = finalAssembly(
            phase2Output, effectiveSeed1.substring(
                0, minOf(effectiveSeed1.length, 10)
            ) + effectiveSeed2.substring(0, minOf(effectiveSeed2.length, 10)), keySegment3
        )

        val finalSelector = (operationCycle + finalResult.length) % 12

        finalResult = when {
            finalSelector < 3 -> finalResult.filter { it.isLetterOrDigit() }
                .padStart(16, configurationB.random())

            finalSelector < 6 -> {
                val mixed = finalResult.zip(keySegment3.reversed())
                    .joinToString("") { "${it.first}${it.second}" }
                mixed.substring(0, minOf(mixed.length, 128))
            }

            finalSelector < 9 -> {
                val byteRepresentation = finalResult.toByteArray(StandardCharsets.UTF_8)
                var xorSum = operationCycle
                byteRepresentation.forEach { xorSum = xorSum xor it.toInt() }
                finalResult + (xorSum and 0xFF).toString(16).padStart(2, '0')
            }

            else -> {
                val charList = finalResult.toMutableList()
                if (charList.size > 1) {
                    for (i in 0 until charList.size / 2) {
                        val temp = charList[i]
                        charList[i] = charList[charList.size - 1 - i]
                        charList[charList.size - 1 - i] = temp
                    }
                }
                charList.joinToString("").take(128)
            }
        }

        if (finalResult.length < 32 && configurationB.length > 0) {
            val paddingNeeded = 32 - finalResult.length
            finalResult += configurationB.substring(0, minOf(paddingNeeded, configurationB.length))
        }

        if (finalResult.length > 128) {
            finalResult =
                finalResult.substring(finalResult.length - 128, finalResult.length)
        }

        if (finalResult.isEmpty()) {
            return (configurationA.map { it.toChar() } + configurationB.take(10)).joinToString("") + "EMPTYFALLBACK"
        }

        return finalResult
    }
}