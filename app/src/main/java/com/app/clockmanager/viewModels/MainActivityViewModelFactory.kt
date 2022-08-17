package com.app.clockmanager.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.clockmanager.repositories.impls.DatabaseRepository
import com.app.clockmanager.usecases.GetAllAlarmsUseCase
import com.app.clockmanager.usecases.InsertAlarmUseCase
import com.app.clockmanager.usecases.UpdateAlarmUseCase
import com.app.clockmanager.usecases.impls.DeleteAlarmUseCaseImpl
import com.app.clockmanager.usecases.impls.GetAllAlarmsUseCaseImpl
import com.app.clockmanager.usecases.impls.InsertAlarmUseCaseImpl
import com.app.clockmanager.usecases.impls.UpdateAlarmUseCaseImpl

class MainActivityViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return MainActivityViewModel(
            GetAllAlarmsUseCaseImpl(DatabaseRepository.get()),
            InsertAlarmUseCaseImpl(DatabaseRepository.get()),
            DeleteAlarmUseCaseImpl(DatabaseRepository.get()),
            UpdateAlarmUseCaseImpl(DatabaseRepository.get())
        ) as T
    }
}