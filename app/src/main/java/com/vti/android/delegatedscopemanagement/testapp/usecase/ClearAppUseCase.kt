package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.DevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class ClearAppUseCase @Inject constructor(
    private val devicePolicyManager: DevicePolicyManager
) : UseCase<String, Unit> {
    override suspend fun execute(params: String) {
        return clear(params)
    }

    private fun clear(packageName: String) {
//        devicePolicyManager.setApplicationHidden(null, packageName, false)
//        devicePolicyManager.setPackagesSuspended(null, arrayOf(packageName), false)
    }
}