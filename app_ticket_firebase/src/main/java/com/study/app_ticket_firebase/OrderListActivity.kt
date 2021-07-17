package com.study.app_ticket_firebase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.study.app_ticket_firebase.models.Order
import com.study.app_ticket_firebase.models.Ticket
import kotlinx.android.synthetic.main.activity_order_list.*

class OrderListActivity : AppCompatActivity() {
    val database = Firebase.database
    val myRef = database.getReference("ticketsStock")

    lateinit var context: Context
    lateinit var userName: String

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
            override fun onDataChange(snapshot: DataSnapshot) {
                val orderList = mutableListOf<Order>()
                val children = snapshot.children
                children.forEach {
                    if (it.key.toString() == "transaction") {
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
                }
                tv_info.text = orderList.toString()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }
}