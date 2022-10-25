package com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.grantstate.contract.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.grantstate.contract.GrantStateViewModel
import com.vungn.android.mybase.livedata.CombinedLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GrantStateViewModelImpl @Inject constructor() : GrantStateViewModel, ViewModel() {
    private val packageName: MutableLiveData<String> = MutableLiveData()
    private val permission: MutableLiveData<String> = MutableLiveData()

    override fun packageName(): MutableLiveData<String> = packageName

    override fun permission(): MutableLiveData<String> = permission

    override fun isEnable(): LiveData<Boolean> =
        CombinedLiveData<Boolean>(packageName, permission) {
            !packageName.value.isNullOrEmpty() && !permission.value.isNullOrEmpty()
        }
}