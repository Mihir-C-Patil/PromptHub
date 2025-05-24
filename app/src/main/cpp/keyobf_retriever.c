#include "keyobf_header.h"
#include <stdlib.h>
#include <string.h>
#include <jni.h>
#include <android/log.h>

#define LOG_TAG "KeyLoaderJNI"

JNIEXPORT jstring JNICALL
Java_com_example_prompthub_utils_KeyLoader_vfsadklgjd(JNIEnv *env, jobject thiz) {
    char *vsgfdhh = (char *)malloc(173);

    for (int i = 0; i < trwbjklgfd; i++) {
        int storage_loc = vadfdg[i];
        char currentChar = (char)vadkffje[storage_loc];
        vsgfdhh[i] = currentChar;
    }
    vsgfdhh[trwbjklgfd] = '\0';

    jstring qtfgaf = (*env)->NewStringUTF(env, vsgfdhh);

    free(vsgfdhh);
    vsgfdhh = NULL;

    return qtfgaf;
}
