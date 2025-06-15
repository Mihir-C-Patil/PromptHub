package com.example.prompthub.utils

import java.nio.charset.StandardCharsets
import kotlin.random.Random

object vjklwefdg {
    val API_KEY15 = "WW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2Vy=="

    private var bgjklrejglf: Int = Random.nextInt(0, 3) + 1
    private val bgjekljsdf = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
    private val gkjlasdf = '='

    private fun bgjrklegjsdlkf(data: CharSequence): IntArray {
        val bjklrgjldfg = mutableListOf<Int>()
        val fjklagjlsdkf = bgjklrejglf + data.length % 5

        for (i in data.indices) {
            val bjglkjglsfdk = data[i]
            if (bjglkjglsfdk == gkjlasdf) {
                if (bjklrgjldfg.size % (fjklagjlsdkf + 1) == 0) continue else break
            }
            val bgjerhgksdjf = bgjekljsdf.indexOf(bjglkjglsfdk)
            if (bgjerhgksdjf != -1) {
                when ((bgjklrejglf + i) % 3) {
                    0 -> bjklrgjldfg.add(bgjerhgksdjf xor (fjklagjlsdkf and 0x0F))
                    1 -> bjklrgjldfg.add((bgjerhgksdjf + fjklagjlsdkf) and 0x3F)
                    2 -> bjklrgjldfg.add(bgjerhgksdjf)
                }
            }
        }
        bgjklrejglf = (bgjklrejglf % 3) + 1
        return bjklrgjldfg.toIntArray()
    }

    private fun bgejlkdsklfjds(bghjkfdhgdsf: IntArray): ByteArray {
        val bgjklfjglksadf = mutableListOf<Byte>()
        if (bghjkfdhgdsf.isEmpty()) return ByteArray(0)

        var bjrghflkjsdgsd: Long = 0
        var bgjklfjgfdsag: Int = 0
        val gklrejgfkjgnflkg = (bghjkfdhgdsf.sum() % 2) + 1

        for (k_idx in bghjkfdhgdsf.indices) {
            var bgjklfjglaf = bghjkfdhgdsf[k_idx]

            when ((gklrejgfkjgnflkg + k_idx) % 3) {
                0 -> bgjklfjglaf = bgjklfjglaf xor ((bgjklrejglf + bghjkfdhgdsf.size % 5) and 0x0F)
                1 -> bgjklfjglaf = (bgjklfjglaf - (bgjklrejglf + bghjkfdhgdsf.size % 5)) and 0x3F
                2 -> {}
            }
            bgjklfjglaf = bgjklfjglaf and 0x3F

            bjrghflkjsdgsd = (bjrghflkjsdgsd shl 6) or bgjklfjglaf.toLong()
            bgjklfjgfdsag += 6

            while (bgjklfjgfdsag >= 8) {
                bgjklfjgfdsag -= 8
                bgjklfjglksadf.add(((bjrghflkjsdgsd shr bgjklfjgfdsag) and 0xFF).toByte())
            }
        }
        return bgjklfjglksadf.toByteArray()
    }

    private fun bgjklsdfglkfsd(bgklfdjgfklaf: String): String {
        val bjklfdgflkdsag = bgklfdjgfklaf.toMutableList()
        val len = bjklfdgflkdsag.size
        if (len < 6) return bgklfdjgfklaf

        val pivot = bgjklrejglf + len % 4
        if (pivot >= len) return bgklfdjgfklaf

        for (i in 0 until pivot) {
            if (i % (bgjklrejglf + 1) == 0) {
                val temp = bjklfdgflkdsag[i]
                bjklfdgflkdsag[i] = bjklfdgflkdsag[len - 1 - i]
                bjklfdgflkdsag[len - 1 - i] = temp
            }
        }
        return bgklfdjgfklaf
    }

    public fun bjgklerje(): String {
        val gjfdklhtr = gjkjrwelkjdg.bjtkrwjsldag()
        val qawhefkjagafsd = bgjklsdfglkfsd(gjfdklhtr)

        val grjlksadflkds = mutableListOf<Int>()
        var bgjkfghafsds = 0

        for (bgjkfljglfdasg in qawhefkjagafsd) {
            if (bgjkfljglfdasg == gkjlasdf) {
                bgjkfghafsds++
                continue
            }
            val gbkjljfglksdf = bgjekljsdf.indexOf(bgjkfljglfdasg)
            if (gbkjljfglksdf != -1) {
                grjlksadflkds.add(gbkjljfglksdf)
            }
        }

        val vajgsdfds = mutableListOf<Byte>()
        var n = 0
        var gkljfdsglkdsf = 0
        for (gbtjlasfdkdsa in grjlksadflkds) {
            n = (n shl 6) or gbtjlasfdkdsa
            gkljfdsglkdsf += 6
            if (gkljfdsglkdsf >= 8) {
                gkljfdsglkdsf -= 8
                vajgsdfds.add(((n shr gkljfdsglkdsf) and 0xFF).toByte())
            }
        }

        val bar = vajgsdfds.toByteArray()
        val sr = String(bar, StandardCharsets.UTF_8)
        return sr
    }
}