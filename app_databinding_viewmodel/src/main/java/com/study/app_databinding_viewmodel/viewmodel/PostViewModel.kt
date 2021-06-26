package com.study.app_databinding_viewmodel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.study.app_databinding_viewmodel.model.Post
import kotlin.random.Random

class PostViewModel : ViewModel() {
    var post = MutableLiveData<Post>()

    fun click() {
        post.value = Post(
            Random.nextInt(100),
            "Java",
            "Python"
        )
    }
}