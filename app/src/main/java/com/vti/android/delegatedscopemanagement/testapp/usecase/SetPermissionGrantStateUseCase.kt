package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.vti.android.delegatedscopemanagement.testapp.module.DelegateDevicePolicyManager
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.data.ApplicationPermission
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class SetPermissionGrantStateUseCase @Inject constructor(
    private val delegateDevicePolicyManager: DelegateDevicePolicyManager
) : UseCase<ApplicationPermission, Boolean> {
    override suspend fun execute(params: ApplicationPermission): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setPermissionGrantState(params)
        } else {
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setPermissionGrantState(params: ApplicationPermission): Boolean {
        return delegateDevicePolicyManager.setPermissionGrantState(params)
    }
}