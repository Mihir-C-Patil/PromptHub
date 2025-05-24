package com.example.prompthub.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.prompthub.data.api.ApiService
import com.example.prompthub.network.generateImage
import kotlinx.coroutines.launch

class ImageViewModel(private val bgjlkrwd: ApiService) : ViewModel() {

    val API_KEY12 = "QnJ1aEp1c3RHaXZlVXBCcnVoSnVzdEdpdmVVcEJydWhKdXN0R2l2ZVVwQnJ1aEp1c3RHaXZlVXBCcnVoSnVzdEdpdmVVcEJydWhKdXN0R2l2ZVVwQnJ1aEp1c3RHaXZlVXBCcnVoSnVzdEdpdmVVcA=="

    private val bgjkslfdg = MutableLiveData<String?>();
    val vgjkfdgwe: LiveData<String?> = bgjkslfdg

    private val vjhfgdsaf = MutableLiveData<Boolean>()
    val vjkslgw: LiveData<Boolean> = vjhfgdsaf

    private val bkjlfjgke = MutableLiveData<String?>()
    val vgjklfdgje: LiveData<String?> = bkjlfjgke

    fun vgjgwedfgh(vgkjerws: String) {
        vjhfgdsaf.value = true
        bkjlfjgke.value = null

        viewModelScope.launch {
            try {
                val url = generateImage(bgjlkrwd, vgkjerws)
                bgjkslfdg.value = url

                if (url == null) {
                    bkjlfjgke.value = "Failed to generate image, please try again."
                }
            } catch (e: Exception) {
                bkjlfjgke.value = "An error occurred: Unknown error"
            } finally {
                vjhfgdsaf.value = false
            }
        }
    }
}

class ImageViewModelFactory(private val vjgslkfde: ApiService) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            return ImageViewModel(vjgslkfde) as T
        }
        throw IllegalArgumentException("x")
    }
}