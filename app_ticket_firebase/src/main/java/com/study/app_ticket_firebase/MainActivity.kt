package com.study.app_ticket_firebase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.study.app_ticket_firebase.models.TicketsStock
import com.study.app_ticket_firebase.services.TicketService
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    val database = Firebase.database
    val myRef = database.getReference("ticketsStock")

    lateinit var context: Context
    lateinit var userName: String
    lateinit var ticketsStock: TicketsStock

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        context = this

        // Get userName
        userName = intent.getStringExtra("userName").toString()

        // Set activity title
        title = String.format(title.toString(), userName)
        // 雲端購票 - %s
        // new TicketsStock
        ticketsStock = TicketsStock(0.0, 0, 0)

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                children.forEach {
                    when (it.key.toString()) {
                        "discount" -> ticketsStock.discount = it.value.toString().toDouble()
                        "price" -> ticketsStock.price = it.value.toString().toInt()
                        "totalAmount" -> ticketsStock.totalAmount = it.value.toString().toInt()
                    }
                }
                // refresh ui
                refreshUI()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        // clear 訂單資料
        clear(null)
    }

    fun refreshUI() {
        tv_ticket_discount.text = ticketsStock.discount.toString()
        tv_ticket_price.text = ticketsStock.price.toString()
        tv_total_amount.text = ticketsStock.totalAmount.toString()
    }

    fun clear(view: View?) {
        // 清除結帳資訊內容
        var result =
            resources.getString(R.string.submit_detail_txt) // 總張數：%d\n來回票：%d\n單程票：%d\n總金額：$%d
        tv_result.text = String.format(result, 0, 0, 0, 0)
        et_all_tickets.setText("0") // 購買張數清除
        et_round_trip.setText("0")  // 來回組數清除
        tv_warning.text = ""       // 警告訊息清除

    }

    // 購票流程(按下購買鈕)
    fun buyTicket(view: View) {
        // 檢查 UI 欄位資料
        if(et_all_tickets.text.toString().length == 0) {
            return
        }
        if(et_round_trip.text.toString().length == 0) {
            return
        }
        // 進行購票
        val allTickets = et_all_tickets.text.toString().toInt()
        val roundTrip = et_round_trip.text.toString().toInt()
        try {
            TicketService.errorMessages = resources.getStringArray(R.array.exception_message_array)
            val ticket = TicketService().submit(allTickets, roundTrip, userName, ticketsStock)
            if (ticket != null) {
                var result = resources.getString(R.string.submit_detail_txt)
                tv_result.text = String.format(
                    result,
                    ticket.allTickets,
                    ticket.roundTrip,
                    ticket.oneWay,
                    ticket.total
                )

                // 通知 firebase ----------------------
                // 1. 變更 totalAmount 剩餘張數
                val updateAmount = ticketsStock.totalAmount - ticket.allTickets
                myRef.child("totalAmount").setValue(updateAmount)

                // 2. 新增訂單紀錄資料
                val sdf = SimpleDateFormat("yyyyMMddHHmmssSSS")
                val dateString = sdf.format(Date())
                val subPath = "transaction/" + userName + "/" + dateString + "/"
                myRef.child(subPath + "allTickets").setValue(ticket.allTickets)
                myRef.child(subPath + "roundTrip").setValue(ticket.roundTrip)
                myRef.child(subPath + "oneWay").setValue(ticket.oneWay)
                myRef.child(subPath + "total").setValue(ticket.total)

                // 購買成功訊息
                tv_warning.text = resources.getString(R.string.warning_txt)
            }
        } catch (e: Exception) {
            tv_warning.text = e.message
        }

    }

    fun viewOrderList(view: View) {
        val intent = Intent(context, OrderListActivity::class.java)
        // 將 userName 帶給 OrderListActivity
        intent.putExtra("userName", userName)
        startActivity(intent)
    }
}