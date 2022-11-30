package com.vti.android.delegatedscopemanagement.testapp.ui.main.cert.contract.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.SecurityExceptionLog
import com.vti.android.delegatedscopemanagement.testapp.ui.main.cert.contract.CertViewModel
import com.vti.android.delegatedscopemanagement.testapp.usecase.GetNumberOfCerts
import com.vti.android.delegatedscopemanagement.testapp.usecase.InstallCertUseCase
import com.vti.android.delegatedscopemanagement.testapp.usecase.UninstallAllCertUseCase
import com.vti.android.delegatedscopemanagement.testapp.usecase.UninstallCertUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CertViewModelImpl @Inject constructor(
    private val getNumberOfCerts: GetNumberOfCerts,
    private val installCertUseCase: InstallCertUseCase,
    private val uninstallCertUseCase: UninstallCertUseCase,
    private val uninstallAllCertUseCase: UninstallAllCertUseCase
) : CertViewModel, ViewModel() {
    private val log: MutableLiveData<Log> = MutableLiveData()

    override fun log(): MutableLiveData<Log> = log

    override fun getNumberOfCerts() {
        viewModelScope.launch {
            try {
                val number = getNumberOfCerts.execute(Unit)
                val title = "getInstalledCaCerts() -> Number of CA Cert: $number"
                log.postValue(Log(title, true))
            } catch (e: SecurityException) {
                log.postValue(Log(SecurityExceptionLog, false))
            } catch (e: Exception) {
                log.postValue(Log(e.message.toString(), false))
            }
        }
    }

    override fun installCert() {
        viewModelScope.launch {
            try {
                val result = installCertUseCase.execute(Unit)
                val title =
                    "installCaCert() -> Install 'Example 1' CA Cert: ${if (result) "Installed" else "Something wrong"}"
                log.postValue(Log(title, true))
            } catch (e: SecurityException) {
                log.postValue(Log(SecurityExceptionLog, false))
            } catch (e: Exception) {
                log.postValue(Log(e.message.toString(), false))
            }
        }
    }

    override fun uninstallCert() {
        viewModelScope.launch {
            try {
                val result = uninstallCertUseCase.execute(Unit)
                val title =
                    "uninstallCaCert() -> Uninstall 'Example 1' CA Cert: ${if (result) "Uninstalled" else "Cert list is empty"}"
                log.postValue(Log(title, true))
            } catch (e: SecurityException) {
                log.postValue(Log(SecurityExceptionLog, false))
            } catch (e: Exception) {
                log.postValue(Log(e.message.toString(), false))
            }
        }
    }

    override fun uninstallALlCert() {
        viewModelScope.launch {
            try {
                uninstallAllCertUseCase.execute(Unit)
                val title = "uninstallAllUserCaCerts() -> Uninstall all CA Cert: Uninstalled"
                log.postValue(Log(title, true))
            } catch (e: SecurityException) {
                log.postValue(Log(SecurityExceptionLog, false))
            } catch (e: Exception) {
                log.postValue(Log(e.message.toString(), false))
            }
        }
    }
}