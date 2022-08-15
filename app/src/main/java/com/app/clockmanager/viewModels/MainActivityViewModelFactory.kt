package com.app.clockmanager.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.clockmanager.repositories.impls.DatabaseRepository
import com.app.clockmanager.usecases.GetAllAlarmsUseCase
import com.app.clockmanager.usecases.InsertAlarmUseCase
import com.app.clockmanager.usecases.impls.GetAllAlarmsUseCaseImpl
import com.app.clockmanager.usecases.impls.InsertAlarmUseCaseImpl

class MainActivityViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val getAllAlarmsUseCase = GetAllAlarmsUseCaseImpl(
            DatabaseRepository.get()
        )

        val insertAlarmUseCase = InsertAlarmUseCaseImpl(
            DatabaseRepository.get()
        )

        return MainActivityViewModel(getAllAlarmsUseCase, insertAlarmUseCase) as T
    }
}