package com.fsa.to_do_app.presentation.service

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.fsa.to_do_app.R
import com.fsa.to_do_app.util.CHANNEL_ID
import java.text.SimpleDateFormat
import java.util.*

class ReminderNotificationService(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(message: String, title: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_app_notification)
            .setContentTitle(title)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(createNotificationId(), notification)

    }

    private fun createNotificationId(): Int {
        val now = Date()
        return SimpleDateFormat("ddHHmmss", Locale.US).format(now).toInt()
    }
}

