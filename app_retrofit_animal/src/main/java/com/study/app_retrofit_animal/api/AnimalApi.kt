package com.study.app_retrofit_animal.api

import com.study.app_retrofit_animal.model.Animal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimalApi {
    // /TransService.aspx?UnitId=QcbUEzN6E6DL
    @GET("TransService.aspx")
    fun getAnimals(@Query("UnitId") uid: String): Call<List<Animal>>
}