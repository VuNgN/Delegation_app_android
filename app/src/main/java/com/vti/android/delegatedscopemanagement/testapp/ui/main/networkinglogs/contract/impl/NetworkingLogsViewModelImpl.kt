package com.vti.android.delegatedscopemanagement.testapp.ui.main.networkinglogs.contract.impl

import android.app.admin.DnsEvent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.SecurityExceptionLog
import com.vti.android.delegatedscopemanagement.testapp.ui.main.networkinglogs.contract.NetworkingLogsViewModel
import com.vti.android.delegatedscopemanagement.testapp.usecase.EnableNetworkLoggingUseCase
import com.vti.android.delegatedscopemanagement.testapp.usecase.GetEnableNetworkLoggingUseCase
import com.vti.android.delegatedscopemanagement.testapp.usecase.RetrieveNetworkLogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NetworkingLogsViewModelImpl @Inject constructor(
    private val enableNetworkLoggingUseCase: EnableNetworkLoggingUseCase,
    private val getEnableNetworkLoggingUseCase: GetEnableNetworkLoggingUseCase,
    private val retrieveNetworkLogUseCase: RetrieveNetworkLogUseCase
) : NetworkingLogsViewModel, ViewModel() {
    private val log: MutableLiveData<Log> = MutableLiveData()
    private val isEnable: MutableLiveData<Boolean> = MutableLiveData()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    override fun log(): MutableLiveData<Log> = log
    override fun isEnable(): MutableLiveData<Boolean> = isEnable
    override fun isLoading(): MutableLiveData<Boolean> = isLoading

    override fun enableNetworkLogging(isEnable: Boolean) {
        viewModelScope.launch {
            try {
                enableNetworkLoggingUseCase.execute(isEnable)
                this@NetworkingLogsViewModelImpl.isEnable.postValue(isEnable)
                log.postValue(
                    Log(
                        "setNetworkLoggingEnabled(): ${if (isEnable) "Enabled" else "Disabled"}",
                        true
                    )
                )
            } catch (e: SecurityException) {
                this@NetworkingLogsViewModelImpl.isEnable.postValue(false)
                log.postValue(Log(SecurityExceptionLog, false))
            } catch (e: Exception) {
                this@NetworkingLogsViewModelImpl.isEnable.postValue(false)
                log.postValue(Log(e.message.toString(), false))
            }
        }
    }

    override fun getState() {
        viewModelScope.launch {
            try {
                val isEnable = getEnableNetworkLoggingUseCase.execute(Unit)
                this@NetworkingLogsViewModelImpl.isEnable.postValue(isEnable)
            } catch (e: Exception) {
                android.util.Log.e(TAG, "getState: ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun retrieve() {
        viewModelScope.launch(Dispatchers.Main) {
            isLoading.value = true
            launch(Dispatchers.Main) RetrieveLog@{
                try {
                    val events = withContext(Dispatchers.IO) {
                        retrieveNetworkLogUseCase.execute(Unit)
                    }
                    if (events == null) {
                        log.value = (Log("retrieveNetworkLogs(): No logs!", true))
                        return@RetrieveLog
                    }
                    events.forEach { event ->
                        val title = "id: ${event.id}, " + "package name: ${event.packageName}${
                            if (event is DnsEvent) ", " + "hostname: ${event.hostname}" else ""
                        }, " + "time: ${getDateTime(event.timestamp)}"
                        log.value = (Log(title, true))
                    }
                    android.util.Log.d(TAG, "retrieveNetworkLogs(): done")
                } catch (e: SecurityException) {
                    log.postValue(Log(SecurityExceptionLog, false))
                } catch (e: Exception) {
                    log.postValue(Log(e.message.toString(), false))
                }
            }.join()
            isLoading.value = false
        }
    }

    override fun getEnableState() {
        viewModelScope.launch {
            try {
                val isEnable = getEnableNetworkLoggingUseCase.execute(Unit)
                log.value = (Log("isNetworkLoggingEnabled(): $isEnable", true))
            } catch (e: SecurityException) {
                log.postValue(Log(SecurityExceptionLog, false))
            } catch (e: Exception) {
                log.postValue(Log(e.message.toString(), false))
            }
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
        private val TAG = NetworkingLogsViewModelImpl::class.simpleName
    }
}