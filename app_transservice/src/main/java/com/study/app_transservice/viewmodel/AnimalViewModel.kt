package com.study.app_transservice.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.app_transservice.model.Animal

class AnimalViewModel: ViewModel() {
    var animals: List<Animal>? = null
    var randomIdx = 0
    val currentImageURL: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val currentInfo: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}