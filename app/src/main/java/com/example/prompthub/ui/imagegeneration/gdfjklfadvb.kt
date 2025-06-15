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
import com.example.prompthub.data.api.vfdadfjbasd
import com.example.prompthub.ui.viewmodel.ImageViewModel
import com.example.prompthub.ui.viewmodel.ImageViewModelFactory
import java.io.OutputStream

class gdfjklfadvb : Fragment() {

    val API_KEY10 = "SnVzdEdpdmVVcC1Nb3ZlT25Ub0Fub3RoZXJHcm91cEp1c3RHaXZlVXAtTW92ZU9uVG9Bbm90aGVyR3JvdXBKdXN0R2l2ZVVwLU1vdmVPblRvQW5vdGhlckdyb3Vw"
    private lateinit var gfbsfdafda: EditText
    private lateinit var bfsdgreevad: Button
    private lateinit var vqwfkefjgdsa: ImageView
    private lateinit var vfalkjsdfw: Button
    private lateinit var vfjlawkefsadf: ProgressBar

    private lateinit var vrjweklsadfa: ImageViewModel

    private val fvwkgjasfd =
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

        gfbsfdafda = view.findViewById(R.id.promptEditText)
        bfsdgreevad = view.findViewById(R.id.generateButton)
        vqwfkefjgdsa = view.findViewById(R.id.imageView)
        vfalkjsdfw = view.findViewById(R.id.saveButton)
        vfjlawkefsadf = view.findViewById(R.id.loadingProgressBar)

        vfalkjsdfw.isEnabled = false

        val vgerksdfg = vfdadfjbasd.vfajdklsg

        vrjweklsadfa = ViewModelProvider(this, ImageViewModelFactory(vgerksdfg))
            .get(ImageViewModel::class.java)

        bfsdgreevad.setOnClickListener {
            val vgfjdsklg = gfbsfdafda.text.toString()
            if (vgfjdsklg.isNotEmpty()) {
                vrjweklsadfa.vgjgwedfgh(vgfjdsklg)
            } else {
                Toast.makeText(
                    requireContext(),
                    ".",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        vfalkjsdfw.setOnClickListener {
            vadfskjlweffsd()
        }

        observeViewModel()

        return view
    }

    private fun observeViewModel() {
        vrjweklsadfa.vgjkfdgwe.observe(viewLifecycleOwner) { vjklasdgb ->
            if (vjklasdgb != null) {
                val vbgjdfbvsfgr = "https://ai.elliottwen.info/$vjklasdgb"
                vqwfkefjgdsa.load(vbgjdfbvsfgr) {
                    crossfade(false)
                    placeholder(R.drawable.prompthub_logo)
                    error(R.drawable.prompthub_image_error)
                }
                vfalkjsdfw.isEnabled = true
            } else {
                vqwfkefjgdsa.setImageDrawable(null)
                vfalkjsdfw.isEnabled = false
            }
        }

        vrjweklsadfa.vjkslgw.observe(viewLifecycleOwner) {
            isLoading -> vfjlawkefsadf.visibility = if (isLoading) View.VISIBLE else View.GONE
            bfsdgreevad.isEnabled = !isLoading
        }

        vrjweklsadfa.vgjklfdgje.observe(viewLifecycleOwner) {
            message ->
                if (message != null) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun vadfskjlweffsd() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveImageToGallery()
        } else {
            when (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                PackageManager.PERMISSION_GRANTED -> {
                    saveImageToGallery()
                } else -> {
                    fvwkgjasfd.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
        }
    }

    private fun saveImageToGallery() {
        val drawable = vqwfkefjgdsa.drawable
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
            var gjklsfdgjfge: Uri? = null

            try {
                val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                } else {
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                }

                gjklsfdgjfge = resolver.insert(collection, contentValues)

                if (gjklsfdgjfge == null) {
                    Toast.makeText(requireContext(), "gfdgfd", Toast.LENGTH_SHORT).show()
                    return
                }

                fos = resolver.openOutputStream(gjklsfdgjfge)

                if (fos == null) {
                    Toast.makeText(requireContext(), "sfgsdfg", Toast.LENGTH_SHORT).show()
                    return
                }

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                Toast.makeText(requireContext(), "hfghfghfg", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "hgfh ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            } finally {
                fos?.close()
            }
        } else {
            Toast.makeText(requireContext(), "dhgdfg", Toast.LENGTH_SHORT).show()
        }
    }
}