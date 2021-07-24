package com.study.app_ticket_firebase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_console.*

class ConsoleActivity : AppCompatActivity() {
    private val database = Firebase.database
    private val myRef = database.getReference("ticketsStock")
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

        // Read from the database
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                children.forEach {
                    when(it.key.toString()) {
                        "discount" -> et_discount.setText(it.value.toString())
                        "price" -> et_price.setText(it.value.toString())
                        "totalAmount" -> et_amount.setText(it.value.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun update(view: View) {
        val tagName = view.tag.toString()
        Toast.makeText(context, tagName, Toast.LENGTH_SHORT).show()
    }
}