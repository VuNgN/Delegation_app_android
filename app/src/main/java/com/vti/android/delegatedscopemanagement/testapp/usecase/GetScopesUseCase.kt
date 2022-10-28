package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.DevicePolicyManager
import com.vti.android.delegatedscopemanagement.testapp.BuildConfig
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class GetScopesUseCase @Inject constructor(private val devicePolicyManager: DevicePolicyManager) :
    UseCase<Unit, List<String>> {
    override suspend fun execute(params: Unit): List<String> {
        return devicePolicyManager.getDelegatedScopes(null, BuildConfig.APPLICATION_ID)
    }
}