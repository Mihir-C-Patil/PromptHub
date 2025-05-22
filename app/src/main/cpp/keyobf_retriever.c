#include "keyobf_header.h"
#include <stdlib.h>
#include <string.h>
#include <jni.h>
#include <android/log.h>

#define LOG_TAG "KeyLoaderJNI"

JNIEXPORT jstring JNICALL
Java_com_example_prompthub_utils_KeyLoader_getObfuscatedAuthHeader(JNIEnv *env, jobject thiz) {
    char *base64Key = (char *)malloc(173);

    __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, "Logging real_indices (up to REAL_VALUE_SIZE=%d):", REAL_VALUE_SIZE);
    for (int i = 0; i < REAL_VALUE_SIZE; i++) {
        // Ensure real_indices[i] is valid if REAL_VALUE_SIZE could exceed actual real_indices allocated size
        // For this example, assuming real_indices is at least as large as REAL_VALUE_SIZE
        __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, "real_indices[%d] = %d", i, real_indices[i]);
    }
    __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, "--- End of real_indices log ---");

    if (base64Key == NULL) {
        return (*env)->NewStringUTF(env, "Error: Memory allocation failed in native retriever.");
    }

    for (int i = 0; i < REAL_VALUE_SIZE; i++) {
        int storage_loc = real_indices[i];
        char currentChar = (char)obfuscatedStorage[storage_loc]; // Store for logging
        __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, "Loop i=%d, storage_loc=%d, char_val=%c (%d)",
                            i, storage_loc, currentChar, (int)currentChar);
        // ... bounds check ...
        base64Key[i] = currentChar;
    }
    base64Key[REAL_VALUE_SIZE] = '\0';

    __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, "Assembled base64Key: %s", base64Key);

    jstring resultJString = (*env)->NewStringUTF(env, base64Key);

    free(base64Key);
    base64Key = NULL;

    if (resultJString == NULL) {
        return (*env)->NewStringUTF(env, "Error: Failed to create string");
    }

    return resultJString;
}
