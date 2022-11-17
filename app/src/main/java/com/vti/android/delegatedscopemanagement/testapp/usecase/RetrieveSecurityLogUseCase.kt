package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.SecurityLog.SecurityEvent
import android.os.Build
import androidx.annotation.RequiresApi
import com.vti.android.delegatedscopemanagement.testapp.module.DelegateDevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class RetrieveSecurityLogUseCase @Inject constructor(
    private val delegateDevicePolicyManager: DelegateDevicePolicyManager
) : UseCase<Unit, List<SecurityEvent>?> {
    @RequiresApi(Build.VERSION_CODES.Q)
    override suspend fun execute(params: Unit): List<SecurityEvent>? {
        return delegateDevicePolicyManager.retrieveSecurityLogs()
    }
}