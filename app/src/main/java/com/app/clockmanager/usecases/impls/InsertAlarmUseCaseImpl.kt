package com.app.clockmanager.usecases.impls

import com.app.clockmanager.database.entities.AlarmEntity
import com.app.clockmanager.repositories.AlarmRepository
import com.app.clockmanager.usecases.InsertAlarmUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InsertAlarmUseCaseImpl(private val repository: AlarmRepository) : InsertAlarmUseCase {
    override suspend fun invoke(alarmEntity: AlarmEntity) {
        withContext(Dispatchers.IO) {
            repository.insterAlarm(alarmEntity)
        }
    }
}