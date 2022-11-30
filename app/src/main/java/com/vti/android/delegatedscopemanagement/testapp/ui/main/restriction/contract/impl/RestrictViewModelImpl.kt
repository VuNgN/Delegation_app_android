package com.vti.android.delegatedscopemanagement.testapp.ui.main.restriction.contract.impl

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.SecurityExceptionLog
import com.vti.android.delegatedscopemanagement.testapp.ui.main.restriction.contract.RestrictViewModel
import com.vti.android.delegatedscopemanagement.testapp.usecase.GetRestrictStatus
import com.vti.android.delegatedscopemanagement.testapp.usecase.RestrictAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestrictViewModelImpl @Inject constructor(
    private val getRestrictStatus: GetRestrictStatus,
    private val restrictAppUseCase: RestrictAppUseCase
) : RestrictViewModel, ViewModel() {
    private val log: MutableLiveData<Log> = MutableLiveData()
    private val isIncognitoDisable: MutableLiveData<Boolean> = MutableLiveData()
    private val isEditBookmarksDisable: MutableLiveData<Boolean> = MutableLiveData()

    override fun log(): MutableLiveData<Log> = log

    override fun isIncognitoDisable(): MutableLiveData<Boolean> = isIncognitoDisable

    override fun isEditBookmarksDisable(): MutableLiveData<Boolean> = isEditBookmarksDisable

    override fun onChangeIncognitoMode(isChecked: Boolean) {
        viewModelScope.launch {
            try {
                getRestrictStatus.execute(Unit)
                isIncognitoDisable.postValue(isChecked)
            } catch (e: Exception) {
                isIncognitoDisable.postValue(false)
            }
        }
    }

    override fun onChangeEditBookmarks(isChecked: Boolean) {
        viewModelScope.launch {
            try {
                getRestrictStatus.execute(Unit)
                isEditBookmarksDisable.postValue(isChecked)
            } catch (e: Exception) {
                isEditBookmarksDisable.postValue(false)
            }
        }
    }

    override fun getStatus() {
        viewModelScope.launch {
            try {
                val bundle = getRestrictStatus.execute(Unit)
                for (key in bundle.keySet()) {
                    when (key) {
                        INCOGNITO_MODE_KEY -> {
                            android.util.Log.d(
                                TAG,
                                "getStatus: INCOGNITO_MODE_KEY: ${bundle.getString(key)}"
                            )
                            val value = bundle.getString(key) == "1"
                            if (value) isIncognitoDisable.postValue(true)
                            else isIncognitoDisable.postValue(false)
                        }
                        EDIT_BOOKMARK_KEY -> {
                            android.util.Log.d(
                                TAG,
                                "getStatus: EDIT_BOOKMARK_KEY: ${bundle.getString(key)}"
                            )
                            val value = bundle.getString(key) == "false"
                            if (value) isEditBookmarksDisable.postValue(true)
                            else isEditBookmarksDisable.postValue(false)
                        }
                    }
                }
            } catch (e: java.lang.Exception) {
                android.util.Log.e(TAG, "getStatus: ${e.message}")
            }
        }
    }

    override fun restrict() {
        viewModelScope.launch {
            val settings = Bundle()
            settings.putString(
                INCOGNITO_MODE_KEY,
                if (isIncognitoDisable.value == true) "1" else "0"
            )
            settings.putString(
                EDIT_BOOKMARK_KEY,
                if (isEditBookmarksDisable.value == true) "false" else "true"
            )
            try {
                restrictAppUseCase.execute(settings)
                val title = "setApplicationRestrictions(): Restricted"
                log.postValue(Log(title, true))
            } catch (e: SecurityException) {
                log.postValue(Log(SecurityExceptionLog, false))
            } catch (e: Exception) {
                log.postValue(Log(e.message.toString(), false))
            }
        }
    }

    companion object {
        private const val INCOGNITO_MODE_KEY = "IncognitoModeAvailability"
        private const val EDIT_BOOKMARK_KEY = "EditBookmarksEnabled"
        private val TAG = RestrictViewModelImpl::class.simpleName
    }
}