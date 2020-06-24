package com.example.schedulelocalnotifications

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity() {
    companion object {
        val NOTIFICATION_CHANNEL_ID = "10001"
        private val default_notification_channel_id = "default"
        lateinit var context:Context
        fun scheduleNotification(notification: Notification, delay: Int) {
            val notificationIntent = Intent(context, MyNotificationPublisher::class.java)
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, 1)
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val futureInMillis = SystemClock.elapsedRealtime() + delay
            val alarmManager = (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager)
            alarmManager[AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis] = pendingIntent
        }

        fun getNotification(content: String): Notification? {
            val builder =
                NotificationCompat.Builder(context, default_notification_channel_id)
            builder.setContentTitle("Scheduled Notification")
            builder.setContentText(content)
            builder.setSmallIcon(R.drawable.ic_launcher_background)
            builder.setAutoCancel(true)
            builder.setChannelId(NOTIFICATION_CHANNEL_ID)
            return builder.build()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context=this
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.action_5 -> {
                getNotification("5 second delay")?.let { scheduleNotification(it, 5000) }
                true
            }
            R.id.action_10 -> {
                getNotification("10 second delay")?.let { scheduleNotification(it, 10000) }
                true
            }
            R.id.action_30 -> {
                getNotification("30 second delay")?.let { scheduleNotification(it, 30000) }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
