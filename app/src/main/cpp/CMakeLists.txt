cmake_minimum_required(VERSION 3.22.1)
project("PromptHub")

add_library(keyobf SHARED
        keyobf.c
        keyobf_retriever.c
)


find_library(log-lib log)
find_library(z-lib z)

add_library(
        native-lib
        SHARED
        native-lib.cpp)

include_directories(
        src/main/cpp
        src/main/cpp/generated
        ${libzip_SOURCE_DIR}/lib
        ${libzip_BINARY_DIR}
        ${ANDROID_NDK}/sources/android/native_app_glue
        ${ANDROID_NDK}/sysroot/usr/include
)

target_link_libraries(
        native-lib
        ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libssl_3.so
        ${CMAKE_SOURCE_DIR}/../jniLibS/${ANDROID_ABI}/libcrypto_3.so
        ${log-lib}
        android
        ${z-lib}
)

if(log-lib)
    target_link_libraries(keyobf ${log-lib})
endif()