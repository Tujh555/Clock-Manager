package com.app.clockmanager.usecases

import com.app.clockmanager.database.entities.AlarmEntity

interface DeleteAlarmUseCase {
    suspend operator fun invoke(alarmEntity: AlarmEntity)
}