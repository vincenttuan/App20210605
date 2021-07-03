package com.study.app_notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class App: Application() {
    companion object {
        val CHANNEL_1_ID = "channel1"
        val CHANNEL_2_ID = "channel2"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    fun createNotificationChannels() {
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
    }
}