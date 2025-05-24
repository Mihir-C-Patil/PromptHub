// Auto-generated - DO NOT EDIT
#pragma once
#include "obfuscate.h"

// Obfuscated hash parts
static std::array<std::string, 5> HASH_PARTS = {
        OBF("df79c22da0db"),
        OBF("faa32b10efd8"),
        OBF("115f0ac3dcd0"),
        OBF("19cc69ef973a"),
        OBF("5b9b4919c84033be")
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