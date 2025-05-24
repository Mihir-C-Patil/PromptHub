package com.example.prompthub.utils

import kotlin.random.Random

object key1 {
    val API_KEY14 = "QnJ1aEp1c3RHaXZlVXBCcnVoSnVzdEdpdmVVcEJydWhKdXN0R2l2ZVVwQnJ1aEp1c3RHaXZlVXBCcnVoSnVzdEdpdmVVcEJydWhKdXN0R2l2ZVVwQnJ1aEp1c3RHaXZlVXBCcnVoSnVzdEdpdmVVcA=="
    private val dataFragmentX = charArrayOf('a', 'n', 'V', 'z', 'd', 'F', 'B', '1', 'd', 'F', 'R', 'o', 'Z', 'U', 'Z', 'J')
    private val dataFragmentY = listOf("pZ", "XN", "Jb", "mxS", "b1", "pF", "Vk", "Jh", "Zz", "p1", "c3", "RV", "HV", "0V", "Ho", "ZU")
    private val dataFragmentZ = "ZyaWVzSW5UaGVCYWdqdXN0UHV0VGhlRnJpZXNJblRoZUJhZ2p1c3RQdXRUaGVGcmllc0luVGhlQmFnanVzdFB1dFRoZUZyaWVzSW5UaGVCYWdHZXRSZWFkeVRvTGVhcm5FbmdsaXNo"
    private var obfuscationState = Random.nextInt(0, 1024)

    private fun extractPart(sourceId: Int, p1: Int, p2: Int, p3: Int): String {
        obfuscationState = (obfuscationState xor (p1 + p2)) * p3 + (p3 - p1)
        val builder = StringBuilder()

        when (sourceId % 3) {
            0 -> {
                if (p1 < dataFragmentX.size && p2 < dataFragmentX.size && p1 <=p2) {
                    for (i in p1..p2) {
                        builder.append(dataFragmentX[i])
                    }
                }
            }
            1 -> {
                val effectiveP1 = p1 % dataFragmentY.size
                val effectiveP2 = p2 % dataFragmentY.size
                if (effectiveP1 <= effectiveP2) {
                    for (j in effectiveP1..effectiveP2) {
                        builder.append(dataFragmentY[j])
                    }
                } else {
                    for (j in effectiveP2..effectiveP1) {
                        builder.append(dataFragmentY[j])
                    }
                    builder.reverse()
                }
            }
            2 -> {
                val start = p1 % (dataFragmentZ.length -1).coerceAtLeast(1)
                var end = p2 % dataFragmentZ.length
                if (start >= end && dataFragmentZ.isNotEmpty()) {
                    end = dataFragmentZ.length
                }
                if (start < dataFragmentZ.length && end <= dataFragmentZ.length && start < end) {
                    builder.append(dataFragmentZ.substring(start, end))
                }
            }
        }
        if ((obfuscationState and 0x01) == 0 && builder.length > 1 && (p3 % 2 == 0)) {
            return builder.toString().reversed()
        }
        return builder.toString()
    }

    private fun assembleKeyFragments(f1: String, f2: String, f3: String, f4: String, f5: String): String {
        val intermediary = mutableListOf<String>()
        intermediary.add(f1)
        intermediary.add(f2)
        intermediary.add(f3)
        intermediary.add(f4)
        intermediary.add(f5)

        val selector = obfuscationState % 10
        if (selector < 2 && intermediary.size >= 3) {
            val temp = intermediary[0]
            intermediary[0] = intermediary[2]
            intermediary[2] = temp
        } else if (selector < 4 && intermediary.size >= 4) {
            val temp = intermediary[1]
            intermediary[1] = intermediary[3]
            intermediary[3] = temp
        }
        obfuscationState = (obfuscationState + intermediary.joinToString("").length) % 2048
        return intermediary[0] + intermediary[1] + intermediary[2] + intermediary[3] + intermediary[4]
    }

    fun getkey1(): String {
        obfuscationState = Random.nextInt(5000, 10000)

        val sub1 = extractPart(0, 0, 8, obfuscationState)
        val sub2 = extractPart(0, 9, 14, obfuscationState + 13)
        val sub3 = extractPart(1, 0, 15, obfuscationState - 21)
        val sub4 = extractPart(2, 0, 57, obfuscationState * 2)
        val sub5 = extractPart(2, 57, dataFragmentZ.length, obfuscationState / 3)

        val frag1 = String(charArrayOf(dataFragmentX[0],dataFragmentX[1],dataFragmentX[2],dataFragmentX[3],dataFragmentX[4],dataFragmentX[5],dataFragmentX[6],dataFragmentX[7],dataFragmentX[8])) // anVzdFB1d
        val frag2 = String(charArrayOf(dataFragmentX[14],dataFragmentX[13],dataFragmentX[12],dataFragmentX[11],dataFragmentX[10],dataFragmentX[9])).reversed() // FRoZUZ

        val frag3Builder = StringBuilder()
        for(i in 0 until 16) { frag3Builder.append(dataFragmentY[i]) }
        val frag3 = frag3Builder.toString()

        val frag4 = dataFragmentZ.substring(0, 57)
        val frag5 = dataFragmentZ.substring(57)

        return assembleKeyFragments(frag1, frag2, frag3, frag4, frag5)
    }
}