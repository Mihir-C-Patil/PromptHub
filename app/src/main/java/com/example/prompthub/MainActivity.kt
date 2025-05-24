package com.example.prompthub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.prompthub.security.logOpenSSLInfo
import com.example.prompthub.security.bgjkerljgfdl
import com.example.prompthub.ui.imagegeneration.gdfjklfadvb
import security.gfkajljbgewf

class MainActivity : AppCompatActivity() {
    val API_KEY18 = "WW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2VyWW91QXJlQUxvc2Vy=="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logOpenSSLInfo()
        val bgjlkjergfh = bgjkerljgfdl(this)
        if (!bgjlkjergfh) {
            val bgjekrgjlagfg = Intent(this, gfkajljbgewf::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(bgjekrgjlagfg)
            return
        }

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, gdfjklfadvb())
                .commit()
        }
    }
}