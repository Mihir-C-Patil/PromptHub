package com.example.prompthub.ui.imagegeneration

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.prompthub.R
import com.example.prompthub.data.api.RetrofitClient
import com.example.prompthub.ui.viewmodel.ImageViewModel
import com.example.prompthub.ui.viewmodel.ImageViewModelFactory
import com.example.prompthub.utils.KeyLoader
import com.example.prompthub.utils.ObfuscationHelper
import java.io.OutputStream

class ImageGenerationFragment : Fragment() {

    private lateinit var promptEditText: EditText
    private lateinit var generateButton: Button
    private lateinit var imageView: ImageView
    private lateinit var saveButton: Button
    private lateinit var loadingProgressBar: ProgressBar

    private lateinit var viewModel: ImageViewModel

    private val AUTH_HEADER: String by lazy {
        ObfuscationHelper.retrieveAuthHeader()
            ?: throw IllegalStateException("Auth header is missing!")
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                saveImageToGallery()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permission to save image denied.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_generation, container, false)

        promptEditText = view.findViewById(R.id.promptEditText)
        generateButton = view.findViewById(R.id.generateButton)
        imageView = view.findViewById(R.id.imageView)
        saveButton = view.findViewById(R.id.saveButton)
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar)

        saveButton.isEnabled = false

        val apiService = RetrofitClient.apiService

        viewModel = ViewModelProvider(this, ImageViewModelFactory(apiService, AUTH_HEADER))
            .get(ImageViewModel::class.java)

        generateButton.setOnClickListener {
            val prompt = promptEditText.text.toString()
            if (prompt.isNotEmpty()) {
                viewModel.generateImageUrl(prompt)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter a prompt to generate an image.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        saveButton.setOnClickListener {
            checkAndRequestPermissionsAndSaveImage()
        }

        observeViewModel()

        return view
    }

    private fun observeViewModel() {
        viewModel.imageUrl.observe(viewLifecycleOwner) { url ->
            if (url != null) {
                val fullImageUrl = "https://ai.elliottwen.info/$url"
                imageView.load(fullImageUrl) {
                    crossfade(false)
                    placeholder(R.drawable.prompthub_logo)
                    error(R.drawable.prompthub_image_error)
                }
                saveButton.isEnabled = true
            } else {
                imageView.setImageDrawable(null)
                saveButton.isEnabled = false
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            isLoading -> loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            generateButton.isEnabled = !isLoading
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            message ->
                if (message != null) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun checkAndRequestPermissionsAndSaveImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveImageToGallery()
        } else {
            when (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                PackageManager.PERMISSION_GRANTED -> {
                    saveImageToGallery()
                } else -> {
                    requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
        }
    }

    private fun saveImageToGallery() {
        val drawable = imageView.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val filename = "${System.currentTimeMillis()}.png"
            val mimeType = "image/png"

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/PromptHub")
                }
            }

            val resolver = requireContext().contentResolver
            var fos: OutputStream? = null
            var imageUri: Uri? = null

            try {
                val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                } else {
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                }

                imageUri = resolver.insert(collection, contentValues)

                if (imageUri == null) {
                    Toast.makeText(requireContext(), "Failed to create new MediaStore record.", Toast.LENGTH_SHORT).show()
                    return
                }

                fos = resolver.openOutputStream(imageUri)

                if (fos == null) {
                    Toast.makeText(requireContext(), "Failed to get OutputStream.", Toast.LENGTH_SHORT).show()
                    return
                }

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                Toast.makeText(requireContext(), "Image saved to gallery!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error saving image: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            } finally {
                fos?.close()
            }
        } else {
            Toast.makeText(requireContext(), "No image to save or image not in a savable format.", Toast.LENGTH_SHORT).show()
        }
    }
}