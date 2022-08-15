package com.app.clockmanager.usecases.impls

import com.app.clockmanager.database.entities.AlarmEntity
import com.app.clockmanager.repositories.AlarmRepository
import com.app.clockmanager.usecases.UpdateAlarmUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateAlarmUseCaseImpl(private val repository: AlarmRepository) : UpdateAlarmUseCase {
    override suspend fun invoke(alarmEntity: AlarmEntity) {
        withContext(Dispatchers.IO) {
            repository.updateAlarm(alarmEntity)
        }
    }
}