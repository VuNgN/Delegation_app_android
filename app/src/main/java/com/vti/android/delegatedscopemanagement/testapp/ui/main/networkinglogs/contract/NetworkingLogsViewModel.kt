package com.vti.android.delegatedscopemanagement.testapp.ui.main.networkinglogs.contract

import androidx.lifecycle.MutableLiveData
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log

interface NetworkingLogsViewModel {
    fun log(): MutableLiveData<Log>
    fun isEnable(): MutableLiveData<Boolean>
    fun isLoading(): MutableLiveData<Boolean>
    fun enableNetworkLogging(isEnable: Boolean)
    fun getState()
    fun retrieve()
    fun callApis(numberOfApi: Int)
}