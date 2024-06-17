package co.za.openwindow.tea_expanded.services

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import co.za.openwindow.tea_expanded.MainActivity
import co.za.openwindow.tea_expanded.R

class MyNotification (
    private val context: Context
){

    private var channelID = "Notification100"
    private var channelName = "MyNotification"

    val notificationManager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private lateinit var notificationChannel: NotificationChannel
    private lateinit var notificationBuilder: NotificationCompat.Builder
    fun showNotification(
        title: String,
        message: String
    ){ //<-- trigger the display of my notification

        //Check SDK version first
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //1. setup notification channel
            notificationChannel = NotificationChannel(
                channelID,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannel(notificationChannel)

            //2. building our notification builder
            //Identify the notification on tap
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            notificationBuilder = NotificationCompat.Builder(context, channelID)
            notificationBuilder.setSmallIcon(R.drawable.ic_launcher_foreground) //<-- sets the logo
            notificationBuilder.setContentTitle(title)
            notificationBuilder.setContentText(message)
            notificationBuilder.setAutoCancel(true)
            notificationBuilder.setContentIntent(pendingIntent)

            //3. Show/trigger the display of notification
            with(NotificationManagerCompat.from(context)){

                if(ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                    Log.d("AAA notification trigger", "DID NOT GIVE PERMISSION")
                }

                notify(100, notificationBuilder.build())
            }

        }

    }

}