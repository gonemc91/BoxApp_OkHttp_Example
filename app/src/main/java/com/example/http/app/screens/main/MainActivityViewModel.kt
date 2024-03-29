package com.example.http.app.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.http.app.Singletons
import com.example.http.app.model.accounts.AccountsRepository
import com.example.http.app.model.accounts.AccountsSources
import com.example.http.app.utils.share
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val accountsRepository: AccountsRepository = Singletons.accountsRepository
) : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username = _username.share()

    init {
        viewModelScope.launch {
            // listening for the current account and send the username to be displayed in the toolbar
            accountsRepository.getAccount().collect { result ->
                _username.value = result.getValueOrNull()?.userName?.let { "@$it" } ?: ""
            }
        }
    }

}
