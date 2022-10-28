package com.vti.android.delegatedscopemanagement.testapp.ui.main.cert.contract

import androidx.lifecycle.MutableLiveData
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log

interface CertViewModel {
    fun log(): MutableLiveData<Log>
    fun getNumberOfCerts()
    fun installCert()
    fun uninstallCert()
    fun uninstallALlCert()
}