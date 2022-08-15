package com.app.clockmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log
import android.widget.Toast
import com.app.clockmanager.data.Alarm
import com.app.clockmanager.services.AlarmService
import com.app.clockmanager.services.ServiceCommands
import com.app.clockmanager.ui.MainActivity
import com.app.clockmanager.ui.WakeUpActivity
import java.text.SimpleDateFormat
import java.util.*

class AlarmBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            WAKE_LOCK_TAG
        )

        wakeLock.acquire(30_000)
        context.startService(
            Intent(context, AlarmService::class.java).apply {
                action = ServiceCommands.PLAY.toString()
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        )

        Toast.makeText(
            context,
            "Broadcast received",
            Toast.LENGTH_LONG
        ).show()

        Log.d("MyLogs", "Broadcast received")

        wakeLock.release()
    }

    fun setAlarm(context: Context, alarm: Alarm): PendingIntent {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val broadcastIntent = Intent(context, AlarmBroadcast::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            broadcastIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val activityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val info = AlarmManager.AlarmClockInfo(
            alarm.startTime,
            PendingIntent.getActivity(
                context,
                1,
                activityIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
        )

        alarmManager.setAlarmClock(
            info,
            pendingIntent
        )

        Log.d("MyLogs", "Broadcast start time = ${
            SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                Date(alarm.startTime)
            )
        }, end time = ${
            SimpleDateFormat("HH:mm", Locale.getDefault()).format(
                Date(alarm.endTime)
            )
        }")

        return pendingIntent
    }

    fun cancelAlarm(context: Context, pendingIntent: PendingIntent) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    companion object {
        private const val WAKE_LOCK_TAG = "com.app.clockmanager:mywakelocktag"
    }
}