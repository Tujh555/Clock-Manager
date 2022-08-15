package com.app.clockmanager.database

import androidx.room.*
import com.app.clockmanager.database.entities.AlarmEntity
import java.util.*

@Dao
interface AlarmDao {
    @Query("SELECT * FROM AlarmEntity")
    suspend fun getAllAlarms(): List<AlarmEntity>

    @Query("SELECT * FROM AlarmEntity WHERE (:alarmId) = id")
    suspend fun getAlarm(alarmId: UUID): AlarmEntity?

    @Delete
    suspend fun deleteAlarm(alarmEntity: AlarmEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAlarm(alarmEntity: AlarmEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarmEntity: AlarmEntity)
}