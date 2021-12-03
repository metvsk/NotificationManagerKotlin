package com.chillandcode.notificationmanagerkotlin

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.chillandcode.notificationmanagerkotlin.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
// a simple test implementation for notification generation
class MainActivity : AppCompatActivity() {
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var notificationManager: NotificationManager
    private lateinit var builder: Notification.Builder
    private val channelId = "12345"
    private lateinit var b: ActivityMainBinding
    private val description = "Test Notification"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
//requesting notification manager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        b.button.setOnClickListener {
            val intent = Intent(this, LauncherActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel =
                    NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.lightColor = Color.BLUE
                notificationChannel.enableVibration(true)
                notificationManager.createNotificationChannel(notificationChannel)
                builder = Notification.Builder(this, channelId).setContentTitle(
                    "NOTIFICATION USING " +
                            "KOTLIN"
                ).setContentText("Test Notification")
                    .setSmallIcon(R.drawable.ic_launcher_foreground).setLargeIcon(
                        BitmapFactory.decodeResource(
                            this.resources, R.drawable
                                .ic_launcher_background
                        )
                    ).setContentIntent(pendingIntent)
            } else {
                Snackbar.make(
                    it,
                    "Android Version Not Supported Implement your own Notification after studying",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            notificationManager.notify(11123, builder.build())
        }
    }
}