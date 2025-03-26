@file:OptIn(ExperimentalMaterial3Api::class)

package ir.moodz.sarafkoochooloo.presentation.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.moodz.sarafkoochooloo.domain.repository.CurrenciesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: CurrenciesRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MainState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                fetchCurrencies()
                hasLoadedInitialData = true
            }
            repository.getCurrencies().onEach { currencies ->
                _state.update { it.copy(currencies = currencies) }
            }.launchIn(viewModelScope)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MainState()
        )

    fun onAction(action: MainAction) {
        when (action) {
            MainAction.OnPullDownRefresh -> fetchCurrencies()
        }
    }

    private fun fetchCurrencies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.fetchCurrencies()
            _state.update { it.copy(isLoading = false) }
        }
    }

}