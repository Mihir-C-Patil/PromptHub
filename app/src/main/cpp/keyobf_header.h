#ifndef PROMPTHUB_KEYOBF_H
#define PROMPTHUB_KEYOBF_H

#include <jni.h>

#define OBFUSCATED_STORAGE_SIZE 600
#define REAL_VALUE_SIZE 172

extern unsigned char obfuscatedStorage[OBFUSCATED_STORAGE_SIZE];
extern int real_indices[REAL_VALUE_SIZE];

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f0(JNIEnv *env, jobject thiz);

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f1(JNIEnv *env, jobject thiz);

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f2(JNIEnv *env, jobject thiz);

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f3(JNIEnv *env, jobject thiz);

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f4(JNIEnv *env, jobject thiz);

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f5(JNIEnv *env, jobject thiz);

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f6(JNIEnv *env, jobject thiz);

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f7(JNIEnv *env, jobject thiz);

JNIEXPORT void JNICALL
Java_com_example_prompthub_utils_KeyLoader_f8(JNIEnv *env, jobject thiz);

#endif //PROMPTHUB_KEYOBF_H
