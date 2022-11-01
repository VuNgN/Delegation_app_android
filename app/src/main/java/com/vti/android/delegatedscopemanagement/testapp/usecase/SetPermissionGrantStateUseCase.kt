package com.vti.android.delegatedscopemanagement.testapp.usecase

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.os.Build
import androidx.annotation.RequiresApi
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.data.ApplicationPermission
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.data.GrantState
import com.vungn.android.mybase.usecase.UseCase
import javax.inject.Inject

class SetPermissionGrantStateUseCase @Inject constructor(
    private val devicePolicyManager: DevicePolicyManager,
    private val componentName: ComponentName
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
        val grantState = when (params.grantState) {
            GrantState.GRANT -> DevicePolicyManager.PERMISSION_GRANT_STATE_GRANTED
            GrantState.DENY -> DevicePolicyManager.PERMISSION_GRANT_STATE_DENIED
            GrantState.DEFAULT -> DevicePolicyManager.PERMISSION_GRANT_STATE_DEFAULT
        }
        return devicePolicyManager.setPermissionGrantState(
            componentName,
            params.packageName,
            params.permission,
            grantState
        )
    }
}