package com.study.app_login_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val message = intent.getStringExtra("message")
        if(message != null) {
            tv_result.text = message
        }
    }

    fun back(view: View) {
        finish()
    }

}