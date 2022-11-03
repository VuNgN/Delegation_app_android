package com.vti.android.delegatedscopemanagement.testapp.usecase

import com.vti.android.delegatedscopemanagement.testapp.module.DelegateDevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class EnableSecurityLoggingUseCase @Inject constructor(
    private val delegateDevicePolicyManager: DelegateDevicePolicyManager
) : UseCase<Boolean, Unit> {
    override suspend fun execute(params: Boolean) {
        delegateDevicePolicyManager.enableSecurityLogging(params)
    }
}