package com.app.clockmanager.usecases.impls

import com.app.clockmanager.database.entities.AlarmEntity
import com.app.clockmanager.repositories.AlarmRepository
import com.app.clockmanager.usecases.GetAlarmUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*

class GetAlarmUseCaseImpl(private val repository: AlarmRepository) : GetAlarmUseCase {
    override suspend fun invoke(uuid: UUID): Flow<AlarmEntity?> = flow {
        emit(
            repository.getAlarm(uuid)
        )
    }.flowOn(Dispatchers.IO)
}