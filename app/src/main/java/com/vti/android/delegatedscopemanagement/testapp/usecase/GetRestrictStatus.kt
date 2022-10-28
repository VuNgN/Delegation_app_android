package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.DevicePolicyManager
import android.os.Bundle
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class GetRestrictStatus @Inject constructor(private val devicePolicyManager: DevicePolicyManager) :
    UseCase<Unit, Bundle> {
    override suspend fun execute(params: Unit): Bundle {
        return devicePolicyManager.getApplicationRestrictions(null, "com.android.chrome")
    }
}