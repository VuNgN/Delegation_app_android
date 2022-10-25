package com.vti.android.delegatedscopemanagement.testapp.ui.main.menu.contract

import androidx.lifecycle.MutableLiveData

interface MenuViewModel {
    fun isCert(): MutableLiveData<Boolean>
    fun isManagedConfig(): MutableLiveData<Boolean>
    fun isBlockUninstall(): MutableLiveData<Boolean>
    fun isPermission(): MutableLiveData<Boolean>
    fun isPackageAccess(): MutableLiveData<Boolean>
    fun isEnableSystemApp(): MutableLiveData<Boolean>
    fun isPackageManagement(): MutableLiveData<Boolean>
    fun isLogging(): MutableLiveData<Boolean>
}