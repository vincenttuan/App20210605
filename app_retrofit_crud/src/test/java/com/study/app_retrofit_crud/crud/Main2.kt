package com.study.app_retrofit_crud.crud

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


interface OpenWeatherService {
    @GET
    fun getStringResponse(@Url url: String?): Call<String>
}

fun main() {
    // https://api.openweathermap.org/data/2.5/weather?q=taoyuan,tw&appid=fcc57465b76d35357c84e4e30fe2431a
    // 透過 retrofit 可以取得 temp 顯在溫度
    val path = "weather?q=taoyuan,tw&appid=fcc57465b76d35357c84e4e30fe2431a"
    val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .build()
    val api = retrofit.create(OpenWeatherService::class.java)

    val json = api.getStringResponse(path).execute().body()

    val jelement: JsonElement = JsonParser.parseString(json)
    val jobject = jelement.asJsonObject
    var temp = jobject.getAsJsonObject("main").get("temp").asDouble
    temp -= 273.15
    println(temp)
}