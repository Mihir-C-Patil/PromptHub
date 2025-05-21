cmake_minimum_required(VERSION 3.18.1) # Use a recent version

project("PromptHub") # You can name your project

# Adds your C source file to a shared library named "keyobf"
# This will produce libkeyobf.so
add_library(keyobf SHARED
        keyobf.c
        keyobf_retriever.c
)

# Optional: Link against the Android log library if you use __android_log_print
find_library(log-lib log)
if(log-lib)
    target_link_libraries(keyobf ${log-lib})
endif()