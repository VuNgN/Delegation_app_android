package com.vti.android.delegatedscopemanagement.testapp.usecase

import com.vti.android.delegatedscopemanagement.testapp.module.DelegateDevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class GetNumberOfCerts @Inject constructor(
    private val delegateDevicePolicyManager: DelegateDevicePolicyManager
) : UseCase<Unit, Int> {
    override suspend fun execute(params: Unit): Int {
        return delegateDevicePolicyManager.numberOfCerts
    }
}