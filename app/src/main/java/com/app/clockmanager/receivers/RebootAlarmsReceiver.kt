package com.app.clockmanager.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class RebootAlarmsReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        p1?.let { intent ->
            if (intent.action == "android.intent.action.BOOT_COMPLETED") {
                TODO()
            }
        }
    }
}