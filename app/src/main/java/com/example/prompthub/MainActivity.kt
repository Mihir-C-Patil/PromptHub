package com.example.prompthub // Make sure this matches your actual package name

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.prompthub.R
import com.example.prompthub.security.OpenSSLHelper
import com.example.prompthub.security.logOpenSSLInfo
import com.example.prompthub.security.logTamperCheckInfo
import com.example.prompthub.security.logTamperCheckInfo2
import com.example.prompthub.ui.imagegeneration.ImageGenerationFragment
import security.TamperDetectedActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Simple test
        logOpenSSLInfo()
        logTamperCheckInfo(this)
        val isUntampered = logTamperCheckInfo2(this)
        if (!isUntampered) {
            // Show red screen if tampered
            val intent = Intent(this, TamperDetectedActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
            return
        }

        // Set the content view of the activity to the layout defined in activity_main.xml
        setContentView(R.layout.activity_main)

        // Load fragment manually
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ImageGenerationFragment())
                .commit()
        }
    }
}