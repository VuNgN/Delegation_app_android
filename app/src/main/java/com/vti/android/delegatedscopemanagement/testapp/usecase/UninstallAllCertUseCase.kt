package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.DevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class UninstallAllCertUseCase @Inject constructor(private val devicePolicyManager: DevicePolicyManager) :
    UseCase<Unit, Unit> {
    override suspend fun execute(params: Unit) {
        return devicePolicyManager.uninstallAllUserCaCerts(null)
    }
}