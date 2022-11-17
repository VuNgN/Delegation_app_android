package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.NetworkEvent
import com.vti.android.delegatedscopemanagement.testapp.module.DelegateDevicePolicyManager
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class RetrieveNetworkLogUseCase @Inject constructor(
    private val delegateDevicePolicyManager: DelegateDevicePolicyManager
) : UseCase<Unit, List<NetworkEvent>?> {
    override suspend fun execute(params: Unit): List<NetworkEvent>? {
        return delegateDevicePolicyManager.retrieveNetworkLogs()
    }
}