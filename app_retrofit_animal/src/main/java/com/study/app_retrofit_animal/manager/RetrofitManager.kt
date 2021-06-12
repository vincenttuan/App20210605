package com.study.app_retrofit.manager

import com.study.app_retrofit_animal.api.AnimalApi
import com.study.app_retrofit_animal.model.Animal
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 會以 Singleton 來設計
class RetrofitManager {
    private val animalApi: AnimalApi
    val api: AnimalApi
        get() = animalApi

    companion object { // 相當於 java 的 static class
        val instance = RetrofitManager()
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://data.coa.gov.tw/Service/OpenData/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        animalApi = retrofit.create(AnimalApi::class.java)
    }

}