package com.study.app_tictactoe_firebase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val database = Firebase.database
    val myTTTRef = database.getReference("ttt/game")
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        bt_mark.setOnClickListener {
            val mark = bt_mark.tag.toString()
            if(mark.equals("O")) {
                bt_mark.text = "X"
                bt_mark.tag = "X"
            } else {
                bt_mark.text = "O"
                bt_mark.tag = "O"
            }
        }

        myTTTRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val game = snapshot.children
                Log.d("MainActivity", game.toString())
                game.forEach {
                    Log.d("MainActivity", it.key + ":" + it.value)
                    val id = resources.getIdentifier(it.key, "id", context.packageName)
                    findViewById<Button>(id).text = it.value.toString()
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun tttOnClick(view: View) {
        val tag = view.getTag().toString()
        val path = "b" + tag
        val mark = bt_mark.tag.toString()
        myTTTRef.child(path).setValue(mark)
    }

    fun tttResetOnClick(view: View) {
        for (i in 1..9) {
            val path = "b" + i
            myTTTRef.child(path).setValue("")
        }
    }
}