package com.example.prompthub.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.prompthub.data.api.ApiService
import com.example.prompthub.network.generateImage
import kotlinx.coroutines.launch

class ImageViewModel(private val apiService: ApiService) : ViewModel() {

    private val _imageUrl = MutableLiveData<String?>();
    val imageUrl: LiveData<String?> = _imageUrl

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun generateImageUrl(prompt: String) {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val url = generateImage(apiService, prompt)
                _imageUrl.value = url

                if (url == null) {
                    _errorMessage.value = "Failed to generate image, please try again."
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred: ${e.localizedMessage ?: "Unknown error"}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class ImageViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            return ImageViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}