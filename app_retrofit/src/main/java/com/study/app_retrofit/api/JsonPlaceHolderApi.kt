package com.study.app_retrofit.api

import com.study.app_retrofit.model.Post
import retrofit2.Call
import retrofit2.http.GET

interface JsonPlaceHolderApi {
    @GET("posts")
    fun getPosts(): Call<List<Post>>
}