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

    var counter  = 0
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
        counter++
        val timer = object : CountDownTimer(10000, 10000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                MainActivity.context = context
                MainActivity.initialize()
                MainActivity.getNotification("10 second delay")
                    ?.let { MainActivity.scheduleNotification(it, MainActivity.timesArrayList[counter]) }
//                start()
            }
        }
        timer.start()

    }

    companion object {
        var NOTIFICATION_ID = "notification-id"
        var NOTIFICATION = "notification"
    }
}