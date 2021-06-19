package com.study.app_retrofit_crud.crud

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// API (Service)
interface JsonPlaceHolderService {
    @GET("posts/{id}")
    fun getPost(@Path("id") id: Int?): Call<Post?>?
}

fun main() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(JsonPlaceHolderService::class.java)

    println("CRUD...")
    // Read
    val post = api.getPost(1)
    println(post!!.execute().body())
}