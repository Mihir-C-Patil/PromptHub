// Auto-generated - DO NOT EDIT
#pragma once
#include "obfuscate.h"

// Obfuscated hash parts
static std::array<std::string, 5> HASH_PARTS = {
        OBF("122f47937707"),
        OBF("90bd098fc886"),
        OBF("a3da316ea683"),
        OBF("32b7a9a43c5a"),
        OBF("bb7383543eaf035d")
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