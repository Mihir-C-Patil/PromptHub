package com.example.prompthub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.prompthub.security.logOpenSSLInfo
import com.example.prompthub.security.logTamperCheckInfo2
import com.example.prompthub.ui.imagegeneration.ImageGenerationFragment

class MainActivity : AppCompatActivity() {
    val API_KEY18 = "WW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2Vy=="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ImageGenerationFragment())
                .commit()
        }
    }
}