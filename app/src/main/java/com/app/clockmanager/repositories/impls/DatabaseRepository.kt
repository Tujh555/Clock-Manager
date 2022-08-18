package com.app.clockmanager.repositories.impls

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.clockmanager.database.AlarmDatabase
import com.app.clockmanager.database.entities.AlarmEntity
import com.app.clockmanager.repositories.AlarmRepository
import java.lang.IllegalStateException
import java.util.*

class DatabaseRepository private constructor(context: Context) : AlarmRepository {
    private val database = Room.databaseBuilder(
        context.applicationContext,
        AlarmDatabase::class.java,
        DATABASE_NAME
    ).addMigrations(MIGRATION_1_2)
        .build()

    private val alarmDao = database.alarmDao()

    override suspend fun getAllAlarms(): List<AlarmEntity> {
        return alarmDao.getAllAlarms()
    }

    override suspend fun getAlarm(uuid: UUID): AlarmEntity? {
        return alarmDao.getAlarm(uuid)
    }

    override suspend fun updateAlarm(alarmEntity: AlarmEntity) {
        alarmDao.updateAlarm(alarmEntity)
    }

    override suspend fun deleteAlarm(alarmEntity: AlarmEntity) {
        alarmDao.deleteAlarm(alarmEntity)
    }

    override suspend fun insterAlarm(alarmEntity: AlarmEntity) {
        alarmDao.insertAlarm(alarmEntity)
    }

    companion object {
        private const val DATABASE_NAME = "alarm database"
        private var instance: AlarmRepository? = null

        fun initialize(context: Context) {
            if (instance == null) {
                instance = DatabaseRepository(context)
            }
        }

        fun get() = instance ?: throw IllegalStateException("Repository must be initialized")
    }
}

internal val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE AlarmEntity ADD COLUMN isLooping INTEGER DEFAULT 0 NOT NULL"
        )
    }

}