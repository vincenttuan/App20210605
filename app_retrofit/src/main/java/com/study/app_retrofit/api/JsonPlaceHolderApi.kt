package com.study.app_retrofit.api

import com.study.app_retrofit.model.Comment
import com.study.app_retrofit.model.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// API 的目的是為了給 client 調用
interface JsonPlaceHolderApi {
    // 查詢 posts
    @GET("posts")
    fun getPosts(): Call<List<Post>>

    // 查詢 comments
    @GET("comments")
    fun getComments(): Call<List<Comment>>

    // 查詢 posts?userId=2&_sort=id&_order=desc
    @GET("posts")
    fun getPosts(@Query("userId") userId: Int,
                 @Query("_sort") _sort: String,
                 @Query("_order") _order: String): Call<List<Post>>


}