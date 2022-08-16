package com.app.clockmanager.receivers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.util.Log
import com.app.clockmanager.AlarmUtils.getPendingIntentBasedOnTime
import com.app.clockmanager.data.Alarm
import com.app.clockmanager.services.AlarmService
import com.app.clockmanager.services.ServiceCommands
import com.app.clockmanager.ui.MainActivity
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

        Log.d("MyLogs", "Broadcast received")

        wakeLock.release()
    }

    fun setAlarm(context: Context, alarm: Alarm) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intents = mutableListOf<PendingIntent>()

        val activityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        for (time in (alarm.startTime..alarm.endTime) step alarm.interval.toLong()) {
            alarmManager.setClock(
                getAlarmInfo(time, context, activityIntent),
                getPendingIntentBasedOnTime(context, time)
            )

            Log.d("MyLogs", "Alarm ${getTime(time)}")
        }

        alarmManager.setClock(
            getAlarmInfo(alarm.endTime, context, activityIntent),
            getPendingIntentBasedOnTime(context, alarm.endTime)
        )

        if (Build.VERSION.SDK_INT >= 31) {
            Log.d("MyLogs", alarmManager.canScheduleExactAlarms().toString())
        }
    }

    private fun getTime(time: Long) = SimpleDateFormat("HH:mm", Locale.getDefault()).format(
        Date(time)
    )

    fun cancelAlarm(context: Context, pendingIntent: PendingIntent) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    private fun getAlarmInfo(time: Long, context: Context, activityIntent: Intent) =
        AlarmManager.AlarmClockInfo(
            time.apply {
                Log.d("MyLogs", "time = ${getTime(time)}")
            },
            PendingIntent.getActivity(
                context,
                time.hashCode(),
                activityIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
        )

    private fun AlarmManager.setClock(
        info: AlarmManager.AlarmClockInfo,
        pendingIntent: PendingIntent
    ) {
        Log.d("MyLogs", "Clock set")
        setAlarmClock(
            info,
            pendingIntent
        )
    }

    companion object {
        private const val WAKE_LOCK_TAG = "com.app.clockmanager:mywakelocktag"
    }
}