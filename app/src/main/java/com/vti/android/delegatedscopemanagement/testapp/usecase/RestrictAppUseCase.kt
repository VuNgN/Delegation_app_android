package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.DevicePolicyManager
import android.os.Bundle
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class RestrictAppUseCase @Inject constructor(private val devicePolicyManager: DevicePolicyManager) :
    UseCase<Bundle, Unit> {
    override suspend fun execute(params: Bundle) {
        devicePolicyManager.setApplicationRestrictions(null, "com.android.chrome", params)
    }
}