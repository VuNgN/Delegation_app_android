package com.vti.android.delegatedscopemanagement.testapp.ui.main.networkinglogs.contract.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.ui.main.networkinglogs.contract.NetworkingLogsViewModel
import com.vti.android.delegatedscopemanagement.testapp.usecase.EnableNetworkLoggingUseCase
import com.vti.android.delegatedscopemanagement.testapp.usecase.GetEnableNetworkLoggingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkingLogsViewModelImpl @Inject constructor(
    private val enableNetworkLoggingUseCase: EnableNetworkLoggingUseCase,
    private val getEnableNetworkLoggingUseCase: GetEnableNetworkLoggingUseCase
) : NetworkingLogsViewModel, ViewModel() {
    private val log: MutableLiveData<Log> = MutableLiveData()
    private val isEnable: MutableLiveData<Boolean> = MutableLiveData()

    override fun log(): MutableLiveData<Log> = log
    override fun isEnable(): MutableLiveData<Boolean> = isEnable

    override fun enableNetworkLogging(isEnable: Boolean) {
        viewModelScope.launch {
            try {
                enableNetworkLoggingUseCase.execute(isEnable)
                this@NetworkingLogsViewModelImpl.isEnable.postValue(isEnable)
                log.postValue(Log(if (isEnable) "Enabled" else "Disabled", true))
            } catch (e: Exception) {
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

    companion object {
        private val TAG = NetworkingLogsViewModelImpl::class.simpleName
    }
}