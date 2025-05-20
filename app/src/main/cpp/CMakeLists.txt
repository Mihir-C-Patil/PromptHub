cmake_minimum_required(VERSION 3.18.1) # Use a recent version

project("keyobf_project") # You can name your project

# Adds your C source file to a shared library named "keyobf"
# This will produce libkeyobf.so
add_library(keyobf SHARED keyobf.c)

# Optional: Link against the Android log library if you use __android_log_print
find_library(log-lib log)
if(log-lib)
    target_link_libraries(keyobf ${log-lib})
endif()