package com.example.weather4u.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.weather4u.R
import com.example.weather4u.util.Constant.NOTIFICATION_ID
import com.example.weather4u.util.Constant.NOTIFICATION_TITLE

object Notification {
    fun createNotificationChannel(context: Context) {
        // Make a channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            val name = Constant.NOTIFICATION_CHANNEL_NAME
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(Constant.CHANNEL_ID, name, importance)
            // Add the channel
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
            notificationManager?.createNotificationChannel(channel)
        }
    }

    fun createNotification(context: Context, contentView: RemoteViews): Notification {
        // Create the notification
        val builder = NotificationCompat.Builder(context, Constant.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(NOTIFICATION_TITLE)
            //  .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))
            .setCustomContentView(contentView)

        // Show the notification
        return builder.build()
    }
}