package com.study.app_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.study.app_retrofit.manager.RetrofitManager
import com.study.app_retrofit.model.Post
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            val api = RetrofitManager.instance.api
            val posts: Call<List<Post>> = api.getPosts()
            val postList = posts.execute().body()
            Log.d("MainActivity", postList.toString())

        }

    }
}