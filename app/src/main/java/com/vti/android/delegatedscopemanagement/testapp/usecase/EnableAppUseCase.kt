package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.DevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class EnableAppUseCase @Inject constructor(private val devicePolicyManager: DevicePolicyManager) :
    UseCase<String, Int> {
    override suspend fun execute(params: String): Int {
        return enable(params)
    }

    private fun enable(params: String): Int {
        return 0
    }
}