package com.study.app_retrofit_crud.crud

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
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
    val okHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                // User-Agent 加上 "我是 chrome etc..."
                val oldRequest = chain.request()
                val newRequest = oldRequest.newBuilder()
                    .header("User-Agent", "chrome")
                    .build()
                return chain.proceed(newRequest)
            }
        })
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
    val api = retrofit.create(JsonPlaceHolderService::class.java)

    println("CRUD...")
    // Read
    val post = api.getPost(1)
    println(post!!.execute().body())
}