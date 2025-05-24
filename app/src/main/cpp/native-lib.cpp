#include <jni.h>
#include <string>
#include <vector>
#include "openssl/ssl.h"
#include <android/log.h>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>
#include "generated/expected_hash.h"

#define LOG_TAG "TamperCheck"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
const char* EXPECTED_APK_HASH = getExpectedHash();

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

// Helper to compute SHA-256 hash
std::string compute_sha256(const std::vector<uint8_t>& data) {
    unsigned char hash[SHA256_DIGEST_LENGTH];
    SHA256_CTX sha256;
    SHA256_Init(&sha256);
    SHA256_Update(&sha256, data.data(), data.size());
    SHA256_Final(hash, &sha256);

    std::string hashStr;
    for (unsigned char i : hash) {
        char buf[3];
        snprintf(buf, sizeof(buf), "%02x", i);
        hashStr += buf;
    }
    return hashStr;
}

// Read file from assets
std::vector<uint8_t> read_asset(AAssetManager* mgr, const char* filename) {
    AAsset* asset = AAssetManager_open(mgr, filename, AASSET_MODE_BUFFER);
    if (!asset) return {};

    size_t size = AAsset_getLength(asset);
    std::vector<uint8_t> buffer(size);
    AAsset_read(asset, buffer.data(), size);
    AAsset_close(asset);
    return buffer;
}

// Helper to list files in assets/res/
void hash_res_directory(AAssetManager* mgr, SHA256_CTX* sha_ctx, const std::string& path) {
    AAssetDir* assetDir = AAssetManager_openDir(mgr, path.c_str());
    if (!assetDir) {
        //LOGD("No directory %s found in assets", path.c_str());
        return;
    }

    const char* filename;
    while ((filename = AAssetDir_getNextFileName(assetDir)) != nullptr) {
        std::string fullPath = path + "/" + filename;

        // Recursively handle directories
        if (AAsset* dirTest = AAssetManager_open(mgr, (fullPath + "/.keep").c_str(), AASSET_MODE_UNKNOWN)) {
            AAsset_close(dirTest);
            hash_res_directory(mgr, sha_ctx, fullPath);
            continue;
        }

        auto data = read_asset(mgr, fullPath.c_str());
        if (data.empty()) {
            SHA256_Update(sha_ctx, data.data(), data.size());
            //LOGD("Hashed res file: %s (%zu bytes)", fullPath.c_str(), data.size());
        }
    }
    AAssetDir_close(assetDir);
}

//Get APK Path in C++
extern "C" JNIEXPORT jboolean JNICALL
Java_com_example_prompthub_security_TamperCheck_nativeVerifyApkHash(
    JNIEnv* env, jobject thiz, jobject assetManager) {
    AAssetManager* mgr = AAssetManager_fromJava(env, assetManager);
    if (!mgr) {
        //LOGE("Failed to get AssetManager");
        return false;
    }

    SHA256_CTX sha256;
    SHA256_Init(&sha256);

    // Core files
    const char* core_files[] = {
            "classes.dex",
            "resources.arsc",
            "AndroidManifest.xml",
            nullptr
    };

    // Hash core files
    for (int i = 0; core_files[i]; i++) {
        auto data = read_asset(mgr, core_files[i]);
        if (data.empty()) {
            SHA256_Update(&sha256, data.data(), data.size());
            //LOGD("Hashed core file: %s (%zu bytes)", core_files[i], data.size());
        }
    }

    // Hash all res/ files recursively
    hash_res_directory(mgr, &sha256, "res");

    // Finalize hash
    unsigned char hash[SHA256_DIGEST_LENGTH];
    SHA256_Final(hash, &sha256);

    // Convert to hex
    char computedHash[SHA256_DIGEST_LENGTH*2 + 1];
    for (int i = 0; i < SHA256_DIGEST_LENGTH; i++) {
        sprintf(computedHash + i*2, "%02x", hash[i]);
    }

    // Debug output
    //LOGD("Expected hash: %s", EXPECTED_APK_HASH);
    //LOGD("Computed hash: %s", computedHash);

    // Constant-time comparison
    bool match = true;
    for (int i = 0; i < SHA256_DIGEST_LENGTH*2; i++) {
        match &= (EXPECTED_APK_HASH[i] == computedHash[i]);
    }

    /*if (!match) {
        LOGE("HASH MISMATCH! Possible tampering detected");
        LOGE("Expected: %s", EXPECTED_APK_HASH);
        LOGE("Actual:   %s", computedHash);
    }*/

    return match;
}

extern "C" JNIEXPORT jboolean JNICALL
Java_com_example_prompthub_security_TamperCheck2_verifyApkHash2(
        JNIEnv* env, jobject thiz, jstring computedHashJava) {

    const char* computedHash = env->GetStringUTFChars(computedHashJava, nullptr);
    if (computedHash == nullptr) {
        LOGE("Failed to get computed hash string");
        return false;
    }

    // Constant-time comparison
    bool match = true;
    for (int i = 0; i < 64; i++) { // SHA-256 is 64 hex chars
        match &= (computedHash[i] == EXPECTED_APK_HASH[i]);
    }

    /*if (!match) {
        LOGE("APK HASH MISMATCH! Possible tampering detected");
        LOGE("Expected: %s", EXPECTED_APK_HASH);
        LOGE("Actual:   %s", computedHash);
    }*/

    env->ReleaseStringUTFChars(computedHashJava, computedHash);
    return match;
}