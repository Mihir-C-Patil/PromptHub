package com.example.prompthub.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.prompthub.data.api.bgjslkerffa
import com.example.prompthub.network.vjafdklsgjwe
import kotlinx.coroutines.launch

class ImageViewModel(private val bgjlkrwd: bgjslkerffa) : ViewModel() {

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
                val bgjklfdgwe = vjafdklsgjwe(bgjlkrwd, vgkjerws)
                bgjkslfdg.value = bgjklfdgwe

                if (bgjklfdgwe == null) {
                    bkjlfjgke.value = "x"
                }
            } catch (e: Exception) {
                bkjlfjgke.value = "vdf"
            } finally {
                vjhfgdsaf.value = false
            }
        }
    }
}

class ImageViewModelFactory(private val vjgslkfde: bgjslkerffa) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            return ImageViewModel(vjgslkfde) as T
        }
        throw IllegalArgumentException("x")
    }
}