package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.SecurityLog.SecurityEvent
import com.vti.android.delegatedscopemanagement.testapp.module.DelegateDevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class RetrievePreRebootSecurityLogsUseCase @Inject constructor(
    private val delegateDevicePolicyManager: DelegateDevicePolicyManager
) : UseCase<Unit, List<SecurityEvent>?> {
    override suspend fun execute(params: Unit): List<SecurityEvent>? {
        return delegateDevicePolicyManager.retrievePreRebootSecurityLogs()
    }
}