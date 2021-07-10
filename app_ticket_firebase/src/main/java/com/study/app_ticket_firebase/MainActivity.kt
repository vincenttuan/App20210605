package com.study.app_ticket_firebase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val database = Firebase.database
    val myRef = database.getReference("ticketsStock")

    lateinit var context: Context
    lateinit var userName: String
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
        // Read from the database
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                children.forEach {
                    when (it.key.toString()) {
                        "discount" -> { }
                        "price" -> { }
                        "totalAmount" -> { }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
}