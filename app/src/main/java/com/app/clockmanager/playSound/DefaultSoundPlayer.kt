package com.app.clockmanager.playSound

import android.content.Context
import android.content.res.AssetManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.media.VolumeAutomation

class DefaultSoundPlayer(private val context: Context) : SoundPlayer {
    private var mediaPlayer: MediaPlayer? = null

    override fun play() {
        mediaPlayer?.run {
            setVolume(1.0f, 1.0f)
            start()
        }
    }

    override fun stop() {
        mediaPlayer?.stop()
    }

    override fun release() {
        mediaPlayer?.release()
    }

    override fun prepareToPlay() {
        val ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        mediaPlayer = MediaPlayer.create(context, ringtoneUri)
    }
}