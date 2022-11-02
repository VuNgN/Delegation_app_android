package com.vti.android.delegatedscopemanagement.testapp.ui.main.enablesystemapp.contract

import androidx.lifecycle.MutableLiveData
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log

interface EnableSystemAppViewModel {
    fun log(): MutableLiveData<Log>
    fun uri(): MutableLiveData<String>
    fun enableApp()
}