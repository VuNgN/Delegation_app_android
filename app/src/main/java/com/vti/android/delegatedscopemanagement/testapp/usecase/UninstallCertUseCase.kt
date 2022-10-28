package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.DevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class UninstallCertUseCase @Inject constructor(private val devicePolicyManager: DevicePolicyManager) :
    UseCase<Unit, Boolean> {
    override suspend fun execute(params: Unit): Boolean {
        return uninstallCert()
    }

    private fun uninstallCert(): Boolean {
        val installedCaCerts = devicePolicyManager.getInstalledCaCerts(null)
        if (installedCaCerts.isEmpty()) {
            return false
        }
        devicePolicyManager.uninstallCaCert(null, installedCaCerts[0])
        return true
    }
}