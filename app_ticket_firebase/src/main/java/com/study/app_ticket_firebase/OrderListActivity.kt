package com.study.app_ticket_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class OrderListActivity : AppCompatActivity() {
    lateinit var userName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)
        // 設定 app title
        title = resources.getString(R.string.activity_order_list_title_text)
        // 取得使用者名稱 (上一頁傳來的 userName 參數資料)
        userName = intent.getStringExtra("userName").toString()
        // 判斷是否有 userName 的資料
        if (userName == null || userName.equals("") || userName.equals("null")) {
            title = String.format(title.toString(), resources.getString(R.string.all_order_text))
        } else {
            title = String.format(title.toString(), userName)
        }



    }
}