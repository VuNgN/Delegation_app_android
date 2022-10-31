package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.DevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class GetBlockUninstallUseCase @Inject constructor(private val devicePolicyManager: DevicePolicyManager) :
    UseCase<String, Boolean> {
    override suspend fun execute(params: String): Boolean {
        return devicePolicyManager.isUninstallBlocked(null, params)
    }
}