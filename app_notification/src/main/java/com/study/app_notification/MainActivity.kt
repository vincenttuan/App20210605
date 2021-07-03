package com.study.app_notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var notificationManager: NotificationManagerCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 建構 notificationManager
        notificationManager = NotificationManagerCompat.from(this)

        // 一但進入先刪除 1001
        //notificationManager!!.cancel(1001)
    }

    fun continueByChannel2(view: View) {
        for(i in 1..5) {
            val title: String = et_title.text.toString() + ":" + i.toString()
            val message: String = et_message.text.toString() + ":" + i.toString()
            val notification: Notification = NotificationCompat.Builder(this, App.CHANNEL_2_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build()
            notificationManager!!.notify(i, notification)
        }
    }

    fun deleteByChannel1(view: View) {
        notificationManager!!.cancel(1001)
    }

    fun sendByChannel1(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val title: String = et_title.text.toString()
        val message: String = et_message.text.toString()
        val notification: Notification = NotificationCompat.Builder(this, App.CHANNEL_1_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setOngoing(true)
            .setContentTitle(title)
            .setContentText(message)
            .setSubText("2021-7-3 by vincent")
            .setContentIntent(pIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()
        notificationManager!!.notify(1001, notification)
    }

    fun sendByChannel2(view: View) {
        val title: String = et_title.text.toString()
        val message: String = et_message.text.toString()
        val notification: Notification = NotificationCompat.Builder(this, App.CHANNEL_2_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
        notificationManager!!.notify(1002, notification)
    }

}