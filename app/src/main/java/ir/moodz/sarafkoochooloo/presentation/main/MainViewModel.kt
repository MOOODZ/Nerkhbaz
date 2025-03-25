package ir.moodz.sarafkoochooloo.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.moodz.sarafkoochooloo.domain.repository.CurrenciesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val repository: CurrenciesRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MainState())
    val state = _state
        .onStart {
            repository.fetchCurrencies()
            repository.getCurrencies().onEach { currencies ->
                _state.update { it.copy(currencies = currencies) }
            }.launchIn(viewModelScope)
            if (!hasLoadedInitialData) {
                /** Load initial data here **/

                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MainState()
        )

    fun onAction(action: MainAction) {
        when (action) {
            else -> TODO("Handle actions")
        }
    }

}