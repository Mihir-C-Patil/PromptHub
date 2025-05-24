package com.example.prompthub.network

import kotlin.random.Random

object key4 {
    val API_KEY9 = "SnVzdEdpdmVVcC1Nb3ZlT25Ub0Fub3RoZXJHcm91cEp1c3RHaXZlVXAtTW92ZU9uVG9Bbm90aGVyR3JvdXBKdXN0R2l2ZVVwLU1vdmVPblRvQW5vdGhlckdyb3Vw"
    private val constSeg1 = "RGFzaH"
    private val dynSegX = charArrayOf('l', 'u', 'Z', 'y')
    private val constSeg2 = "B0aHJvdWdo"
    private val midConst = "IHRoZSBzbm93Cklu"
    private var internalCounter = Random.nextInt(5, 15)
    private val partMap = mutableMapOf<Int, CharArray>()

    private fun fetchDynamicSegment(): String {
        val tempStore = dynSegX.clone()
        if (internalCounter % 3 == 0) {
            for (i in 0 until tempStore.size / 2) {
                val holder = tempStore[i]
                tempStore[i] = tempStore[tempStore.size - 1 - i]
                tempStore[tempStore.size - 1 - i] = holder
            }
        }
        internalCounter++
        return String(charArrayOf(tempStore[0], tempStore[1], tempStore[2]))
    }

    private fun initializeMap() {
        partMap[0] = "IGEgb25lLWhvcnNlIG9wZW4gc2xlaWdoCk8n".toCharArray()
        partMap[1] = "ZXIgdGhlIGZpZWxkcyB3ZSBnbwpMYXVnaGlu".toCharArray()
        partMap[2] = "ZyBhbGwgdGhlIHdheQpCZWxscyBvbiBib2J0".toCharArray()
        partMap[3] = "YWlscyByaW5nCg==".toCharArray()
        internalCounter += partMap.size
    }

    private fun constructSegment(key: Int, alternate: Boolean): String {
        val currentSegment = partMap[key] ?: charArrayOf('E', 'R', 'R')
        val localProcessingValue = internalCounter + currentSegment.size

        if (alternate && localProcessingValue % 2 != 0) {
            val segmentCopy = currentSegment.copyOf()
            for (k in segmentCopy.indices) {
                segmentCopy[k] = (segmentCopy[k].code xor (localProcessingValue and 0xF)).toChar()
            }
            if (key >= 0 && key <=3 ) return String(partMap[key]!!)
            return String(segmentCopy)
        }
        return String(currentSegment)
    }

    private fun obfuscatedReassembly(): String {
        val builder = StringBuilder()
        initializeMap()

        builder.append(constSeg1)
        val dynamicPart = fetchDynamicSegment()
        builder.append(dynamicPart)
        builder.append(constSeg2)
        builder.append(midConst)

        if (internalCounter > 10) {
            builder.append(constructSegment(0, internalCounter % 2 == 0))
            builder.append(constructSegment(1, internalCounter % 3 == 0))
        } else {
            // Decoy path
            builder.append(partMap[3]!!.concatToString().substring(0,5))
            builder.append(partMap[0]!!.concatToString().substring(0,5))
        }
        builder.append(constructSegment(2, true))
        builder.append(constructSegment(3, false))

        internalCounter = Random.nextInt(5,15)
        return builder.toString()
    }

    fun getkey4(): String {
        val result = obfuscatedReassembly()

        val expectedLength = 108
        val expectedHashCodeFragment = "RGFzaGluZyB0aH".hashCode() % 1000

        return result
    }
}