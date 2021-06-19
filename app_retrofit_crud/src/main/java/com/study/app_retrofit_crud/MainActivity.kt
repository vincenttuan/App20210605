package com.study.app_retrofit_crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.study.app_retrofit_crud.manager.JsonDBManager
import com.study.app_retrofit_crud.model.Employee
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.RowClickListener {
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始配置 recyclerView
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewAdapter = RecyclerViewAdapter(this@MainActivity)
            adapter = recyclerViewAdapter
            val divider = DividerItemDecoration(applicationContext, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(divider)
        }

        GlobalScope.launch {
            val api = JsonDBManager.instance.api
            val employees: List<Employee>? = api.getEmployees().execute().body()
            runOnUiThread {
                title = "員工筆數: ${employees!!.size}"
                recyclerViewAdapter.setListData(employees!!)
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }

        bt_update.setOnClickListener {
            GlobalScope.launch {
                val api = JsonDBManager.instance.api
                // Update

                val employees: List<Employee>? = api.getEmployees().execute().body()
                runOnUiThread {
                    title = "員工筆數: ${employees!!.size}"
                    recyclerViewAdapter.setListData(employees!!)
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onItemClickListener(employee: Employee) {
        Toast.makeText(this, employee.toString(), Toast.LENGTH_SHORT).show()
        et_basic.setText(employee.id)
        et_basic.setText(employee.salary.basic.toString())
    }

}