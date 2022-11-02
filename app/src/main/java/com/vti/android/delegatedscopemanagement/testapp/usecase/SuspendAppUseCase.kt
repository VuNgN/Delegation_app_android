package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.DevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class SuspendAppUseCase @Inject constructor(
    private val devicePolicyManager: DevicePolicyManager
): UseCase<String, Boolean> {
    override suspend fun execute(params: String): Boolean {
        return suspend(params)
    }

    private fun suspend(packageName: String): Boolean {
//        val packageNames = arrayOf(packageName)
//        val result = devicePolicyManager.setPackagesSuspended(null, packageNames, true)
//        return result.isNotEmpty()
        return false
    }
}