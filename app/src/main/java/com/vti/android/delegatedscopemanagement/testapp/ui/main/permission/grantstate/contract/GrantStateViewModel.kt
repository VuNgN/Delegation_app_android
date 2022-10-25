package com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.grantstate.contract

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface GrantStateViewModel {
    fun packageName(): MutableLiveData<String>
    fun permission(): MutableLiveData<String>
    fun isEnable(): LiveData<Boolean>
}