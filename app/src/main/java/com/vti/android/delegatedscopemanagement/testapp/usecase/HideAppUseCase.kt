package com.vti.android.delegatedscopemanagement.testapp.usecase

import com.vti.android.delegatedscopemanagement.testapp.module.DelegateDevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class HideAppUseCase @Inject constructor(
    private val delegateDevicePolicyManager: DelegateDevicePolicyManager
) : UseCase<String, Boolean> {
    override suspend fun execute(params: String): Boolean {
        return delegateDevicePolicyManager.setApplicationHidden(params, true)
    }
}