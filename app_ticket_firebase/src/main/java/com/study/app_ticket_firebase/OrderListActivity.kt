package com.study.app_ticket_firebase

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.study.app_ticket_firebase.adapter.RecyclerViewAdapter
import com.study.app_ticket_firebase.models.Order
import com.study.app_ticket_firebase.models.Ticket
import kotlinx.android.synthetic.main.activity_order_list.*

class OrderListActivity : AppCompatActivity(), RecyclerViewAdapter.OrderOnItemClickListener {
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
            lateinit var orderList: MutableList<Order>
            override fun onDataChange(snapshot: DataSnapshot) {
                orderList = mutableListOf<Order>()
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
            recyclerViewAdapter = RecyclerViewAdapter(this@OrderListActivity)
            adapter = recyclerViewAdapter
        }

    }

    // 按一下可以產生 QR-Code
    override fun onItemClickListener(order: Order) {
        // 產生 Json
        val orderJsonString = Gson().toJson(order).toString()
        Toast.makeText(context, "click:" + orderJsonString, Toast.LENGTH_SHORT).show()
        // 產生 OR-Code
        val writer = QRCodeWriter()
        // 產生 bit 矩陣資料
        val bitMatrix = writer.encode(orderJsonString, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        // 產生 bitmap 空間
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        // 將 bitMatrix 矩陣資料 注入到 bitmap 空間
        for(x in 0 until width) {
            for(y in 0 until height) {
                // 有資料放黑色反之白色
                bitmap.setPixel(x, y, if(bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        // 建立自製 ImageView
        val qrcodeImageView = ImageView(context)
        qrcodeImageView.setImageBitmap(bitmap)
        // 建立 AlterDialog 顯示 bitmap 圖像
        AlertDialog.Builder(context)
            .setView(qrcodeImageView)
            .create()
            .show()
    }

    // 長按一下可以取消訂單(退票)
    override fun onItemLongClickListener(order: Order) {
        // 組合訂單路徑
        val path = "transaction/" + order.ticket.userName + "/" + order.key
        // 刪除訂單路徑資料
        myRef.child(path).removeValue()

        Toast.makeText(context, "long click:" + order.toString(), Toast.LENGTH_SHORT).show()
    }
}