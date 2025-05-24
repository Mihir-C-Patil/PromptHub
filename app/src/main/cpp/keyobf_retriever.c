#include "keyobf_header.h"
#include <stdlib.h>
#include <string.h>
#include <jni.h>
#include <android/log.h>

#define LOG_TAG "KeyLoaderJNI"

JNIEXPORT jstring JNICALL
Java_com_example_prompthub_utils_KeyLoader_getObfuscatedAuthHeader(JNIEnv *env, jobject thiz) {
    char *base64Key = (char *)malloc(173);

    for (int i = 0; i < REAL_VALUE_SIZE; i++) {
        int storage_loc = real_indices[i];
        char currentChar = (char)obfuscatedStorage[storage_loc]; // Store for logging

        base64Key[i] = currentChar;
    }
    base64Key[REAL_VALUE_SIZE] = '\0';

    jstring resultJString = (*env)->NewStringUTF(env, base64Key);

    free(base64Key);
    base64Key = NULL;

    return resultJString;
}
