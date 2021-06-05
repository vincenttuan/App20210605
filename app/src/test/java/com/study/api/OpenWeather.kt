package com.study.api

data class OpenWeather(val name: String,
                       val country: String,
                       val weather_main: String,
                       val weather_description: String,
                       val weather_icon: String,
                       val main_temp: Double,
                       val main_feels_like: Double,
                       val main_humidity: Double,
                       val clouds_all: Int,
                       val dt: Int) {
}