package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.os.Bundle
import com.vti.android.delegatedscopemanagement.testapp.module.DelegateDevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class RestrictAppUseCase @Inject constructor(
    private val delegateDevicePolicyManager: DelegateDevicePolicyManager
) : UseCase<Bundle, Unit> {
    override suspend fun execute(params: Bundle) {
        delegateDevicePolicyManager.setAppRestrictions(params)
    }
}