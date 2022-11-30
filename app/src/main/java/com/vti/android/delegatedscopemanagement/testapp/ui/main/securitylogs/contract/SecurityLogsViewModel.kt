package com.vti.android.delegatedscopemanagement.testapp.ui.main.securitylogs.contract

import androidx.lifecycle.MutableLiveData
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log

interface SecurityLogsViewModel {
    fun log(): MutableLiveData<Log>
    fun isEnable(): MutableLiveData<Boolean>
    fun enableSecurityLogging(isEnable: Boolean)
    fun getState()
    fun retrieve()
    fun getEnableState()
}