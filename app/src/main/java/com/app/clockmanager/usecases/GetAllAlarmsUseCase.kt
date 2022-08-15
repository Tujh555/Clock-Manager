package com.app.clockmanager.usecases

import com.app.clockmanager.database.entities.AlarmEntity
import kotlinx.coroutines.flow.Flow

interface GetAllAlarmsUseCase {
    suspend operator fun invoke(): Flow<List<AlarmEntity>>
}