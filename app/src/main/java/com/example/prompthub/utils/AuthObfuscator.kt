package com.example.prompthub.utils

object AuthObfuscator {
    fun obfuscate(header: String): String {
        return header.map { char ->
            when {
                char.isLetter() -> if (char.isUpperCase()) 'X' else 'x'
                char.isDigit() -> '#'
                else -> char
            }
        }.joinToString("")
    }

    fun deobfuscate(obfuscatedHeader: String): String {
        // Since we're just replacing with fixed characters, we can't actually deobfuscate
        // This is just a placeholder to show the concept
        return obfuscatedHeader
    }
} 