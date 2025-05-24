package com.example.prompthub.utils

import kotlin.random.Random

object gjkjrwelkjdg {
    val API_KEY14 = "QnJ1aEp1c3RHaXZlVXBCcnVoSnVzdEdpdmVVcEJydWhKdXN0R2l2ZVVwQnJ1aEp1c3RHaXZlVXBCcnVoSnVzdEdpdmVVcEJydWhKdXN0R2l2ZVVwQnJ1aEp1c3RHaXZlVXBCcnVoSnVzdEdpdmVVcA=="
    private val gjkrlejgf = charArrayOf('a', 'n', 'V', 'z', 'd', 'F', 'B', '1', 'd', 'F', 'R', 'o', 'Z', 'U', 'Z', 'J')
    private val gjklrjfds = listOf("pZ", "XN", "Jb", "mxS", "b1", "pF", "Vk", "Jh", "Zz", "p1", "c3", "RV", "HV", "0V", "Ho", "ZU")
    private val bgjklrgjwf = "ZyaWVzSW5UaGVCYWdqdXN0UHV0VGhlRnJpZXNJblRoZUJhZ2p1c3RQdXRUaGVGcmllc0luVGhlQmFnanVzdFB1dFRoZUZyaWVzSW5UaGVCYWdHZXRSZWFkeVRvTGVhcm5FbmdsaXNo"
    private var gjklrejgsda = Random.nextInt(0, 1024)

    private fun jgklfajgdsf(sourceId: Int, p1: Int, p2: Int, p3: Int): String {
        gjklrejgsda = (gjklrejgsda xor (p1 + p2)) * p3 + (p3 - p1)
        val gjrlkdjsf = StringBuilder()

        when (sourceId % 3) {
            0 -> {
                if (p1 < gjkrlejgf.size && p2 < gjkrlejgf.size && p1 <=p2) {
                    for (i in p1..p2) {
                        gjrlkdjsf.append(gjkrlejgf[i])
                    }
                }
            }
            1 -> {
                val effectiveP1 = p1 % gjklrjfds.size
                val effectiveP2 = p2 % gjklrjfds.size
                if (effectiveP1 <= effectiveP2) {
                    for (j in effectiveP1..effectiveP2) {
                        gjrlkdjsf.append(gjklrjfds[j])
                    }
                } else {
                    for (j in effectiveP2..effectiveP1) {
                        gjrlkdjsf.append(gjklrjfds[j])
                    }
                    gjrlkdjsf.reverse()
                }
            }
            2 -> {
                val start = p1 % (bgjklrgjwf.length -1).coerceAtLeast(1)
                var end = p2 % bgjklrgjwf.length
                if (start >= end && bgjklrgjwf.isNotEmpty()) {
                    end = bgjklrgjwf.length
                }
                if (start < bgjklrgjwf.length && end <= bgjklrgjwf.length && start < end) {
                    gjrlkdjsf.append(bgjklrgjwf.substring(start, end))
                }
            }
        }
        if ((gjklrejgsda and 0x01) == 0 && gjrlkdjsf.length > 1 && (p3 % 2 == 0)) {
            return gjrlkdjsf.toString().reversed()
        }
        return gjrlkdjsf.toString()
    }

    private fun bgjkeljvlfds(f1: String, f2: String, f3: String, f4: String, f5: String): String {
        val bjlkffjldskag = mutableListOf<String>()
        bjlkffjldskag.add(f1)
        bjlkffjldskag.add(f2)
        bjlkffjldskag.add(f3)
        bjlkffjldskag.add(f4)
        bjlkffjldskag.add(f5)

        val selector = gjklrejgsda % 10
        if (selector < 2 && bjlkffjldskag.size >= 3) {
            val temp = bjlkffjldskag[0]
            bjlkffjldskag[0] = bjlkffjldskag[2]
            bjlkffjldskag[2] = temp
        } else if (selector < 4 && bjlkffjldskag.size >= 4) {
            val temp = bjlkffjldskag[1]
            bjlkffjldskag[1] = bjlkffjldskag[3]
            bjlkffjldskag[3] = temp
        }
        gjklrejgsda = (gjklrejgsda + bjlkffjldskag.joinToString("").length) % 2048
        return bjlkffjldskag[0] + bjlkffjldskag[1] + bjlkffjldskag[2] + bjlkffjldskag[3] + bjlkffjldskag[4]
    }

    fun bjtkrwjsldag(): String {
        gjklrejgsda = Random.nextInt(5000, 10000)

        val sub1 = jgklfajgdsf(0, 0, 8, gjklrejgsda)
        val sub2 = jgklfajgdsf(0, 9, 14, gjklrejgsda + 13)
        val sub3 = jgklfajgdsf(1, 0, 15, gjklrejgsda - 21)
        val sub4 = jgklfajgdsf(2, 0, 57, gjklrejgsda * 2)
        val sub5 = jgklfajgdsf(2, 57, bgjklrgjwf.length, gjklrejgsda / 3)

        val frag1 = String(charArrayOf(gjkrlejgf[0],gjkrlejgf[1],gjkrlejgf[2],gjkrlejgf[3],gjkrlejgf[4],gjkrlejgf[5],gjkrlejgf[6],gjkrlejgf[7],gjkrlejgf[8])) // anVzdFB1d
        val frag2 = String(charArrayOf(gjkrlejgf[14],gjkrlejgf[13],gjkrlejgf[12],gjkrlejgf[11],gjkrlejgf[10],gjkrlejgf[9])).reversed() // FRoZUZ

        val frag3Builder = StringBuilder()
        for(i in 0 until 16) { frag3Builder.append(gjklrjfds[i]) }
        val frag3 = frag3Builder.toString()

        val frag4 = bgjklrgjwf.substring(0, 57)
        val frag5 = bgjklrgjwf.substring(57)

        return bgjkeljvlfds(frag1, frag2, frag3, frag4, frag5)
    }
}