package com.example.prompthub // Make sure this matches your actual package name

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.prompthub.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view of the activity to the layout defined in activity_main.xml
        setContentView(R.layout.activity_main)
    }
}