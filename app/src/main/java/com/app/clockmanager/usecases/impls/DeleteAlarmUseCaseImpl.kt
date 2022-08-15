package com.app.clockmanager.usecases.impls

import com.app.clockmanager.database.entities.AlarmEntity
import com.app.clockmanager.repositories.AlarmRepository
import com.app.clockmanager.usecases.DeleteAlarmUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteAlarmUseCaseImpl(private val repository: AlarmRepository) : DeleteAlarmUseCase {
    override suspend fun invoke(alarmEntity: AlarmEntity) {
        withContext(Dispatchers.IO) {
            repository.deleteAlarm(alarmEntity)
        }
    }
}