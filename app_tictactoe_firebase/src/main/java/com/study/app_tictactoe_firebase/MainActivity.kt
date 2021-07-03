package com.study.app_tictactoe_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val database = Firebase.database
    val myTTTRef = database.getReference("ttt/game")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_mark.setOnClickListener {
            val mark = bt_mark.text.toString()
            if(mark.equals("O")) {
                bt_mark.text = "X"
            } else {
                bt_mark.text = "O"
            }
        }
    }
}