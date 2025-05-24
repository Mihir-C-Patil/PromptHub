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

void throwJavaException(JNIEnv* env, const char* msg) {
    jclass exClass = env->FindClass("java/lang/Exception");
    env->ThrowNew(exClass, msg);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_prompthub_security_OpenSSLHelper_getOpenSSLVersion(
        JNIEnv* env,
        jobject /* this */) {
    return env->NewStringUTF(OpenSSL_version(OPENSSL_VERSION));
}

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

std::vector<uint8_t> read_asset(AAssetManager* mgr, const char* filename) {
    AAsset* asset = AAssetManager_open(mgr, filename, AASSET_MODE_BUFFER);
    if (!asset) return {};

    size_t size = AAsset_getLength(asset);
    std::vector<uint8_t> buffer(size);
    AAsset_read(asset, buffer.data(), size);
    AAsset_close(asset);
    return buffer;
}

void hash_res_directory(AAssetManager* mgr, SHA256_CTX* sha_ctx, const std::string& path) {
    AAssetDir* assetDir = AAssetManager_openDir(mgr, path.c_str());
    if (!assetDir) {
        return;
    }

    const char* filename;
    while ((filename = AAssetDir_getNextFileName(assetDir)) != nullptr) {
        std::string fullPath = path + "/" + filename;

        if (AAsset* dirTest = AAssetManager_open(mgr, (fullPath + "/.keep").c_str(), AASSET_MODE_UNKNOWN)) {
            AAsset_close(dirTest);
            hash_res_directory(mgr, sha_ctx, fullPath);
            continue;
        }

        auto data = read_asset(mgr, fullPath.c_str());
        if (data.empty()) {
            SHA256_Update(sha_ctx, data.data(), data.size());
        }
    }
    AAssetDir_close(assetDir);
}

extern "C" JNIEXPORT jboolean JNICALL
Java_com_example_prompthub_security_TamperCheck_nativeVerifyApkHash(
    JNIEnv* env, jobject thiz, jobject assetManager) {
    AAssetManager* mgr = AAssetManager_fromJava(env, assetManager);
    if (!mgr) {
        return false;
    }

    SHA256_CTX sha256;
    SHA256_Init(&sha256);

    const char* core_files[] = {
            "classes.dex",
            "resources.arsc",
            "AndroidManifest.xml",
            nullptr
    };

    for (int i = 0; core_files[i]; i++) {
        auto data = read_asset(mgr, core_files[i]);
        if (data.empty()) {
            SHA256_Update(&sha256, data.data(), data.size());
        }
    }

    hash_res_directory(mgr, &sha256, "res");

    unsigned char hash[SHA256_DIGEST_LENGTH];
    SHA256_Final(hash, &sha256);

    char computedHash[SHA256_DIGEST_LENGTH*2 + 1];
    for (int i = 0; i < SHA256_DIGEST_LENGTH; i++) {
        sprintf(computedHash + i*2, "%02x", hash[i]);
    }

    bool match = true;
    for (int i = 0; i < SHA256_DIGEST_LENGTH*2; i++) {
        match &= (EXPECTED_APK_HASH[i] == computedHash[i]);
    }

    return match;
}

extern "C" JNIEXPORT jboolean JNICALL
Java_com_example_prompthub_security_TamperCheck2_verifyApkHash2(
        JNIEnv* env, jobject thiz, jstring computedHashJava) {

    const char* computedHash = env->GetStringUTFChars(computedHashJava, nullptr);
    if (computedHash == nullptr) {
        return false;
    }

    bool match = true;
    for (int i = 0; i < 64; i++) {
        match &= (computedHash[i] == EXPECTED_APK_HASH[i]);
    }

    env->ReleaseStringUTFChars(computedHashJava, computedHash);
    return match;
}