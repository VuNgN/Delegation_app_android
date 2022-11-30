package com.vti.android.delegatedscopemanagement.testapp.ui.main.packageaccess.contract.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.SecurityExceptionLog
import com.vti.android.delegatedscopemanagement.testapp.ui.main.packageaccess.contract.PackageAccessViewModel
import com.vti.android.delegatedscopemanagement.testapp.usecase.ClearAppUseCase
import com.vti.android.delegatedscopemanagement.testapp.usecase.HideAppUseCase
import com.vti.android.delegatedscopemanagement.testapp.usecase.SuspendAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PackageAccessViewModelImpl @Inject constructor(
    private val clearAppUseCase: ClearAppUseCase,
    private val hideAppUseCase: HideAppUseCase,
    private val suspendAppUseCase: SuspendAppUseCase
) : PackageAccessViewModel, ViewModel() {
    private val packageName: MutableLiveData<String> = MutableLiveData()
    private val log: MutableLiveData<Log> = MutableLiveData()

    override fun packageName(): MutableLiveData<String> = packageName
    override fun log(): MutableLiveData<Log> = log

    override fun clear() {
        viewModelScope.launch {
            try {
                clearAppUseCase.execute(packageName.value.toString())
                log.postValue(Log("Clear !", true))
            } catch (e: SecurityException) {
                log.postValue(Log(SecurityExceptionLog, false))
            } catch (e: Exception) {
                log.postValue(Log(e.message.toString(), false))
            }
        }
    }

    override fun hide() {
        viewModelScope.launch {
            try {
                val result = hideAppUseCase.execute(packageName.value.toString())
                log.postValue(Log("Hide ${packageName.value} package name is $result", true))
            } catch (e: SecurityException) {
                log.postValue(Log(SecurityExceptionLog, false))
            } catch (e: Exception) {
                log.postValue(Log(e.message.toString(), false))
            }
        }
    }

    override fun suspend() {
        viewModelScope.launch {
            try {
                val result = suspendAppUseCase.execute(packageName.value.toString())
                log.postValue(Log("Suspend ${packageName.value} package name is $result", true))
            } catch (e: SecurityException) {
                log.postValue(Log(SecurityExceptionLog, false))
            } catch (e: Exception) {
                log.postValue(Log(e.message.toString(), false))
            }
        }
    }
}