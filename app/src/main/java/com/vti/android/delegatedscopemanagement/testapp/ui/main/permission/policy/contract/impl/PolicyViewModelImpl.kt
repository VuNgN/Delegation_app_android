package com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.policy.contract.impl

import android.app.admin.DevicePolicyManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.SecurityExceptionLog
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.policy.contract.PolicyViewModel
import com.vti.android.delegatedscopemanagement.testapp.usecase.SetPermissionPolicyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PolicyViewModelImpl @Inject constructor(
    private val setPermissionPolicyUseCase: SetPermissionPolicyUseCase
) : PolicyViewModel, ViewModel() {
    private val log: MutableLiveData<Log> = MutableLiveData()

    override fun log(): MutableLiveData<Log> = log

    override fun setAutoGrantState() {
        setPermission(DevicePolicyManager.PERMISSION_POLICY_AUTO_GRANT)
    }

    override fun setAutoDenyState() {
        setPermission(DevicePolicyManager.PERMISSION_POLICY_AUTO_DENY)
    }

    override fun setPromptState() {
        setPermission(DevicePolicyManager.PERMISSION_POLICY_PROMPT)
    }

    private fun setPermission(policy: Int) {
        viewModelScope.launch {
            try {
                setPermissionPolicyUseCase.execute(policy)
                log.postValue(Log("Set permission policy success!", true))
            } catch (e: SecurityException) {
                log.postValue(Log(SecurityExceptionLog, false))
            } catch (e: Exception) {
                log.postValue(Log(e.message.toString(), false))
            }
        }
    }
}