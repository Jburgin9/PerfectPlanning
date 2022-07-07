package com.example.myplanner

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

const val channelId = "channel1"
const val notificationId = 1
class NotificationReceiver: BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_baseline_notifications)
            .setContentTitle(intent.getStringExtra("title"))
            .setContentText(intent.getStringExtra("description"))
            .setSubText(intent.getStringExtra("desc"))
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationId, notification)

    }
}