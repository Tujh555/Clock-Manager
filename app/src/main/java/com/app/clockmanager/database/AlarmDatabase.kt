package com.app.clockmanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.clockmanager.database.entities.AlarmEntity
import com.app.clockmanager.database.typeConverters.AlarmTypeConverters

@Database(entities = [ AlarmEntity::class ], version = 2)
@TypeConverters(AlarmTypeConverters::class)
abstract class AlarmDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}