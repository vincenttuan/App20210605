package com.study.app_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.study.app_retrofit.manager.RetrofitManager
import com.study.app_retrofit.model.Comment
import com.study.app_retrofit.model.Photo
import com.study.app_retrofit.model.Post
import com.study.app_retrofit.model.users.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始配置 recyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewAdapter = RecyclerViewAdapter()
            adapter = recyclerViewAdapter
            val divider = DividerItemDecoration(applicationContext, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }

        GlobalScope.launch {
            val api = RetrofitManager.instance.api

            //val call: Call<List<Post>> = api.getPosts()
            // 方法一 :
            //val posts = call.execute().body()
            //Log.d("MainActivity", posts.toString())
            // 方法二 :
            //call.enqueue(getPosts())
            btn_posts.setOnClickListener {
                recyclerView.visibility = View.GONE
                ns_view.visibility = View.VISIBLE
                //api.getPosts().enqueue(getPosts())
                //api.getPosts(2, "id", "desc").enqueue(getPosts())
                //api.getPosts(arrayOf(2, 4), "id", "desc").enqueue(getPosts())
                api.getPost(2).enqueue(getPost())
            }
            btn_comments.setOnClickListener {
                recyclerView.visibility = View.GONE
                ns_view.visibility = View.VISIBLE
                //api.getComments().enqueue(getComments())
                //api.getComments("/posts/2/comments").enqueue(getComments())
                //api.getComments(3).enqueue(getComments())
                val params = HashMap<String, String>()
                params.put("postId", "4")
                params.put("_sort", "id")
                params.put("_order", "desc")
                api.getComments(params).enqueue(getComments())
            }
            btn_users.setOnClickListener {
                recyclerView.visibility = View.GONE
                ns_view.visibility = View.VISIBLE
                //api.getUsers().enqueue(getUsers())
                api.getUser(1).enqueue(getUser())
            }
            btn_photos.setOnClickListener {
                recyclerView.visibility = View.VISIBLE
                ns_view.visibility = View.GONE
                api.getPhotos().enqueue(getPhotos())
            }
        }

    }

    fun getPhotos(): Callback<List<Photo>> {
        val cb = object: Callback<List<Photo>> {
            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                if(!response.isSuccessful) {
                    // Ex: 404 找不到 page 的錯誤
                    Log.d("MainActivity", "Is not successful: ${response.code()}")
                    return
                }
                val photos = response.body()
                Log.d("MainActivity", photos!!.size.toString())
                Log.d("MainActivity", photos.toString())
                // UI 呈現
                runOnUiThread {
                    recyclerViewAdapter.setListData(photos)
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                Log.d("MainActivity", "Fail: ${t.message}")
            }

        }
        return cb;
    }

    fun getUser(): Callback<User> {
        val cb = object: Callback<User> {
            // Server 端有回應
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(!response.isSuccessful) {
                    // Ex: 404 找不到 page 的錯誤
                    Log.d("MainActivity", "Is not successful: ${response.code()}")
                    return
                }
                val user = response.body()
                Log.d("MainActivity", user.toString())
                // UI 呈現
                runOnUiThread {
                    tv_posts.text = user.toString()
                }

            }
            // 無法連線(Ex: 找不到主機 hostname)
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("MainActivity", "Fail: ${t.message}")
            }
        }
        return cb
    }

    fun getUsers(): Callback<List<User>> {
        val cb = object: Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(!response.isSuccessful) {
                    // Ex: 404 找不到 page 的錯誤
                    Log.d("MainActivity", "Is not successful: ${response.code()}")
                    return
                }
                val users = response.body()
                Log.d("MainActivity", users!!.size.toString())
                Log.d("MainActivity", users.toString())
                // UI 呈現
                runOnUiThread {
                    tv_posts.text = users!!.size.toString() + "\n" + users.toString()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("MainActivity", "Fail: ${t.message}")
            }

        }
        return cb;
    }


    fun getPost(): Callback<Post> {
        val cb = object: Callback<Post> {
            // Server 端有回應
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if(!response.isSuccessful) {
                    // Ex: 404 找不到 page 的錯誤
                    Log.d("MainActivity", "Is not successful: ${response.code()}")
                    return
                }
                val post = response.body()
                Log.d("MainActivity", post.toString())
                // UI 呈現
                runOnUiThread {
                    tv_posts.text = post.toString()
                }

            }
            // 無法連線(Ex: 找不到主機 hostname)
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("MainActivity", "Fail: ${t.message}")
            }
        }
        return cb
    }

    fun getPosts(): Callback<List<Post>> {
        val cb = object: Callback<List<Post>> {
            // Server 端有回應
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if(!response.isSuccessful) {
                    // Ex: 404 找不到 page 的錯誤
                    Log.d("MainActivity", "Is not successful: ${response.code()}")
                    return
                }
                val posts = response.body()
                Log.d("MainActivity", posts!!.size.toString())
                Log.d("MainActivity", posts.toString())
                // UI 呈現
                runOnUiThread {
                    tv_posts.text = posts!!.size.toString() + "\n" + posts.toString()
                }

            }
            // 無法連線(Ex: 找不到主機 hostname)
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.d("MainActivity", "Fail: ${t.message}")
            }
        }
        return cb
    }

    fun getComments(): Callback<List<Comment>> {
        val cb = object: Callback<List<Comment>> {
            // Server 端有回應
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if(!response.isSuccessful) {
                    // Ex: 404 找不到 page 的錯誤
                    Log.d("MainActivity", "Is not successful: ${response.code()}")
                    return
                }
                val comments = response.body()
                Log.d("MainActivity", comments!!.size.toString())
                Log.d("MainActivity", comments.toString())
                // UI 呈現
                runOnUiThread {
                    tv_posts.text = comments!!.size.toString() + "\n" + comments.toString()
                }

            }
            // 無法連線(Ex: 找不到主機 hostname)
            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.d("MainActivity", "Fail: ${t.message}")
            }
        }
        return cb
    }
}


