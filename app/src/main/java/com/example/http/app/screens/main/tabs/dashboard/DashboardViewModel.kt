package com.example.http.app.screens.main.tabs.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.http.app.Singletons
import com.example.http.app.model.accounts.AccountsRepository
import com.example.http.app.model.boxes.BoxesRepository
import com.example.http.app.model.boxes.entities.Box
import com.example.http.app.model.boxes.entities.BoxesFilter
import com.example.http.app.screens.base.BaseViewModel
import com.example.http.app.utils.logger.LogCatLogger
import com.example.http.app.utils.logger.Logger
import com.example.http.app.utils.share
import kotlinx.coroutines.launch
import com.example.http.app.Result


class DashboardViewModel(
    private val boxesRepository: BoxesRepository = Singletons.boxesRepository,
    accountsRepository: AccountsRepository = Singletons.accountsRepository,
    logger: Logger = LogCatLogger
) : BaseViewModel(accountsRepository, logger) {

    private val _boxes = MutableLiveData<Result<List<Box>>>()
    val boxes = _boxes.share()

    init {
        viewModelScope.launch {
            boxesRepository.getBoxesAndSettings(BoxesFilter.ONLY_ACTIVE).collect { result ->
                _boxes.value = result.map { list -> list.map { it.box } }
            }
        }
    }

    fun reload() = viewModelScope.launch {
        boxesRepository.reload(BoxesFilter.ONLY_ACTIVE)
    }

}