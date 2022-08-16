package com.app.clockmanager

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.app.clockmanager.receivers.AlarmBroadcast

object AlarmUtils {
    fun getPendingIntentBasedOnTime(context: Context, time: Long): PendingIntent {
        val broadcastIntent = Intent(context, AlarmBroadcast::class.java)

        return PendingIntent.getBroadcast(
            context,
            time.hashCode(),
            broadcastIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}