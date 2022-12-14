package com.app.clockmanager.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LifecycleService
import com.app.clockmanager.R
import com.app.clockmanager.playSound.DefaultSoundPlayer
import com.app.clockmanager.playSound.SoundPlayer
import com.app.clockmanager.ui.WakeUpActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class AlarmService : LifecycleService() {
    private val player: SoundPlayer by lazy { DefaultSoundPlayer(this) }
    private val notificationManager: NotificationManager
        get() = getSystemService(NotificationManager::class.java)
    private val stopServiceIntent by lazy {
        PendingIntent.getService(
            this,
            Random.nextInt(),
            Intent(this, AlarmService::class.java).apply {
                action = ServiceCommands.STOP.toString()
            },
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun onCreate() {
        super.onCreate()
        player.prepareToPlay()
        showNotification()
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        Toast.makeText(
            this,
            "Service starts command",
            Toast.LENGTH_SHORT
        ).show()
        Log.d("MyLogs", "Service starts command")

        intent?.let {
            Log.d("MyLogs", "Action = ${it.action}")

            when (it.action) {
                ServiceCommands.PLAY.toString() -> {
                    player.play()
                }

                ServiceCommands.STOP.toString() -> {
                    player.run {
                        stop()
                        release()
                    }

                    val notificationManager =
                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.cancelAll()

                    stopSelf()
                }
                else -> {}
            }
        }

        return START_STICKY
    }

    private fun showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannel(channel)
        }

        val currentDate = Date()
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val builder = NotificationCompat.Builder(this, NOTIFICATION_BUILDER_ID)
            .setContentTitle("???????????????????? ??????")
            .setContentText(timeFormat.format(currentDate))
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setShowWhen(false)
            .setChannelId(NOTIFICATION_CHANNEL_ID)
            .addAction(
                NotificationCompat.Action(
                    R.drawable.ic_baseline_arrow_right_alt_24,
                    "Stop",
                    stopServiceIntent
                )
            ).setDeleteIntent(
                stopServiceIntent
            )

        NotificationManagerCompat.from(this)
            .notify(NOTIFICATION_ID, builder.build())
    }

    override fun onDestroy() {
        super.onDestroy()

        player.release()
    }

    companion object {
        //const val DURATION_KEY = "duration_key"
        const val NOTIFICATION_BUILDER_ID = "notification_builder"
        const val NOTIFICATION_ID = 1
        const val NOTIFICATION_CHANNEL_ID = "notification_channel_1"
        const val NOTIFICATION_CHANNEL_NAME = "notification_name"
    }
}