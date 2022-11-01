package com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.grantstate.contract.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.data.ApplicationPermission
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.data.GrantState
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.grantstate.contract.GrantStateViewModel
import com.vti.android.delegatedscopemanagement.testapp.usecase.SetPermissionGrantStateUseCase
import com.vungn.android.mybase.livedata.CombinedLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GrantStateViewModelImpl @Inject constructor(
    private val setPermissionGrantStateUseCase: SetPermissionGrantStateUseCase
) : GrantStateViewModel, ViewModel() {
    private val log: MutableLiveData<Log> = MutableLiveData()
    private val packageName: MutableLiveData<String> = MutableLiveData()
    private val permission: MutableLiveData<String> = MutableLiveData()
    private val grantState: MutableLiveData<GrantState> = MutableLiveData()

    override fun log(): MutableLiveData<Log> = log
    override fun packageName(): MutableLiveData<String> = packageName
    override fun permission(): MutableLiveData<String> = permission
    override fun grantState(): MutableLiveData<GrantState> = grantState

    override fun isEnable(): LiveData<Boolean> =
        CombinedLiveData<Boolean>(packageName, permission) {
            !packageName.value.isNullOrEmpty() && !permission.value.isNullOrEmpty()
        }

    override fun setPermission() {
        val applicationPermission = ApplicationPermission(
            packageName = packageName.value.toString(),
            permission = permission.value.toString(),
            grantState = grantState.value ?: GrantState.DEFAULT
        )
        viewModelScope.launch {
            try {
                val result = setPermissionGrantStateUseCase.execute(applicationPermission)
                log.postValue(
                    Log(
                        "Set ${permission.value} permission for ${packageName.value} package name is $result",
                        true
                    )
                )
            } catch (e: Exception) {
                log.postValue(Log(e.message.toString(), false))
            }
        }
    }
}