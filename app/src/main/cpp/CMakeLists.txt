cmake_minimum_required(VERSION 3.22.1)
project("prompthub")

add_library(
        native-lib
        SHARED
        native-lib.cpp)

find_library(log-lib log)
include_directories(src/main/cpp)
include_directories(src/main/cpp/generated)

target_link_libraries(
        native-lib
        ${CMAKE_SOURCE_DIR}/../jniLibs/${ANDROID_ABI}/libssl.so
        ${CMAKE_SOURCE_DIR}/../jniLibS/${ANDROID_ABI}/libcrypto.so
        ${log-lib})