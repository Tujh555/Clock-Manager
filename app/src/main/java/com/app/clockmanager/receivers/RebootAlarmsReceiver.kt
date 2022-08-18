package com.app.clockmanager.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.clockmanager.repositories.impls.DatabaseRepository
import com.app.clockmanager.usecases.impls.GetAllAlarmsUseCaseImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RebootAlarmsReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        p1?.let { intent ->
            if (
                intent.action == "android.intent.action.BOOT_COMPLETED" ||
                intent.action == "android.intent.action.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED"
            ) {
                if (p0 == null) return

                setAllAlarms(p0)
            }
        }
    }

    private fun setAllAlarms(context: Context) {
        val getAlarms = GetAllAlarmsUseCaseImpl(DatabaseRepository.get())

        CoroutineScope(Dispatchers.IO).launch {
            val alarmBroadcast = AlarmBroadcast(this)

            getAlarms().collect { alarms ->
                alarms.forEach { alarmEntity ->
                    if (alarmEntity.isActive) {
                        alarmBroadcast.setAlarm(context, alarmEntity.toAlarm())
                    }
                }
            }
        }
    }
}