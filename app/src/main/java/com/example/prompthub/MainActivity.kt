package com.example.prompthub

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import android.util.Log
import com.bumptech.glide.Glide
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var promptInput: EditText
    private lateinit var generateBtn: Button
    private lateinit var generatedImage: ImageView
    private lateinit var saveBtn: Button
    private val client = OkHttpClient()

    // Your provided authorization key
    private val authKey = "c0957e34a11786192e8819a7d4faef725c3a0becf05716823b30e37111196e92ba1953a695dddd761cce8abbffefce40da8059d06aa651a02f9cc3322a7d1e0b"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        promptInput = findViewById(R.id.promptInput)
        generateBtn = findViewById(R.id.generateBtn)
        generatedImage = findViewById(R.id.generatedImage)
        saveBtn = findViewById(R.id.saveBtn)

        generateBtn.setOnClickListener {
            val prompt = promptInput.text.toString().trim()
            if (prompt.isNotEmpty()) {
                generateImage(prompt)
            } else {
                Toast.makeText(this, "Please enter a prompt", Toast.LENGTH_SHORT).show()
            }
        }

        saveBtn.setOnClickListener {
            saveImage()
        }
    }

    private fun generateImage(prompt: String) {
        val authUrl = "https://ai.elliottwen.info/auth"  // Replace with actual auth URL
        val authRequest = Request.Builder()
            .url(authUrl)
            .header("Authorization", authKey)  // Use provided key here
            .post(RequestBody.create(null, ByteArray(0)))  // No body for auth request
            .build()

        client.newCall(authRequest).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val signature = response.body?.string()?.let {
                        parseSignature(it)
                    }
                    if (signature != null) {
                        fetchGeneratedImage(signature, prompt)
                    }
                } else {
                    Log.e("MainActivity", "Auth failed: ${response.message}")
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Auth failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("MainActivity", "Request failed: ${e.message}")
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Request failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun fetchGeneratedImage(signature: String, prompt: String) {
        val generateUrl = "https://ai.elliottwen.info/generate_image"
        val json = """
            {
                "signature": "$signature",
                "prompt": "$prompt"
            }
        """.trimIndent()

        val body = RequestBody.create(
            "application/json".toMediaTypeOrNull(), json
        )

        val generateRequest = Request.Builder()
            .url(generateUrl)
            .header("Authorization", authKey)  // Use provided key here
            .post(body)
            .build()

        client.newCall(generateRequest).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val imageUrl = response.body?.string()  // Assuming response contains the image URL
                    if (imageUrl != null) {
                        runOnUiThread {
                            loadImage(imageUrl)
                        }
                    }
                } else {
                    Log.e("MainActivity", "Failed to generate image: ${response.message}")
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "Failed to generate image", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("MainActivity", "Request failed: ${e.message}")
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Request failed", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun loadImage(imageUrl: String) {
        val imgUrl = "https://ai.elliottwen.info/$imageUrl"
        Glide.with(this)
            .load(imgUrl)
            .into(generatedImage)

        generatedImage.visibility = View.VISIBLE
        saveBtn.visibility = View.VISIBLE
    }

    private fun saveImage() {
        // Saving the image logic (implement saving to gallery)
        Toast.makeText(this, "Image saved!", Toast.LENGTH_SHORT).show()
    }

    private fun parseSignature(response: String): String? {
        // Parse the response to extract the signature (replace this with your actual parsing logic)
        return response  // In a real case, parse the signature here
    }
}
