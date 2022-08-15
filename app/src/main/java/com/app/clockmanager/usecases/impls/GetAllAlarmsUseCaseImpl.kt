package com.app.clockmanager.usecases.impls

import com.app.clockmanager.database.entities.AlarmEntity
import com.app.clockmanager.repositories.AlarmRepository
import com.app.clockmanager.usecases.GetAllAlarmsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllAlarmsUseCaseImpl(private val repository: AlarmRepository) : GetAllAlarmsUseCase {
    override suspend fun invoke(): Flow<List<AlarmEntity>> = flow {
        emit(
            repository.getAllAlarms()
        )
    }
}