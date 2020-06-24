package com.example.schedulelocalnotifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import com.example.schedulelocalnotifications.MainActivity.Companion.NOTIFICATION_CHANNEL_ID


class MyNotificationPublisher : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        MainActivity.context = context
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: Notification? =
            intent.getParcelableExtra(NOTIFICATION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "NOTIFICATION_CHANNEL_NAME",
                importance
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val id = intent.getIntExtra(NOTIFICATION_ID, 0)
        notificationManager.notify(id, notification)
        val timer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                MainActivity.getNotification("10 second delay")
                    ?.let { MainActivity.scheduleNotification(it, 0) }
            }

            override fun onFinish() {

            }
        }
        timer.start()

    }

    companion object {
        var NOTIFICATION_ID = "notification-id"
        var NOTIFICATION = "notification"
    }
}