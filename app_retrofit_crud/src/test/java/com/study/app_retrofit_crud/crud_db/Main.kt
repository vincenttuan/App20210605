package com.study.app_retrofit_crud.crud_db

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface JsonDBService {
    @GET("/users")
    fun getUsers(): Call<List<User>>

    @GET("/users/{id}")
    fun getUser(@Path("id") id: Int): Call<User>

    @POST("/users")
    fun createUser(@Body user: User): Call<User>

    @PUT("/users/{id}")
    fun updateUser(@Path("id") id: Int, @Body user: User): Call<User>

    @PATCH("/users/{id}")
    fun patchUser(@Path("id") id: Int, @Body user: User): Call<User>

    @DELETE("/users/{id}")
    fun deleteUser(@Path("id") id: Int): Call<Void>
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
        .baseUrl("http://localhost:3000/") // json-db 位置
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
    val api = retrofit.create(JsonDBService::class.java)

    // 查詢
    //println(api.getUsers().execute().isSuccessful)
    // 新增一筆
    // id=2 name=Mary age=19
    //val user = User(2, "Mary", 19)
    //println(api.createUser(user).execute().isSuccessful)

    // 修改 PUT   id = 2, name="Jane" , age = 20
    /*
    var user = api.getUser(2).execute().body()
    if(user != null) {
        user.name = "Jane"
        user.age = 20
        println(api.updateUser(2, user).execute().isSuccessful)
    }
    */
    // 修改 PATCH id = 2, age = 21
    //var user = User(null, null, 21)
    //println(api.patchUser(2, user).execute().isSuccessful)

    // 刪除 id = 2
    println(api.deleteUser(2).execute().isSuccessful)
}