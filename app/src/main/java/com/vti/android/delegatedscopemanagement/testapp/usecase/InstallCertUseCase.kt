package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.DevicePolicyManager
import android.content.Context
import com.vti.android.delegatedscopemanagement.testapp.util.FileUtils
import com.vungn.android.mybase.usecase.UseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class InstallCertUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val devicePolicyManager: DevicePolicyManager
) : UseCase<Unit, Boolean> {
    override suspend fun execute(params: Unit): Boolean {
        return installCert()
    }

    private fun installCert(): Boolean {
        val filename = "ca-example.cer"
        val certBuffer = FileUtils.read(context.assets, filename)
        return devicePolicyManager.installCaCert(null, certBuffer)
    }
}