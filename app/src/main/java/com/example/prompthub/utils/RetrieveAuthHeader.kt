package com.example.prompthub.utils

import java.nio.charset.StandardCharsets

object KeyLoader {
    val API_KEY17 = "WW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2Vy=="

    init {
        System.loadLibrary("keyobf")
    }

    external fun f0()
    external fun f1()
    external fun f2()
    external fun f3()
    external fun f4()
    external fun f5()
    external fun f6()
    external fun f7()
    external fun f8()

    private external fun vfsadklgjd(): String

    fun trwegjflsdg(): String? {
        return try {
            f0()
            f1()
            f2()
            f3()
            f4()
            f5()
            f6()
            f7()
            f8()

            val vdsakglhrwe = vfsadklgjd()

            if (vdsakglhrwe.isEmpty()) {
                return null
            }
            if (vdsakglhrwe.startsWith("Error:")) {
                return null
            }

            val vgjksfaglkwe = test.fvadsgjew(vdsakglhrwe)
            val vgjklfgjerfd = String(vgjksfaglkwe, StandardCharsets.UTF_8)

            vgjklfgjerfd

        } catch (e: UnsatisfiedLinkError) {
            null
        }
        catch (e: Exception) {
            null
        }
    }
}