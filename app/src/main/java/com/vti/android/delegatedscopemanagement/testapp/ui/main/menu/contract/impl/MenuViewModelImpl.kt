package com.vti.android.delegatedscopemanagement.testapp.ui.main.menu.contract.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vti.android.delegatedscopemanagement.testapp.ui.main.menu.contract.MenuViewModel
import com.vti.android.delegatedscopemanagement.testapp.usecase.GetScopesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModelImpl @Inject constructor(private val getScopesUseCase: GetScopesUseCase) :
    MenuViewModel, ViewModel() {
    private val scopes: MutableLiveData<List<String>> = MutableLiveData()

    override fun scopes(): MutableLiveData<List<String>> = scopes

    override fun getScopes() {
        viewModelScope.launch {
            scopes.postValue(getScopesUseCase.execute(Unit))
        }
    }
}