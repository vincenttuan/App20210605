package com.study.app_notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val CHANNEL_1_ID = "channel1"
    val CHANNEL_2_ID = "channel2"
    var notificationManager: NotificationManagerCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 判斷版本 ( Android 8.0 (SDK26) 以上才有支援 channel
        // 註冊 channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                CHANNEL_1_ID, "Channel 1", NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "This is channel 1"
            val channel2 = NotificationChannel(
                CHANNEL_2_ID, "Channel 2", NotificationManager.IMPORTANCE_LOW
            )
            channel2.description = "This is channel 2"
            // 註冊 channel
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel1)
            manager.createNotificationChannel(channel2)
        }
        // 建構 notificationManager
        notificationManager = NotificationManagerCompat.from(this)
    }

    fun sendByChannel1(view: View) {
        val title: String = et_title.text.toString()
        val message: String = et_message.text.toString()
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_1_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle(title)
            .setContentText(message)
            .setContentInfo("2021-7-3 by vincent")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .build()
        notificationManager!!.notify(1001, notification)
    }

    fun sendByChannel2(view: View) {
        val title: String = et_title.text.toString()
        val message: String = et_message.text.toString()
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_2_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
        notificationManager!!.notify(1002, notification)
    }

}