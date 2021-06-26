package com.study.app_databinding_recyclerview.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.javafaker.Faker
import com.study.app_databinding_recyclerview.model.Post

class PostViewModel: ViewModel() {
    var posts = MutableLiveData<MutableList<Post>>()
    init {
        posts.value = ArrayList()
    }

    fun defaultData() {
        if(posts.value!!.size == 0) {
            val faker = Faker()
            val tempList = mutableListOf<Post>()
            tempList.add(Post(1, faker.book().title(), faker.book().author()))
            tempList.add(Post(2, faker.book().title(), faker.book().author()))
            tempList.add(Post(3, faker.book().title(), faker.book().author()))
            posts.postValue(tempList)
        }
    }
}