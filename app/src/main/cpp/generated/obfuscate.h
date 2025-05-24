#pragma once
#include <array>
#include <cstring>

template<size_t N>
struct ObfuscatedString {
    std::array<unsigned char, N> data;

    constexpr ObfuscatedString(const char* str) {
        for (size_t i = 0; i < N; i++) {
            data[i] = static_cast<unsigned char>(str[i]) ^ static_cast<unsigned char>(0x55 + i);
        }
    }

    std::string decrypt() const {
        std::string result(N - 1, '\0');
        for (size_t i = 0; i < N - 1; i++) {
            result[i] = static_cast<char>(data[i] ^ static_cast<unsigned char>(0x55 + i));
        }
        return result;
    }
};

#define OBF(str) ([]{ \
    static ObfuscatedString<sizeof(str)> obf(str); \
    return obf.decrypt(); \
}())