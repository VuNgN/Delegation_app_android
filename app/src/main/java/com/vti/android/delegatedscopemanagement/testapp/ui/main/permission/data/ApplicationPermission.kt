package com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.data

data class ApplicationPermission(
    val packageName: String,
    val permission: String,
    val grantState: GrantState
)
