package com.vti.android.delegatedscopemanagement.testapp.ui.main.blockuninstall.contract.impl


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.ui.main.blockuninstall.contract.BlockUninstallViewModel
import com.vti.android.delegatedscopemanagement.testapp.usecase.BlockUninstallUseCase
import com.vti.android.delegatedscopemanagement.testapp.usecase.GetBlockUninstallUseCase
import com.vti.android.delegatedscopemanagement.testapp.usecase.UnBlockUninstallUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlockUninstallViewModelImpl @Inject constructor(
    private val blockUninstallUseCase: BlockUninstallUseCase,
    private val unBlockUninstallUseCase: UnBlockUninstallUseCase,
    private val getBlockUninstallUseCase: GetBlockUninstallUseCase
) : BlockUninstallViewModel, ViewModel() {
    private val log: MutableLiveData<Log> = MutableLiveData()
    private val packageName: MutableLiveData<String> = MutableLiveData()
    private val isBlocked: MutableLiveData<Boolean> = MutableLiveData()

    override fun log(): MutableLiveData<Log> = log
    override fun packageName(): MutableLiveData<String> = packageName
    override fun isBlocked(): MutableLiveData<Boolean> = isBlocked

    override fun blockUninstall() {
        viewModelScope.launch {
            try {
                if (isBlocked.value == true) {
                    unBlockUninstallUseCase.execute(packageName.value.toString())
                    log.postValue(Log("Un-blocked", true))
                } else {
                    blockUninstallUseCase.execute(packageName.value.toString())
                    log.postValue(Log("Blocked", true))
                }
                getBlockUninstall()
            } catch (e: java.lang.Exception) {
                log.postValue(Log(e.message.toString(), false))
            }
        }
    }

    override fun getBlockUninstall() {
        viewModelScope.launch {
            try {
                isBlocked.postValue(getBlockUninstallUseCase.execute(packageName.value.toString()))
            } catch (e: Exception) {
                android.util.Log.e(TAG, "getBlockUninstall: ${e.message}")
            }
        }
    }

    companion object {
        private val TAG = BlockUninstallViewModelImpl::class.simpleName
    }
}