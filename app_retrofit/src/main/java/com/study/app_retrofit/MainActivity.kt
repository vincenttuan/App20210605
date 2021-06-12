package com.study.app_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.study.app_retrofit.manager.RetrofitManager
import com.study.app_retrofit.model.Post
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            val api = RetrofitManager.instance.api
            val call: Call<List<Post>> = api.getPosts()
            // 方法一 :
            //val posts = call.execute().body()
            //Log.d("MainActivity", posts.toString())
            // 方法二 :
            call.enqueue(getPosts())
        }

    }

    fun getPosts(): Callback<List<Post>> {
        val cb = object: Callback<List<Post>> {
            // Server 端有回應
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if(!response.isSuccessful) {
                    Log.d("MainActivity", "Is not successfil: ${response.code()}")
                    return
                }
                val posts = response.body()
                Log.d("MainActivity", posts!!.size.toString())
                Log.d("MainActivity", posts.toString())
            }
            // 無法連線(Ex: 404 找不到 page 的錯誤)
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.d("MainActivity", "Fail: ${t.message}")
            }
        }
        return cb
    }
}


