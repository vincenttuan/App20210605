package com.study.app_databinding_recyclerview_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.javafaker.Faker
import com.study.app_databinding_recyclerview_retrofit.manager.JsonDBManager
import com.study.app_databinding_recyclerview_retrofit.model.Post
import com.study.app_databinding_recyclerview_retrofit.viewmodel.PostViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        viewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        viewModel.posts.observe(this, Observer {
            GlobalScope.launch {
                recyclerView.adapter = PostAdapter(it)
            }

        })

    }

    fun add(view: View) {
        GlobalScope.launch {
            val api = JsonDBManager.instance.api
            val faker = Faker()
            val id = Random.nextInt(1000)
            val post = Post(id, faker.book().title(), faker.book().author())
            val status = api.addPost(post).execute().isSuccessful
            runOnUiThread {
                Toast.makeText(applicationContext, status.toString(), Toast.LENGTH_SHORT).show()
                viewModel.posts.value!!.add(post)
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }

    fun list(view: View) {
        GlobalScope.launch {
            val api = JsonDBManager.instance.api
            val posts:List<Post> = api.getPosts().execute().body()!!
            runOnUiThread {
                //viewModel.posts.postValue(posts as MutableList<Post>?)
                recyclerView.adapter = PostAdapter(posts)
            }
        }

    }


    class PostAdapter(private var list: List<Post>) :
        RecyclerView.Adapter<PostAdapter.ViewHolder>() {

        inner class ViewHolder(var dataBinding: ViewDataBinding) :
            RecyclerView.ViewHolder(dataBinding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding: ViewDataBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_row, parent, false
            )
            return ViewHolder(binding);
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val binding: ViewDataBinding = holder.dataBinding
            // 数据绑定库在該 App Module 中生成一个名为 BR 的类，其中包含用于数据绑定的资源的 ID。
            binding.setVariable(BR.post, list[position])

        }

        override fun getItemCount(): Int {
            return list.size
        }

    }

}