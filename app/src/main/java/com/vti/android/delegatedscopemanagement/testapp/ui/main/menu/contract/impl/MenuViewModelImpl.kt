package com.vti.android.delegatedscopemanagement.testapp.ui.main.menu.contract.impl

import android.app.admin.DevicePolicyManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vti.android.delegatedscopemanagement.testapp.BuildConfig
import com.vti.android.delegatedscopemanagement.testapp.ui.main.menu.contract.MenuViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModelImpl @Inject constructor(devicePolicyManager: DevicePolicyManager) :
    MenuViewModel, ViewModel() {
    private val scopes = devicePolicyManager.getDelegatedScopes(null, BuildConfig.APPLICATION_ID)
    private val isCert: MutableLiveData<Boolean> =
        MutableLiveData(scopes.contains(DevicePolicyManager.DELEGATION_CERT_INSTALL))
    private val isManagedConfig: MutableLiveData<Boolean> =
        MutableLiveData(scopes.contains(DevicePolicyManager.DELEGATION_APP_RESTRICTIONS))
    private val isBlockUninstall: MutableLiveData<Boolean> =
        MutableLiveData(scopes.contains(DevicePolicyManager.DELEGATION_BLOCK_UNINSTALL))
    private val isPermission: MutableLiveData<Boolean> =
        MutableLiveData(scopes.contains(DevicePolicyManager.DELEGATION_PERMISSION_GRANT))
    private val isPackageAccess: MutableLiveData<Boolean> =
        MutableLiveData(scopes.contains(DevicePolicyManager.DELEGATION_PACKAGE_ACCESS))
    private val isEnableSystemApp: MutableLiveData<Boolean> =
        MutableLiveData(scopes.contains(DevicePolicyManager.DELEGATION_ENABLE_SYSTEM_APP))

    @RequiresApi(Build.VERSION_CODES.P)
    private val isPackageManagement: MutableLiveData<Boolean> = MutableLiveData(
        scopes.contains(DevicePolicyManager.DELEGATION_KEEP_UNINSTALLED_PACKAGES) || scopes.contains(
            DevicePolicyManager.DELEGATION_INSTALL_EXISTING_PACKAGE
        )
    )

    @RequiresApi(Build.VERSION_CODES.S)
    private val isLogging: MutableLiveData<Boolean> = MutableLiveData(
        scopes.contains(DevicePolicyManager.DELEGATION_NETWORK_LOGGING) || scopes.contains(
            DevicePolicyManager.DELEGATION_SECURITY_LOGGING
        )
    )

    override fun isCert(): MutableLiveData<Boolean> = isCert

    override fun isManagedConfig(): MutableLiveData<Boolean> = isManagedConfig

    override fun isBlockUninstall(): MutableLiveData<Boolean> = isBlockUninstall

    override fun isPermission(): MutableLiveData<Boolean> = isPermission

    override fun isPackageAccess(): MutableLiveData<Boolean> = isPackageAccess

    override fun isEnableSystemApp(): MutableLiveData<Boolean> = isEnableSystemApp

    @RequiresApi(Build.VERSION_CODES.P)
    override fun isPackageManagement(): MutableLiveData<Boolean> = isPackageManagement

    @RequiresApi(Build.VERSION_CODES.S)
    override fun isLogging(): MutableLiveData<Boolean> = isLogging
}