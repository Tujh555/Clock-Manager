package com.app.clockmanager.usecases

import com.app.clockmanager.database.entities.AlarmEntity

interface InsertAlarmUseCase {
    suspend operator fun invoke(alarmEntity: AlarmEntity)
}