package com.vti.android.delegatedscopemanagement.testapp.usecase

import com.vti.android.delegatedscopemanagement.testapp.module.DelegateDevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class EnableAppUseCase @Inject constructor(private val delegateDevicePolicyManager: DelegateDevicePolicyManager) :
    UseCase<String, Int> {
    override suspend fun execute(params: String): Int {
        return delegateDevicePolicyManager.enableSystemApp(params)
    }
}