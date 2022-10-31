package com.vti.android.delegatedscopemanagement.testapp.ui.main.blockuninstall.contract

import androidx.lifecycle.MutableLiveData
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log

interface BlockUninstallViewModel {
    fun log(): MutableLiveData<Log>
    fun packageName(): MutableLiveData<String>
    fun isBlocked(): MutableLiveData<Boolean>
    fun blockUninstall()
    fun getBlockUninstall()
}