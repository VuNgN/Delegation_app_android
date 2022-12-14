package com.vti.android.delegatedscopemanagement.testapp.usecase

import com.vti.android.delegatedscopemanagement.testapp.module.DelegateDevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class GetEnableNetworkLoggingUseCase @Inject constructor(
    private val delegateDevicePolicyManager: DelegateDevicePolicyManager
) : UseCase<Unit, Boolean> {
    override suspend fun execute(params: Unit): Boolean {
        return delegateDevicePolicyManager.isEnableNetworkLogging
    }
}