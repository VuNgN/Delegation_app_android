package com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.policy.contract

import androidx.lifecycle.MutableLiveData
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log

interface PolicyViewModel {
    fun log(): MutableLiveData<Log>
    fun setAutoGrantState()
    fun setAutoDenyState()
    fun setPromptState()
}