package com.app.clockmanager.data

import com.app.clockmanager.database.entities.AlarmEntity
import java.util.*

data class Alarm(
    val id: UUID = UUID.randomUUID(),
    val startTime: Long,
    val endTime: Long,
    val interval: Int,
    var isActive: Boolean = true,
    var isLooping: Boolean = false,
) {
    fun toAlarmEntity() = AlarmEntity(
        id = id,
        startTime = startTime,
        endTime = endTime,
        interval = interval,
        isActive = isActive,
        isLooping = isLooping
    )
}