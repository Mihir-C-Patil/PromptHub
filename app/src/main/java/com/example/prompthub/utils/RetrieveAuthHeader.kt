package com.example.prompthub.utils

import android.util.Base64
import android.util.Log
import java.nio.charset.StandardCharsets

class RetrieveAuthHeader {
    object NativeLib {
        init {
            System.loadLibrary("keyobf")
        }

        external fun retrieveAuthKey(): String
    }
}