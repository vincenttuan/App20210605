package com.study.app_databinding_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.databinding.DataBindingUtil
import com.study.app_databinding_retrofit.databinding.ActivityMainBinding
import com.study.app_databinding_retrofit.manager.JsonDBManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var mBinging: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        mBinging = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

    }

    fun click(view: View) {
        GlobalScope.launch {
            val api = JsonDBManager.instance.api
            val count = api.getPosts().execute().body()!!.size
            val id = Random.nextInt(count) + 1
            val post = api.getPost(id).execute().body()
//            runOnUiThread {
//                mBinging.post = post
//            }
            mBinging.post = post
        }
    }
}