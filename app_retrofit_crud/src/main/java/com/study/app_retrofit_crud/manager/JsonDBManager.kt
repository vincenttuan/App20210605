package com.study.app_retrofit_crud.manager

import com.study.app_retrofit_crud.api.JsonDBApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JsonDBManager {
    private val jsonDBApi: JsonDBApi
    val api: JsonDBApi
        get() = jsonDBApi

    companion object { // 相當於 java 的 static class
        val instance = JsonDBManager()
    }

    init {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(logging)
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
            .baseUrl("http://10.0.2.2:3000/") // json-db 位置
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        jsonDBApi = retrofit.create(JsonDBApi::class.java)
    }
}