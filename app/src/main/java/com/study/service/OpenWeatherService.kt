package com.study.service

import com.google.gson.JsonParser
import com.study.model.OpenWeather
import okhttp3.OkHttpClient
import okhttp3.Request

class OpenWeatherService {
    fun getOpenWeather(q: String): OpenWeather {
        val appid = "fcc57465b76d35357c84e4e30fe2431a"
        val path = "http://api.openweathermap.org/data/2.5/weather?q=${q}&appid=${appid}"
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(path)
            .build()

        client.newCall(request).execute().use {
            val json = it.body!!.string()
            val root = JsonParser.parseString(json).asJsonObject
            val name = root.get("name").toString().replace("\"", "")
            val country = root.getAsJsonObject("sys").get("country").toString().replace("\"", "")
            val weather = root.getAsJsonArray("weather")[0].asJsonObject
            val weather_main = weather.get("main").toString().replace("\"", "")
            val weather_description = weather.get("description").toString().replace("\"", "")
            val weather_icon = weather.get("icon").toString().replace("\"", "")
            val main = root.getAsJsonObject("main")
            val main_temp = main.get("temp").asDouble
            val main_feels_like = main.get("feels_like").asDouble
            val main_humidity = main.get("humidity").asDouble
            val clouds_all = root.getAsJsonObject("clouds").get("all").asInt
            val dt = root.get("dt").asInt

            val ow = OpenWeather(name, country, weather_main, weather_description, weather_icon,
                main_temp, main_feels_like, main_humidity, clouds_all, dt)

            return ow
        }
    }
}