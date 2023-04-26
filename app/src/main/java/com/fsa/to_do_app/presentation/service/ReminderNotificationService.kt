package com.fsa.to_do_app.presentation.service

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.fsa.to_do_app.util.CHANNEL_ID

class ReminderNotificationService(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    fun showNotification(taskContent:String) {
        var notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("meow")
            .setContentText("go meow")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(1, notification)

    }
}