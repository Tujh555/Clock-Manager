package com.app.clockmanager.playSound

import android.content.res.AssetManager

interface SoundPlayer {
    fun play()
    fun stop()
    fun release()
    fun prepareToPlay()
}