package com.vti.android.delegatedscopemanagement.testapp.ui.main.packageaccess.contract

import androidx.lifecycle.MutableLiveData
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log

interface PackageAccessViewModel {
    fun log(): MutableLiveData<Log>
    fun packageName(): MutableLiveData<String>
    fun clear()
    fun hide()
    fun suspend()
}