package com.study.app_retrofit_animal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.study.app_retrofit.manager.RetrofitManager
import com.study.app_retrofit_animal.model.Animal
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
            val uid = resources.getString(R.string.uid)
            val animals: List<Animal>? = api.getAnimals(uid).execute().body()
            Log.d("MainActivity", animals.toString())
            runOnUiThread {
                recyclerViewAdapter.setListData(animals!!)
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }
    }
}