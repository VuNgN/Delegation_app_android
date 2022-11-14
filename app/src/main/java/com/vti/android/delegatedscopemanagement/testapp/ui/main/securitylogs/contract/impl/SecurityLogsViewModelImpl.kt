package com.vti.android.delegatedscopemanagement.testapp.ui.main.securitylogs.contract.impl

import android.app.admin.SecurityLog.SecurityEvent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.ui.main.securitylogs.contract.SecurityLogsViewModel
import com.vti.android.delegatedscopemanagement.testapp.usecase.EnableSecurityLoggingUseCase
import com.vti.android.delegatedscopemanagement.testapp.usecase.GetEnableSecurityLoggingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SecurityLogsViewModelImpl @Inject constructor(
    private val enableSecurityLoggingUseCase: EnableSecurityLoggingUseCase,
    private val getEnableSecurityLoggingUseCase: GetEnableSecurityLoggingUseCase
) : SecurityLogsViewModel, ViewModel() {
    private val log: MutableLiveData<Log> = MutableLiveData()
    private val isEnable: MutableLiveData<Boolean> = MutableLiveData()

    override fun log(): MutableLiveData<Log> = log

    override fun isEnable(): MutableLiveData<Boolean> = isEnable

    override fun enableSecurityLogging(isEnable: Boolean) {
        viewModelScope.launch {
            try {
                enableSecurityLoggingUseCase.execute(isEnable)
                this@SecurityLogsViewModelImpl.isEnable.postValue(isEnable)
                log.postValue(Log(if (isEnable) "Enabled" else "Disabled", true))
            } catch (e: Exception) {
                log.postValue(Log(e.message.toString(), false))
            }
        }
    }

    override fun getState() {
        viewModelScope.launch {
            try {
                val isEnable = getEnableSecurityLoggingUseCase.execute(Unit)
                this@SecurityLogsViewModelImpl.isEnable.postValue(isEnable)
            } catch (e: Exception) {
                android.util.Log.e(TAG, "getState: ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun pushLog(events: List<SecurityEvent>) {
        events.forEach { event ->
            val title =
                "id: ${event.id}, log level: ${event.logLevel}, has data: ${event.data != null}, time: ${
                    getDateTime(
                        event.timeNanos
                    )
                }"
            log.value = (Log(title, true))
        }
    }

    private fun getDateTime(s: Long): String? {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val netDate = Date(s * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    companion object {
        private val TAG = SecurityLogsViewModelImpl::class.simpleName
    }
}