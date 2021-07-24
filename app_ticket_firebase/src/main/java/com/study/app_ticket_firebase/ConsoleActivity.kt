package com.study.app_ticket_firebase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class ConsoleActivity : AppCompatActivity() {
    private lateinit var userName: String
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_console)
        // 在上方 ActionBar 會顯示 <- 返回箭頭
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        context = this
        // Get userName
        userName = intent.getStringExtra("userName").toString()

        // Set activity title
        title = String.format(title.toString(), userName)
    }

    fun update(view: View) {
        val tagName = view.tag.toString()
        Toast.makeText(context, tagName, Toast.LENGTH_SHORT).show()
    }
}