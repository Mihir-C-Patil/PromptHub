// Auto-generated - DO NOT EDIT
#pragma once
#include "obfuscate.h"

// Obfuscated hash parts
static std::array<std::string, 5> HASH_PARTS = {
        OBF("e3883859088a"),
        OBF("437602a387ab"),
        OBF("833160fb1637"),
        OBF("be40dddf1d84"),
        OBF("e736bb4c62844475")
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