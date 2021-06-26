package com.study.app_databinding_basic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
// 根據 layout 配置是否支援 databinding 來動態產生
import com.study.app_databinding_basic.databinding.ActivityMainBinding
import com.study.app_databinding_basic.model.Post
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 取代傳統 setContentView(R.layout.activity_main) 的配置
        mBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        // 建立資料
        val post = Post(12, "Kotlin", "Java")
        // 繫結資料 Binding data
        mBinding.post = post
    }

    fun click(view: View) {
        val post = Post(Random.nextInt(100), "Python", "Vincent")
        Toast.makeText(this, post.toString(), Toast.LENGTH_SHORT).show()
        mBinding.post = post
    }
}