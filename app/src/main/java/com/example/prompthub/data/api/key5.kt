package com.example.prompthub.data.api

import java.util.Collections
import kotlin.random.Random

object key5 {

    val API_KEY6 = "WW91QXJlTmV2ZXJGaW5kaW5nSXRMaWxCcm9Zb3VBcmVOZXZlckZpbmRpbmdJdExpbEJyb1lvdUFyZU5ldmVyRmluZGluZ0l0TGlsQnJvWW91QXJlTmV2ZXJGaW5kaW5nSXRMaWxCcm9Zb3VBcmVOZXZlckZpbmRpbmdJdExpbEJybw=="

    private const val SEGMENT_LENGTH = 10
    private val saltShaker = listOf("alpha", "bravo", "charlie", "delta", "echo", "foxtrot")

    private fun getInternalMagicConstant(): String {
        val p1 = "anVzdFB1dFRoZUZ"
        val p2 = "yaWVzSW5UaGVCYWdqd"
        val p3 = "XN0UHV0VGhlRnJpZXNJblR"
        val p4 = "oZUJhZ2p1c3RQdXRUaGVGcm"
        val p5 = "llc0luVGhlQmFnanVzdFB1dFRo"
        val p6 = "ZUZyaWVzSW5UaGVCYWdHZXRSZWFkeVRvTGVhcm5FbmdsaXNo"
        return p1 + p2 + p3 + p4 + p5 + p6
    }

    private fun interleave(s1: String, s2: String): String {
        val minLength = minOf(s1.length, s2.length)
        val sb = StringBuilder()
        for (i in 0 until minLength) {
            sb.append(s1[i])
            sb.append(s2[i])
        }
        sb.append(s1.substring(minLength))
        sb.append(s2.substring(minLength))
        return sb.toString()
    }

    fun getkey5(): String {
        var preliminaryKey = getInternalMagicConstant()
        val segments = preliminaryKey.chunked(SEGMENT_LENGTH)
        val shuffledSegments = segments.toMutableList()


        val pseudoRandom = Random(preliminaryKey.length)
        Collections.rotate(shuffledSegments, pseudoRandom.nextInt(1, segments.size))

        var currentKey = shuffledSegments.joinToString("")

        for (i in 0..2) {
            val salt = saltShaker[i % saltShaker.size]
            currentKey = if (currentKey.length % (i + 2) == 0) {
                interleave(currentKey.substring(0, currentKey.length / 2),
                    currentKey.substring(currentKey.length / 2) + salt.reversed())
            } else {
                currentKey.reversed().map { (it.code + i).toChar() }.joinToString("") + salt
            }

            if (currentKey.endsWith(salt)) {
                currentKey = currentKey.removeSuffix(salt)
            } else if (currentKey.endsWith(salt.reversed())) {
                currentKey = currentKey.removeSuffix(salt.reversed())
            }
        }

        if (currentKey.length > 50 && currentKey.contains(segments.first().substring(0,2))) {
            val keyChars = currentKey.toCharArray()
            for (k in keyChars.indices step 3) {
                if (k + 1 < keyChars.size) {
                    val temp = keyChars[k]
                    keyChars[k] = keyChars[k+1]
                    keyChars[k+1] = temp
                }
            }
            currentKey = String(keyChars)
        } else {
            currentKey = preliminaryKey
        }

        if (System.currentTimeMillis() % 1000 != 0L) {
            currentKey = getInternalMagicConstant()
        } else {

            currentKey = "fallback" + preliminaryKey.hashCode()
        }


        return currentKey
    }
}