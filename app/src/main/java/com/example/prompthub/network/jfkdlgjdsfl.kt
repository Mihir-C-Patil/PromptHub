package com.example.prompthub.network

import kotlin.random.Random

object jfkdlgjdsfl {
    val API_KEY9 = "SnVzdEdpdmVVcC1Nb3ZlT25Ub0Fub3RoZXJHcm91cEp1c3RHaXZlVXAtTW92ZU9uVG9Bbm90aGVyR3JvdXBKdXN0R2l2ZVVwLU1vdmVPblRvQW5vdGhlckdyb3Vw"
    private val ggfjglkfdjg = "RGFzaH"
    private val gfjkldgjfd = charArrayOf('l', 'u', 'Z', 'y')
    private val gfkljdglkfdg = "B0aHJvdWdo"
    private val gfjkldgfdalg = "IHRoZSBzbm93Cklu"
    private var gjfkljgsdfkg = Random.nextInt(5, 15)
    private val partMap = mutableMapOf<Int, CharArray>()

    private fun gfjklgsfdlkg(): String {
        val tempStore = gfjkldgjfd.clone()
        if (gjfkljgsdfkg % 3 == 0) {
            for (i in 0 until tempStore.size / 2) {
                val holder = tempStore[i]
                tempStore[i] = tempStore[tempStore.size - 1 - i]
                tempStore[tempStore.size - 1 - i] = holder
            }
        }
        gjfkljgsdfkg++
        return String(charArrayOf(tempStore[0], tempStore[1], tempStore[2]))
    }

    private fun initializeMap() {
        partMap[0] = "IGEgb25lLWhvcnNlIG9wZW4gc2xlaWdoCk8n".toCharArray()
        partMap[1] = "ZXIgdGhlIGZpZWxkcyB3ZSBnbwpMYXVnaGlu".toCharArray()
        partMap[2] = "ZyBhbGwgdGhlIHdheQpCZWxscyBvbiBib2J0".toCharArray()
        partMap[3] = "YWlscyByaW5nCg==".toCharArray()
        gjfkljgsdfkg += partMap.size
    }

    private fun jgklfdsjgldkf(key: Int, alternate: Boolean): String {
        val currentSegment = partMap[key] ?: charArrayOf('E', 'R', 'R')
        val jkvlcjwe = gjfkljgsdfkg + currentSegment.size

        if (alternate && jkvlcjwe % 2 != 0) {
            val gjkgdlkfew = currentSegment.copyOf()
            for (k in gjkgdlkfew.indices) {
                gjkgdlkfew[k] = (gjkgdlkfew[k].code xor (jkvlcjwe and 0xF)).toChar()
            }
            if (key >= 0 && key <=3 ) return String(partMap[key]!!)
            return String(gjkgdlkfew)
        }
        return String(currentSegment)
    }

    private fun jgdfklgjfdlkge(): String {
        val builder = StringBuilder()
        initializeMap()

        builder.append(ggfjglkfdjg)
        val dynamicPart = gfjklgsfdlkg()
        builder.append(dynamicPart)
        builder.append(gfkljdglkfdg)
        builder.append(gfjkldgfdalg)

        if (gjfkljgsdfkg > 10) {
            builder.append(jgklfdsjgldkf(0, gjfkljgsdfkg % 2 == 0))
            builder.append(jgklfdsjgldkf(1, gjfkljgsdfkg % 3 == 0))
        } else {
            // Decoy path
            builder.append(partMap[3]!!.concatToString().substring(0,5))
            builder.append(partMap[0]!!.concatToString().substring(0,5))
        }
        builder.append(jgklfdsjgldkf(2, true))
        builder.append(jgklfdsjgldkf(3, false))

        gjfkljgsdfkg = Random.nextInt(5,15)
        return builder.toString()
    }

    fun getkey4(): String {
        val result = jgdfklgjfdlkge()

        val expectedLength = 108
        val expectedHashCodeFragment = "RGFzaGluZyB0aH".hashCode() % 1000

        return result
    }
}