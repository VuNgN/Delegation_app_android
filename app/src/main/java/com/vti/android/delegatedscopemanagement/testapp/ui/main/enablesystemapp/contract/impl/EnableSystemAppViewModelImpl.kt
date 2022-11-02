package com.vti.android.delegatedscopemanagement.testapp.ui.main.enablesystemapp.contract.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.ui.main.enablesystemapp.contract.EnableSystemAppViewModel
import com.vti.android.delegatedscopemanagement.testapp.usecase.EnableAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnableSystemAppViewModelImpl @Inject constructor(
    private val enableAppUseCase: EnableAppUseCase
) : EnableSystemAppViewModel, ViewModel() {
    private val log: MutableLiveData<Log> = MutableLiveData()
    private val uri: MutableLiveData<String> = MutableLiveData()

    override fun log(): MutableLiveData<Log> = log
    override fun uri(): MutableLiveData<String> = uri

    override fun enableApp() {
        viewModelScope.launch {
            try {
                val result = enableAppUseCase.execute(uri.value.toString())
                log.postValue(Log("Enable system app with ${uri.value} uri -> $result", true))
            } catch (e: Exception) {
                log.postValue(Log(e.message.toString(), false))
            }
        }
    }
}