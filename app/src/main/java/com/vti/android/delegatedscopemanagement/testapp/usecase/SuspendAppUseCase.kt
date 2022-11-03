package com.vti.android.delegatedscopemanagement.testapp.usecase

import com.vti.android.delegatedscopemanagement.testapp.module.DelegateDevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class SuspendAppUseCase @Inject constructor(
    private val delegateDevicePolicyManager: DelegateDevicePolicyManager
) : UseCase<String, Boolean> {
    override suspend fun execute(params: String): Boolean {
        return suspend(params)
    }

    private fun suspend(packageName: String): Boolean {
        val result = delegateDevicePolicyManager.setPackageSuspended(packageName, true)
        return result.isEmpty()
    }
}