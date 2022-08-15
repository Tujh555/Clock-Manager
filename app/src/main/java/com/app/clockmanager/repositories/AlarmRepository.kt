package com.app.clockmanager.repositories

import com.app.clockmanager.database.entities.AlarmEntity
import java.util.*

interface AlarmRepository {
    suspend fun getAllAlarms(): List<AlarmEntity>

    suspend fun getAlarm(uuid: UUID): AlarmEntity?

    suspend fun updateAlarm(alarmEntity: AlarmEntity)

    suspend fun deleteAlarm(alarmEntity: AlarmEntity)

    suspend fun insterAlarm(alarmEntity: AlarmEntity)
}