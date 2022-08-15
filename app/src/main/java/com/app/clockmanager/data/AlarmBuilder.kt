package com.app.clockmanager.data

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
            if (time < start) throw IllegalArgumentException("End time less than start time")
        }

        endTime = time
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
}