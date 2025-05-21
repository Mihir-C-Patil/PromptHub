package com.example.prompthub // Make sure this matches your actual package name

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.prompthub.R
import com.example.prompthub.security.OpenSSLHelper
import com.example.prompthub.security.logOpenSSLInfo
import com.example.prompthub.security.logTamperCheckInfo
import com.example.prompthub.security.logTamperCheckInfo2

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view of the activity to the layout defined in activity_main.xml
        setContentView(R.layout.activity_main)
        // Simple test
        logOpenSSLInfo()
        //logTamperCheckInfo(this)
        logTamperCheckInfo2(this)
        // Display on screen
        //findViewById<TextView>(R.id.text_view).text = sslVersion
    }
}