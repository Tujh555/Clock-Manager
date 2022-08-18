package com.app.clockmanager.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.clockmanager.data.Alarm
import java.util.*

@Entity
data class AlarmEntity(
    @PrimaryKey val id: UUID,
    val startTime: Long,
    val endTime: Long,
    val interval: Int,
    var isActive: Boolean,
    var isLooping: Boolean
) {
    fun toAlarm() = Alarm(
        id = id,
        startTime = startTime,
        endTime = endTime,
        interval = interval,
        isActive = isActive,
        isLooping = isLooping,
    )
}
