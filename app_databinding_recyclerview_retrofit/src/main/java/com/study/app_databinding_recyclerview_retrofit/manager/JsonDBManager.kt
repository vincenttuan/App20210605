package com.study.app_databinding_recyclerview_retrofit.manager

import com.study.app_databinding_recyclerview_retrofit.api.JsonDBApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JsonDBManager {
    private val jsonDBApi: JsonDBApi
    val api: JsonDBApi
        get() = jsonDBApi
    companion object {
        val instance = JsonDBManager()
    }
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        jsonDBApi = retrofit.create(JsonDBApi::class.java)
    }
}