package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.DevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class GetEnableNetworkLoggingUseCase @Inject constructor(
    private val devicePolicyManager: DevicePolicyManager
) : UseCase<Unit, Boolean> {
    override suspend fun execute(params: Unit): Boolean {
        return devicePolicyManager.isNetworkLoggingEnabled(null)
    }
}