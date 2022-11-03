package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.DevicePolicyManager
import android.content.Intent
import android.net.Uri
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class EnableAppUseCase @Inject constructor(private val devicePolicyManager: DevicePolicyManager) :
    UseCase<String, Int> {
    override suspend fun execute(params: String): Int {
        return enable(params)
    }

    private fun enable(params: String): Int {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(params))
//        return devicePolicyManager.enableSystemApp(null, intent)
        return 0;
    }
}