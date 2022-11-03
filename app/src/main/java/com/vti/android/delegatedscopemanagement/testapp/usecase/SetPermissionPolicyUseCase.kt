package com.vti.android.delegatedscopemanagement.testapp.usecase

import com.vti.android.delegatedscopemanagement.testapp.module.DelegateDevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class SetPermissionPolicyUseCase @Inject constructor(
    private val delegateDevicePolicyManager: DelegateDevicePolicyManager
) : UseCase<Int, Unit> {
    override suspend fun execute(params: Int) {
        delegateDevicePolicyManager.setPermissionPolicy(params)
    }
}