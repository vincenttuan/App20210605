package com.study.app_retrofit_crud.crud

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

// API (Service)
interface JsonPlaceHolderService {
    @GET("posts/{id}")
    fun getPost(@Path("id") id: Int?): Call<Post?>?

    @POST("/posts")
    fun createPost(@Body post: Post): Call<Post>

    @FormUrlEncoded
    @POST("/posts")
    fun createPost(
        @Field("userId") userId: Int,
        @Field("title") title: String,
        @Field("body") body: String
    ): Call<Post>

    @PUT("/posts/{id}")
    fun updatePost(@Path("id") id: Int, @Body post: Post): Call<Post>

    @PATCH("/posts/{id}")
    fun patchPost(@Path("id") id: Int, @Body post: Post): Call<Post>
}

fun main() {
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
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
    val api = retrofit.create(JsonPlaceHolderService::class.java)

    println("CRUD...")
    // Read
    //val post = api.getPost(1)
    //println(post!!.execute().body())

    // Create
    //val post = Post(23, 0, "New Title", "New Body")
    //println(api.createPost(post).execute().isSuccessful)

    // Create 使用 url-encode
    //println(api.createPost(24, "New Title2", "New Body2").execute().isSuccessful)

    // Put 全部修改
    //val post = api.getPost(1)!!.execute().body()
    //println(post)
    /*
    if(post != null) {
        post.userId = 99
        post.title = "AAA"
        post.body = "BBB"
        println(api.updatePost(1, post).execute().isSuccessful)
   }
   */
    // Patch 部分修改(沒有傳的就不會更新)
    val post = Post(15, 1, "CCC", null)
    println(api.patchPost(1, post).execute().isSuccessful)

}