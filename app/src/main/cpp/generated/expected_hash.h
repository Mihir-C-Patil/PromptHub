// Auto-generated - DO NOT EDIT
#pragma once
#include "obfuscate.h"

// Obfuscated hash parts
static std::array<std::string, 5> HASH_PARTS = {
        OBF("54444869bc0d"),
        OBF("e2eb3de213da"),
        OBF("e5cf1471b896"),
        OBF("62bc5ab3dcb7"),
        OBF("68d8b99168575ed7")
};

// Runtime hash reconstruction
inline const char* getExpectedHash() {
    static char fullHash[65] = {0}; // SHA-256 hash is 64 chars + '\0'
    static bool initialized = false;

    if (!initialized) {
        std::string result;
        for (const auto& part : HASH_PARTS) {
            result += part;
        }
        std::strncpy(fullHash, result.c_str(), sizeof(fullHash) - 1);
        fullHash[64] = '\0'; // Ensure null-termination
        initialized = true;
    }

    return fullHash;
}