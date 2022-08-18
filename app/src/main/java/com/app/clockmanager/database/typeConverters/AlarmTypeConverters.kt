package com.app.clockmanager.database.typeConverters

import androidx.room.TypeConverter
import java.util.*

class AlarmTypeConverters {

    @TypeConverter
    fun fromUUID(uuid: UUID): String = uuid.toString()

    @TypeConverter
    fun toUUID(str: String): UUID = UUID.fromString(str)
}