package com.vti.android.delegatedscopemanagement.testapp.ui.main.restriction.contract.impl

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
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
    private val isEnable: MutableLiveData<Boolean> = MutableLiveData(true)
    private val isIncognitoEnable: MutableLiveData<Boolean> = MutableLiveData()
    private val isEditBookmarksEnable: MutableLiveData<Boolean> = MutableLiveData()

    override fun log(): MutableLiveData<Log> = log

    override fun isEnable(): MutableLiveData<Boolean> = isEnable

    override fun isIncognitoEnable(): MutableLiveData<Boolean> = isIncognitoEnable

    override fun isEditBookmarksEnable(): MutableLiveData<Boolean> = isEditBookmarksEnable

    override fun onChangeIncognitoMode(isChecked: Boolean) {
        android.util.Log.d(TAG, "onChangeIncognitoMode: ")
        isIncognitoEnable.postValue(isChecked)
    }

    override fun onChangeEditBookmarks(isChecked: Boolean) {
        android.util.Log.d(TAG, "onChangeEditBookmarks: ")
        isEditBookmarksEnable.postValue(isChecked)
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
                            val value = bundle.getString(key) == "0"
                            if (value) isIncognitoEnable.postValue(true)
                            else isIncognitoEnable.postValue(false)
                        }
                        EDIT_BOOKMARK_KEY -> {
                            android.util.Log.d(
                                TAG,
                                "getStatus: EDIT_BOOKMARK_KEY: ${bundle.getString(key)}"
                            )
                            val value = bundle.getString(key) == "true"
                            if (value) isEditBookmarksEnable.postValue(true)
                            else isEditBookmarksEnable.postValue(false)
                        }
                    }
                }
                isEnable.postValue(true)
            } catch (e: java.lang.Exception) {
                isEnable.postValue(false)
                android.util.Log.e(TAG, "getStatus: ${e.message}")
            }
        }
    }

    override fun restrict() {
        viewModelScope.launch {
            val settings = Bundle()
            settings.putString(
                INCOGNITO_MODE_KEY,
                if (isIncognitoEnable.value == true) "0" else "1"
            )
            settings.putString(
                EDIT_BOOKMARK_KEY,
                if (isEditBookmarksEnable.value == true) "true" else "false"
            )
            try {
                restrictAppUseCase.execute(settings)
                val title = "Restricted"
                log.postValue(Log(title, true))
            } catch (e: java.lang.Exception) {
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