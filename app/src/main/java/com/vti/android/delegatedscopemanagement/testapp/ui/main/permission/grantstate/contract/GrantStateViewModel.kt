package com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.grantstate.contract

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.data.GrantState

interface GrantStateViewModel {
    fun log(): MutableLiveData<Log>
    fun packageName(): MutableLiveData<String>
    fun permission(): MutableLiveData<String>
    fun grantState(): MutableLiveData<GrantState>
    fun isEnable(): LiveData<Boolean>
    fun setPermission()
}