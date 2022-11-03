package com.vti.android.delegatedscopemanagement.testapp.usecase

import com.vti.android.delegatedscopemanagement.testapp.module.DelegateDevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class GetScopesUseCase @Inject constructor(
    private val delegateDevicePolicyManager: DelegateDevicePolicyManager
) : UseCase<Unit, List<String>> {
    override suspend fun execute(params: Unit): List<String> {
        return delegateDevicePolicyManager.scopes
    }
}