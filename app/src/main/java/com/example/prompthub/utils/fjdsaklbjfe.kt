package com.example.prompthub.utils

object fjdsaklbjfe {
    val API_KEY18 = "WW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2Vy=="
    private val bjgklef = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    private val bgbkjlrjgd = IntArray(256) { -1 }.apply {
        for (i in bjgklef.indices) {
            this[bjgklef[i].code] = i
        }
    }

    fun fvadsgjew(input: String): ByteArray {
        val jglkglkdf = input.trim().replace("=", "")
        val bgjklergj = ByteArray((jglkglkdf.length * 6) / 8)
        var gbjkrlejgsd = 0

        var gbkjrelgjlds = 0
        var blkrjglkfdsg = 0

        for (bglkfdjgf in jglkglkdf) {
            val fvjlkdjlsdf = bgbkjlrjgd[bglkfdjgf.code]
            if (fvjlkdjlsdf == -1) continue

            gbkjrelgjlds = (gbkjrelgjlds shl 6) or fvjlkdjlsdf
            blkrjglkfdsg += 6

            if (blkrjglkfdsg >= 8) {
                blkrjglkfdsg -= 8
                bgjklergj[gbjkrlejgsd++] = (gbkjrelgjlds shr blkrjglkfdsg).toByte()
                gbkjrelgjlds = gbkjrelgjlds and ((1 shl blkrjglkfdsg) - 1)
            }
        }

        return bgjklergj.copyOf(gbjkrlejgsd)
    }
}
