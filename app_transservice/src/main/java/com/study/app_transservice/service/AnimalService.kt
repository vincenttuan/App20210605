package com.study.app_transservice.service

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.study.app_transservice.model.Animal
import okhttp3.OkHttpClient
import okhttp3.Request

class AnimalService(var path: String) {
    fun getAnimals(): List<Animal> {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(path)
            .build()

        client.newCall(request).execute().use {
            val json = it.body!!.string()
             return Gson().fromJson(json, Array<Animal>::class.java).toList()
        }
    }
}