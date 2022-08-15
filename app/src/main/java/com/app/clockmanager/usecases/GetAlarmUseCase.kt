package com.app.clockmanager.usecases

import com.app.clockmanager.database.entities.AlarmEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

interface GetAlarmUseCase {
    suspend operator fun invoke(uuid: UUID): Flow<AlarmEntity?>
}