package com.app.clockmanager.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.clockmanager.data.Alarm
import com.app.clockmanager.receivers.AlarmBroadcast
import com.app.clockmanager.repositories.MockRepository
import com.app.clockmanager.repositories.impls.DatabaseRepository
import com.app.clockmanager.ui.adapters.AlarmAdapter
import com.app.clockmanager.usecases.DeleteAlarmUseCase
import com.app.clockmanager.usecases.GetAllAlarmsUseCase
import com.app.clockmanager.usecases.InsertAlarmUseCase
import com.app.clockmanager.usecases.UpdateAlarmUseCase
import com.app.clockmanager.usecases.impls.DeleteAlarmUseCaseImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import java.util.*
import kotlin.random.Random.Default.nextInt

class MainActivityViewModel(
    private val getAllAlarmsUseCase: GetAllAlarmsUseCase,
    private val insertAlarmUseCase: InsertAlarmUseCase,
    private val deleteAlarmUseCase: DeleteAlarmUseCase,
    private val updateAlarmUseCase: UpdateAlarmUseCase
) : ViewModel() {
    private val _alarms = MutableStateFlow(listOf<Alarm>())
    val alarms = _alarms.asStateFlow()
    private var latestUpdateJob: Job? = null

    fun loadAlarms() {
        viewModelScope.launch {
            getAllAlarmsUseCase().collectLatest { newAlarms ->
                newAlarms.forEach {
                    Log.d("MyLogs", it.id.toString())
                }
                _alarms.emit(newAlarms.map { it.toAlarm() })
            }
        }
    }

    fun insertAlarm(alarm: Alarm) {
        viewModelScope.launch {
            Log.d("MyLogs", "Alarm inserted")
            insertAlarmUseCase(alarm.toAlarmEntity())
        }
    }

    fun deleteAll(adapter: AlarmAdapter, broadcast: AlarmBroadcast, context: Context) {
        viewModelScope.launch {
            adapter.alarms.forEach {
                Log.d("MyLogs", it.id.toString())

                coroutineScope {
                    broadcast.cancelAlarm(context, it)
                }

                deleteAlarmUseCase(it.toAlarmEntity())
            }

            adapter.setList(emptyList())
        }
    }

    fun deleteAlarm(alarm: Alarm) {
        TODO()
    }

    fun updateAlarm(alarm: Alarm) {
        latestUpdateJob = viewModelScope.launch {
            latestUpdateJob?.join()
            updateAlarmUseCase(alarmEntity = alarm.toAlarmEntity())
        }
    }
}