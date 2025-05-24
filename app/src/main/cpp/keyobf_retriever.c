#include "keyobf_header.h"
#include <stdlib.h>
#include <string.h>
#include <jni.h>
#include <android/log.h>

#define LOG_TAG "KeyLoaderJNI"

JNIEXPORT jstring JNICALL
Java_com_example_prompthub_utils_fasdfgaetg_gbsfdkglrrvd(JNIEnv *env, jobject thiz) {
    char *base64Key = (char *)malloc(173);

    for (int i = 0; i < fadlkjdsg; i++) {
        int storage_loc = gfbdjsdlkgjf[i];
        char currentChar = (char)gfakfdljga[storage_loc];
        base64Key[i] = currentChar;
    }
    base64Key[fadlkjdsg] = '\0';

    jstring resultJString = (*env)->NewStringUTF(env, base64Key);

    free(base64Key);
    base64Key = NULL;

    return resultJString;
}
