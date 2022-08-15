package com.app.clockmanager.ui

import android.content.Intent
import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.app.clockmanager.R
import com.app.clockmanager.playSound.DefaultSoundPlayer
import com.app.clockmanager.services.AlarmService
import com.app.clockmanager.services.ServiceCommands

class WakeUpActivity : AppCompatActivity() {
    private var ringtone: Ringtone? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wake_up)

        Log.d("MyLogs", "WakeUpActivity ${ringtone?.toString() ?: "null"}")
    }

    override fun onDestroy() {
        val stopServiceIntent = Intent(this, AlarmService::class.java).apply {
            action = ServiceCommands.STOP.toString()
        }

        startService(stopServiceIntent)

        super.onDestroy()
    }
}