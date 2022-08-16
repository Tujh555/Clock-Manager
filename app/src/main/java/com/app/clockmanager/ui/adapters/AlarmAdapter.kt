package com.app.clockmanager.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.clockmanager.R
import com.app.clockmanager.data.Alarm
import com.app.clockmanager.databinding.AlarmItemBinding
import java.text.SimpleDateFormat
import java.util.*

class AlarmAdapter : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {
    val alarms = mutableListOf<Alarm>()

    fun setList(newAlarms: List<Alarm>) {
        alarms.clear()
        alarms.addAll(newAlarms)
        notifyDataSetChanged()
    }

    fun addAlarm(alarm: Alarm) {
        alarms.add(alarm)
        notifyItemInserted(alarms.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return AlarmViewHolder(
            AlarmItemBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(alarms[position])
    }

    override fun getItemCount() = alarms.size

    inner class AlarmViewHolder(
        private val binding: AlarmItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(alarm: Alarm) {
            binding.run {
                tvStartTime.text = alarm.startTime.toStringTime()
                tvEndTime.text = alarm.endTime.toStringTime()
                tvInterval.text = root.context.getString(
                    R.string.period_text,
                    alarm.interval.div(60 * 1000).toString()
                )
                swOnAlarm.isChecked = alarm.isActive
            }
        }
    }
}

internal fun Long.toStringTime(format: String = "HH:mm"): String {
    val time = Date(this)
    return SimpleDateFormat(format, Locale.getDefault()).format(time)
}