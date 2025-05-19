#include <jni.h>
#include <string>
#include "openssl/evp.h"
#include "openssl/ssl.h"
#include "openssl/err.h"
#include "openssl/sha.h"
#include <android/log.h>
#include "generated/expected_hash.h"

#define LOG_TAG "OpenSSL_Native"
#define LOG_ERROR(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOG_INFO(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
//const char* EXPECTED_APK_HASH = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";

// Helper to throw Java exceptions from native code
void throwJavaException(JNIEnv* env, const char* msg) {
    jclass exClass = env->FindClass("java/lang/Exception");
    env->ThrowNew(exClass, msg);
}

// 1. Basic OpenSSL version check
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_prompthub_security_OpenSSLHelper_getOpenSSLVersion(
        JNIEnv* env,
        jobject /* this */) {
    return env->NewStringUTF(OpenSSL_version(OPENSSL_VERSION));
}

// 2. SHA-256 Hashing
extern "C" JNIEXPORT jbyteArray JNICALL
Java_com_example_prompthub_security_OpenSSLHelper_sha256(
        JNIEnv* env,
        jobject /* this */,
        jbyteArray input) {

    jbyte* inputBytes = env->GetByteArrayElements(input, nullptr);
    jsize length = env->GetArrayLength(input);

    EVP_MD_CTX* mdctx = EVP_MD_CTX_new();
    unsigned char hash[EVP_MAX_MD_SIZE];
    unsigned int hashLen;

    if(!mdctx) {
        throwJavaException(env, "Failed to create EVP context");
        return nullptr;
    }

    if(EVP_DigestInit_ex(mdctx, EVP_sha256(), nullptr) &&
       EVP_DigestUpdate(mdctx, inputBytes, length) &&
       EVP_DigestFinal_ex(mdctx, hash, &hashLen)) {

        jbyteArray result = env->NewByteArray(hashLen);
        env->SetByteArrayRegion(result, 0, hashLen, reinterpret_cast<jbyte*>(hash));
        EVP_MD_CTX_free(mdctx);
        env->ReleaseByteArrayElements(input, inputBytes, JNI_ABORT);
        return result;
    }

    EVP_MD_CTX_free(mdctx);
    env->ReleaseByteArrayElements(input, inputBytes, JNI_ABORT);
    throwJavaException(env, "Hashing failed");
    return nullptr;
}

//Get APK Path in C++
extern "C" JNIEXPORT jboolean JNICALL
Java_com_example_prompthub_security_TamperCheck_verifyApkHash(
        JNIEnv* env,
        jobject thiz,
        jstring apkPath) {

    const char* path = env->GetStringUTFChars(apkPath, NULL);
    FILE* apkFile = fopen(path, "rb");

    if (!apkFile) {
        __android_log_write(ANDROID_LOG_ERROR, "SECURITY", "Failed to open APK");
        return JNI_FALSE;
    }
    // Compute SHA-256
    SHA256_CTX sha256;
    SHA256_Init(&sha256);

    unsigned char buffer[4096];
    size_t bytesRead;
    while ((bytesRead = fread(buffer, 1, 4096, apkFile))) {
        SHA256_Update(&sha256, buffer, bytesRead);
    }

    unsigned char actualHash[SHA256_DIGEST_LENGTH];
    SHA256_Final(actualHash, &sha256);
    fclose(apkFile);

    // Convert expected hash from hex to binary
    unsigned char expectedHashBin[SHA256_DIGEST_LENGTH];
    for (int i = 0; i < SHA256_DIGEST_LENGTH; i++) {
        sscanf(EXPECTED_APK_HASH + 2*i, "%02hhx", &expectedHashBin[i]);
    }

    // Compare
    int mismatch = memcmp(actualHash, expectedHashBin, SHA256_DIGEST_LENGTH);

    __android_log_print(ANDROID_LOG_INFO, "SECURITY",
                        "Hash match: %s", mismatch ? "NO" : "YES");

    return mismatch ? JNI_FALSE : JNI_TRUE;
}