package com.study.app_databinding_retrofit.api

import com.study.app_databinding_retrofit.model.Post
import retrofit2.Call
import retrofit2.http.*

interface JsonDBApi {

    @GET("/posts")
    fun getPosts(): Call<List<Post>>

    @GET("/posts/{id}")
    fun getPost(@Path("id") id: Int): Call<Post>

    @POST("/posts")
    fun addPost(@Body post: Post): Call<Post>

}