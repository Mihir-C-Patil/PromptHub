#include "keyobf_header.h"
#include <stdlib.h>
#include <string.h>
#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_example_prompthub_utils_KeyLoader_getObfuscatedAuthHeader(JNIEnv *env, jobject thiz) {
    char *base64Key = (char *)malloc(REAL_VALUE_SIZE + 1);

    if (base64Key == NULL) {
        return (*env)->NewStringUTF(env, "Error: Memory allocation failed in native retriever.");
    }

    for (int i = 0; i < REAL_VALUE_SIZE; i++) {
        int storage_loc = real_indices[i];

        if (storage_loc < 0 || storage_loc >= OBFUSCATED_STORAGE_SIZE) {
            free(base64Key);
            return (*env)->NewStringUTF(env, "Error: Invalid storage index during key assembly");
        }
        base64Key[i] = (char)obfuscatedStorage[storage_loc];
    }
    base64Key[REAL_VALUE_SIZE] = '\0';

    jstring resultJString = (*env)->NewStringUTF(env, base64Key);

    free(base64Key);
    base64Key = NULL;

    if (resultJString == NULL) {
        return (*env)->NewStringUTF(env, "Error: Failed to create string");
    }

    return resultJString;
}