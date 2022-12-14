package com.vti.android.delegatedscopemanagement.testapp.ui.main.restriction.contract

import androidx.lifecycle.MutableLiveData
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log

interface RestrictViewModel {
    fun log(): MutableLiveData<Log>
    fun isIncognitoDisable(): MutableLiveData<Boolean>
    fun isEditBookmarksDisable(): MutableLiveData<Boolean>
    fun onChangeIncognitoMode(isChecked: Boolean)
    fun onChangeEditBookmarks(isChecked: Boolean)
    fun getStatus()
    fun restrict()
}