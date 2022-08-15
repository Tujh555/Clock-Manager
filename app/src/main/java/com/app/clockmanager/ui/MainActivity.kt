package com.app.clockmanager.ui

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.clockmanager.AlarmBroadcast
import com.app.clockmanager.contracts.DrawOverlayContract
import com.app.clockmanager.data.AlarmBuilder
import com.app.clockmanager.databinding.ActivityMainBinding
import com.app.clockmanager.ui.adapters.AlarmAdapter
import com.app.clockmanager.ui.fragments.PeriodPickDialogFragment
import com.app.clockmanager.viewModels.MainActivityViewModel
import com.app.clockmanager.viewModels.MainActivityViewModelFactory
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val bd: ActivityMainBinding
        get() = requireNotNull(binding)
    private val broadcast by lazy { AlarmBroadcast() }
    private val adapter by lazy { AlarmAdapter() }

    private val timerPickStart = getTimerPick("Выберите стартовое время для будильника  ")
    private val timerPickEnd = getTimerPick("Выберите конечное время для будильника  ")

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            MainActivityViewModelFactory()
        )[MainActivityViewModel::class.java]
    }

    private val alarmBuilder = AlarmBuilder()

    private val getDrawOverlayPermission = registerForActivityResult(
        DrawOverlayContract(this)
    ) { result ->
        Log.d("MyLogs", result.toString())

        showToast(
            if (result) "Разрешение получено!" else "Разрешение не получено(("
        )
    }

    private val drawOverlayDialog by lazy {
        AlertDialog.Builder(this)
            .setMessage("Разрешите показывать окна поверх других приложений.")
            .setTitle("Окна")
            .setNegativeButton("Отмена") { _, _ ->
                showToast("Разрешение не получено((")
            }
            .setPositiveButton("Ок") { _, _ ->
                getDrawOverlayPermission.launch(null)
            }
            .create()
    }

    private val positiveStartClickListener = View.OnClickListener {
        val startTime = setAlarm(timerPickStart)
        alarmBuilder.setStartTime(startTime)

        timerPickEnd.show(supportFragmentManager, END_TIME_PICK_FRAGMENT)
    }

    private val periodApplyListener = PeriodPickDialogFragment.OnPeriodApplyClickListener {
        alarmBuilder.setPeriod(it)
        val alarm = alarmBuilder.build()

        Log.d("MyLogs", alarm.toString())

        viewModel.insertAlarm(alarm)
        adapter.addAlarm(alarm)
        broadcast.setAlarm(this, alarm)
    }

    private val positiveEndClickListener = View.OnClickListener {
        val endTime = setAlarm(timerPickEnd)
        alarmBuilder.setEndTime(endTime)

        val periodDialog = PeriodPickDialogFragment()
        periodDialog.periodApplyClickListener = periodApplyListener

        periodDialog.show(supportFragmentManager, PERIOD_PICK_FRAGMENT)
    }

    private val newAlarmClickListener = View.OnClickListener {
        timerPickStart.show(supportFragmentManager, START_TIME_PICK_FRAGMENT)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bd.root)

        bd.btnAddAlarm.setOnClickListener(newAlarmClickListener)
        timerPickStart.addOnPositiveButtonClickListener(positiveStartClickListener)
        timerPickEnd.addOnPositiveButtonClickListener(positiveEndClickListener)

        viewModel.loadAlarms()
        followOnData()

        if (!Settings.canDrawOverlays(this)) {
            drawOverlayDialog.show()
        }
    }

    override fun onStart() {
        super.onStart()

        bd.run {
            rvAlarms.adapter = adapter
            rvAlarms.layoutManager = LinearLayoutManager(this@MainActivity)
            btnDeleteAll.setOnClickListener {
                viewModel.deleteAll(adapter)
            }
        }
    }

    private fun followOnData() {
        lifecycleScope.launchWhenStarted {
            viewModel.alarms.collect {
                adapter.setList(it)
            }
        }
    }

    private fun showToast(message: String) = Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    ).show()

    private fun getTimerPick(titleText: String) = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_24H)
        .setHour(12)
        .setMinute(0)
        .setTitleText(titleText)
        .build()

    private fun setAlarm(timePicker: MaterialTimePicker) = Calendar
        .getInstance(TimeZone.getDefault())
        .apply {
            set(Calendar.MILLISECOND, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, timePicker.minute)
            set(Calendar.HOUR_OF_DAY, timePicker.hour)
        }.timeInMillis

    companion object {
        private const val START_TIME_PICK_FRAGMENT = "Start time fragment"
        private const val END_TIME_PICK_FRAGMENT = "End time fragment"
        private const val PERIOD_PICK_FRAGMENT = "Period fragment"
    }
}