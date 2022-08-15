package com.app.clockmanager.repositories

import com.app.clockmanager.data.Alarm
import kotlinx.coroutines.delay
import kotlin.random.Random

class MockRepository private constructor() {
    private val alarms = MutableList(10) {
        val start = Random.nextInt(1_000_000, 2_000_000)
        Alarm(
            startTime = start.toLong(),
            endTime = start + 100_000L,
            interval = Random.nextInt(5, 15)
        )
    }

    suspend fun getList(): List<Alarm> {
        delay(500L)
        return alarms
    }

    companion object {
        private var instance: MockRepository? = null

        fun getInstance(): MockRepository {
            if (instance == null) {
                instance = MockRepository()
            }

            return instance!!
        }
    }
}