package com.study.app_databinding_recyclerview_retrofit.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.javafaker.Faker
import com.study.app_databinding_recyclerview_retrofit.model.Post
import kotlin.random.Random

class PostViewModel: ViewModel() {
    var posts = MutableLiveData<MutableList<Post>>()
    init {
        posts.value = ArrayList()
    }


}