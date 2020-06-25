package com.example.schedulelocalnotifications

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity() {
    companion object {
        private val sharedPrefFile = "kotlinsharedpreference"
        val NOTIFICATION_CHANNEL_ID = "10001"
        private val default_notification_channel_id = "default"
        lateinit var context: Context
        var counter = 1
        var counterList = 0
        var timesArrayList: ArrayList<Long> = ArrayList()
        fun scheduleNotification(notification: Notification) {
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
            val timesArrayList2: ArrayList<Long> =
                sharedPreferences.getStringSet("key", null)?.map { it.toLong() } as ArrayList<Long>
            var timeStamp: Long = 0
            if (timesArrayList2.isNotEmpty()) {
                timeStamp = timesArrayList2.min()!!
                timesArrayList2.remove(timeStamp)
                if (timeStamp.toString().isNotEmpty()) {
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putStringSet("key", timesArrayList2.map { it.toString() }.toMutableSet())
                    editor.apply()
                }
            }
            val notificationIntent = Intent(context, MyNotificationPublisher::class.java)
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, counter)
            notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification)

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            AlarmManagerCompat.setExact(
                alarmManager!!,
                AlarmManager.RTC_WAKEUP,
                timeStamp,
                pendingIntent
            )
            counter++
            Log.e("scheduleNotification", "Notification Scheduled Done       ".plus(timeStamp))
            /*          val notificationIntent = Intent(context, MyNotificationPublisher::class.java)
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
                      alarmManager[AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis] = pendingIntent*/
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

        fun initialize() {
            timesArrayList.add(1593078420000)
            timesArrayList.add(1593079020000)
            timesArrayList.add(1593177780000)
            timesArrayList.add(1593178380000)
            timesArrayList.add(1593189960000)
            timesArrayList.add(1593190560000)
            timesArrayList.add(1593195780000)
            timesArrayList.add(1593196380000)
            timesArrayList.add(1593220080000)
            timesArrayList.add(1593220680000)
            timesArrayList.add(1593251220000)
            timesArrayList.add(1593251820000)
            timesArrayList.add(1593264180000)
            timesArrayList.add(1593264780000)
            timesArrayList.add(1593276360000)
            timesArrayList.add(1593276960000)
            timesArrayList.add(1593282180000)
            timesArrayList.add(1593282780000)
            timesArrayList.add(1593306480000)
            timesArrayList.add(1593307080000)
            timesArrayList.add(1593337620000)
            timesArrayList.add(1593338220000)
            timesArrayList.add(1593350580000)
            timesArrayList.add(1593351180000)
            timesArrayList.add(1593362760000)
            timesArrayList.add(1593363360000)
            timesArrayList.add(1593368580000)
            timesArrayList.add(1593369180000)
            val sharedPreferences: SharedPreferences =
                context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            val set: MutableSet<String> = timesArrayList.map { it.toString() }.toMutableSet()
            editor.putStringSet("key", set)
            editor.apply()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        initialize()
        getNotification("10 second delay")
            ?.let { scheduleNotification(it) }
    }


    override fun onDestroy() {
        super.onDestroy()
        context = applicationContext
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.action_5 -> {
                getNotification("5 second delay")?.let { scheduleNotification(it) }
                true
            }
            R.id.action_10 -> {
                getNotification("10 second delay")?.let { scheduleNotification(it) }
                true
            }
            R.id.action_30 -> {
                getNotification("30 second delay")?.let { scheduleNotification(it) }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
