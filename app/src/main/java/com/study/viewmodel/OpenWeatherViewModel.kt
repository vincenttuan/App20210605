package com.study.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.model.OpenWeather

class OpenWeatherViewModel: ViewModel() {
    var ow: OpenWeather? = null
    val currentImageURL: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val currentLog: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}