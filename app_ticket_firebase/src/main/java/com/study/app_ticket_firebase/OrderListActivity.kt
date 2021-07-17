package com.study.app_ticket_firebase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.study.app_ticket_firebase.adapter.RecyclerViewAdapter
import com.study.app_ticket_firebase.models.Order
import com.study.app_ticket_firebase.models.Ticket
import kotlinx.android.synthetic.main.activity_order_list.*

class OrderListActivity : AppCompatActivity() {
    val database = Firebase.database
    val myRef = database.getReference("ticketsStock")

    lateinit var context: Context
    lateinit var userName: String
    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        context = this
        // 設定 app title
        title = resources.getString(R.string.activity_order_list_title_text)
        // 取得使用者名稱 (上一頁傳來的 userName 參數資料)
        userName = intent.getStringExtra("userName").toString()
        // 判斷是否有 userName 的資料
        title = if (userName == null || userName.equals("") || userName.equals("null")) {
            String.format(title.toString(), resources.getString(R.string.all_order_text))
        } else {
            String.format(title.toString(), userName)
        }

        // Read from the databse
        myRef.addValueEventListener(object : ValueEventListener {
            val orderList = mutableListOf<Order>()
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                children.forEach {
                    if (it.key.toString() == "transaction") {
                        // 未指名 userName
                        if (userName == null || userName.equals("") || userName.equals("null")) {
                            it.children.forEach { record ->
                                addRecord(it, record.key.toString())
                            }
                        } else { // 有指名 userName
                            addRecord(it, userName)
                        }
                    }
                }
                tv_info.text = orderList.toString()
                // 更新 recycler view 的 orderList 資料
                recyclerViewAdapter.setOrderList(orderList)
                // 通知變更(UI 刷新)
                recyclerViewAdapter.notifyDataSetChanged()
            }

            private fun addRecord(it: DataSnapshot, userName: String) {
                it.child(userName).children.forEach {
                    val key = it.key.toString()
                    val allTickets = it.child("allTickets").value.toString().toInt()
                    val roundTrip = it.child("roundTrip").value.toString().toInt()
                    val oneWay = it.child("oneWay").value.toString().toInt()
                    val total = it.child("total").value.toString().toInt()
                    val ticket = Ticket(userName, allTickets, roundTrip, oneWay, total)
                    val order = Order(key, ticket)
                    orderList.add(order)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        // Init RecyclerView
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = RecyclerViewAdapter()
            adapter = recyclerViewAdapter
        }

    }
}