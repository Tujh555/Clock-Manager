package com.app.clockmanager.data

import android.icu.util.Calendar

class AlarmBuilder {
    private var startTime: Long? = null
    private var endTime: Long? = null
    private var period: Int? = null
    private var isActive = true

    fun setStartTime(time: Long) {
        startTime = time
    }

    fun setEndTime(time: Long) {
        startTime?.let { start ->
            endTime = if (time < start) {
                time + DAY_IN_MILLIS
            } else {
                time
            }
        }
    }

    fun setPeriod(period: Int) {
        this.period = period
    }

    fun setActive(active: Boolean) {
        isActive = active
    }

    fun build() = Alarm(
        startTime = requireNotNull(startTime),
        endTime = requireNotNull(endTime),
        interval =  requireNotNull(period),
        isActive = isActive
    )

    companion object {
        private const val DAY_IN_MILLIS = 24 * 60 * 60 * 1000
    }
}