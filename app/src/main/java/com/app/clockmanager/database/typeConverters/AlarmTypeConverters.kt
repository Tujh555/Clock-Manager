package com.app.clockmanager.database.typeConverters

import androidx.room.TypeConverter
import java.util.*

class AlarmTypeConverters {

    @TypeConverter
    fun fromUUID(uuid: UUID) = uuid.toString()

    @TypeConverter
    fun toUUID(str: String) = UUID.fromString(str)
}