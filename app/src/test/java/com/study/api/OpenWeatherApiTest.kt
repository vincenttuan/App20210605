package com.study.api

import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request

fun main() {
    val q = "taoyuan,tw"
    val appid = "fcc57465b76d35357c84e4e30fe2431a"
    val path = "http://api.openweathermap.org/data/2.5/weather?q=${q}&appid=${appid}"
    val client = OkHttpClient()

    val request = Request.Builder()
        .url(path)
        .build()

    client.newCall(request).execute().use {
        val json = it.body!!.string()
        println("json: ${json}")
        // 利用 Gson 分析出我要的資料
        // sys.country. name
        // weather.main, weather.description, weather.icon
        // main.temp, main.feels_like, main.humidity
        // clouds.all
        val root = JsonParser.parseString(json).asJsonObject
        val name = root.get("name").toString().replace("\"", "")
        val country = root.getAsJsonObject("sys").get("country").toString().replace("\"", "")
        val weather = root.getAsJsonArray("weather")[0].asJsonObject
        val weather_main = weather.get("main").toString().replace("\"", "")
        val weather_description = weather.get("description").toString().replace("\"", "")
        val weather_icon = weather.get("icon").toString().replace("\"", "")
        val main = root.getAsJsonObject("main")
        val main_temp = main.get("temp")
        val main_feels_like = main.get("feels_like")
        val main_humidity = main.get("humidity")
        val clouds_all = root.getAsJsonObject("clouds").get("all")

        println(name)
        println(country)
        println(weather_main)
        println(weather_description)
        println(weather_icon)
        println(main_temp)
        println(main_feels_like)
        println(main_humidity)
        println(clouds_all)
    }


}