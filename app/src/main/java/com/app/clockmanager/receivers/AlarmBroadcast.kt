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
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class AlarmBroadcast(private val scope: CoroutineScope) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(
            PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.SCREEN_BRIGHT_WAKE_LOCK,
            WAKE_LOCK_TAG
        )

        wakeLock.acquire(30_000)

        context.startService(
            Intent(context, AlarmService::class.java).apply {
                action = ServiceCommands.PLAY.toString()
            }
        )

        Log.d("MyLogs", "Broadcast received")
    }

    fun setAlarm(context: Context, alarm: Alarm) {
        scope.launch {
            withContext(Dispatchers.Default) {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                val activityIntent = Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                }

                val currentDate = Date()

                for (time in (alarm.startTime..alarm.endTime) step alarm.interval.toLong()) {
                    if (time > currentDate.time) {
                        alarmManager.setClock(
                            getAlarmInfo(time, context, activityIntent),
                            getPendingIntentBasedOnTime(context, time)
                        )
                    }

                    Log.d("MyLogs", "Alarm ${getTime(time)}")
                }

                alarmManager.setClock(
                    getAlarmInfo(alarm.endTime, context, activityIntent),
                    getPendingIntentBasedOnTime(context, alarm.endTime)
                )

                Log.d("MyLogs", "Alarm ${getTime(alarm.endTime)}")
            }
        }
    }

    private fun getTime(time: Long) = SimpleDateFormat("HH:mm", Locale.getDefault()).format(
        Date(time)
    )

    fun cancelAlarm(context: Context, alarm: Alarm) {
        scope.launch {
            withContext(Dispatchers.Default) {
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                for (time in (alarm.startTime..alarm.endTime) step alarm.interval.toLong()) {
                    alarmManager.cancel(
                        getPendingIntentBasedOnTime(context, time)
                    )

                    Log.d("MyLogs", "Cancelled ${getTime(time)}")
                }

                alarmManager.cancel(
                    getPendingIntentBasedOnTime(context, alarm.endTime)
                )

                Log.d("MyLogs", "Cancelled ${getTime(alarm.endTime)}")
            }
        }
    }

    private fun getAlarmInfo(time: Long, context: Context, activityIntent: Intent) =
        AlarmManager.AlarmClockInfo(
            time,
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
        setAlarmClock(
            info,
            pendingIntent
        )
    }

    companion object {
        private const val WAKE_LOCK_TAG = "com.app.clockmanager:mywakelocktag"
    }
}