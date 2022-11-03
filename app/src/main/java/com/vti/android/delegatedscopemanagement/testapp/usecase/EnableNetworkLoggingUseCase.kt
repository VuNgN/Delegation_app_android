package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.DevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class EnableNetworkLoggingUseCase @Inject constructor(
    private val devicePolicyManager: DevicePolicyManager
) : UseCase<Boolean, Unit> {
    override suspend fun execute(params: Boolean) {
        return devicePolicyManager.setNetworkLoggingEnabled(null, params)
    }
}