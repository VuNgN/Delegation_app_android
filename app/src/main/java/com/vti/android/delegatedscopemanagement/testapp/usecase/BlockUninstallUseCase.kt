package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.DevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class BlockUninstallUseCase @Inject constructor(private val devicePolicyManager: DevicePolicyManager) :
    UseCase<String, Unit> {
    override suspend fun execute(params: String) {
        devicePolicyManager.setUninstallBlocked(null, params, true)
    }
}