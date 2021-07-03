package com.study.app_notification_firebase

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    var notificationManager: NotificationManagerCompat? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 建構 notificationManager
        notificationManager = NotificationManagerCompat.from(this)

        // Write a message to the database
        val database = Firebase.database
        val myMessageRef = database.getReference("message")

        bt_submit.setOnClickListener {
            val data = et_message.text.toString()
            myMessageRef.setValue(data)
        }

        // Read from the database
        myMessageRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.getValue<String>()
                tv_message.text = value
                sendByChannel1(value.toString())
                Log.d(TAG, "Value is: " + value)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }

    fun sendByChannel1(message: String) {
        val intent = Intent(this, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val title: String = "Firebase"
        val notification: Notification = NotificationCompat.Builder(this, App.CHANNEL_1_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setOngoing(true)
            .setContentTitle(title)
            .setContentText(message)
            .setSubText("2021-7-3")
            .setContentIntent(pIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()
        notificationManager!!.notify(1001, notification)
    }
}