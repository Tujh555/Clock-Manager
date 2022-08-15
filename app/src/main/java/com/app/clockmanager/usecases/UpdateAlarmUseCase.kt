package com.app.clockmanager.usecases

import com.app.clockmanager.database.entities.AlarmEntity

interface UpdateAlarmUseCase {
    suspend operator fun invoke(alarmEntity: AlarmEntity)
}