package com.vti.android.delegatedscopemanagement.testapp.ui.main.menu.contract

import androidx.lifecycle.MutableLiveData

interface MenuViewModel {
    fun scopes(): MutableLiveData<List<String>>
    fun getScopes()
}