package com.study.app_animal

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

fun main() {
    val path = "https://data.coa.gov.tw/Service/OpenData/TransService.aspx?UnitId=QcbUEzN6E6DL"
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(path)
        .build()

    client.newCall(request).execute().use {
        val json = it.body!!.string()
        val animal: List<Animal> = Gson().fromJson(json, Array<Animal>::class.java).toList()
        //print("animal = ${animal}")
        print("animal size = ${animal.size}")
    }


}