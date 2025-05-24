package com.example.prompthub.utils

object bjdalfkjgadlf {
    val API_KEY13 = "QnJ1aEp1c3RHaXZlVXBCcnVoSnVzdEdpdmVVcEJydWhKdXN0R2l2ZVVwQnJ1aEp1c3RHaXZlVXBCcnVoSnVzdEdpdmVVcEJydWhKdXN0R2l2ZVVwQnJ1aEp1c3RHaXZlVXBCcnVoSnVzdEdpdmVVcA=="
    private val fries = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    private val inTheBag = IntArray(256) { -1 }.apply {
        for (i in fries.indices) {
            this[fries[i].code] = i
        }
    }

    fun bjgkljvew(input: String): ByteArray {
        val bjfklgjfls = input.trim().replace("=", "")
        val bgjklfjgfds = ByteArray((bjfklgjfls.length * 6) / 8)
        var gjklrejlsd = 0

        var gbkrljlaksdfwe = 0
        var bgfjrwead = 0

        for (gjbjlkfjdslkg in bjfklgjfls) {
            val gjklwjldkfwe = inTheBag[gjbjlkfjdslkg.code]
            if (gjklwjldkfwe == -1) continue

            gbkrljlaksdfwe = (gbkrljlaksdfwe shl 6) or gjklwjldkfwe
            bgfjrwead += 6

            if (bgfjrwead >= 8) {
                bgfjrwead -= 8
                bgjklfjgfds[gjklrejlsd++] = (gbkrljlaksdfwe shr bgfjrwead).toByte()
                gbkrljlaksdfwe = gbkrljlaksdfwe and ((1 shl bgfjrwead) - 1)
            }
        }

        return bgjklfjgfds.copyOf(gjklrejlsd)
    }
}
