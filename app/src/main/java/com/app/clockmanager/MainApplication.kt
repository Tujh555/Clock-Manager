package com.app.clockmanager

import android.app.Application
import com.app.clockmanager.repositories.impls.DatabaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseRepository.initialize(this)
    }
}