package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.DevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class GetNumberOfCerts @Inject constructor(private val devicePolicyManager: DevicePolicyManager) :
    UseCase<Unit, Int> {
    override suspend fun execute(params: Unit): Int {
        return devicePolicyManager.getInstalledCaCerts(null).size
    }
}