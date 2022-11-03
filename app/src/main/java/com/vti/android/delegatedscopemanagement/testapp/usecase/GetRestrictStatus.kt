package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.os.Bundle
import com.vti.android.delegatedscopemanagement.testapp.module.DelegateDevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class GetRestrictStatus @Inject constructor(
    private val delegateDevicePolicyManager: DelegateDevicePolicyManager
) : UseCase<Unit, Bundle> {
    override suspend fun execute(params: Unit): Bundle {
        return delegateDevicePolicyManager.restrictStatus
    }
}