@file:OptIn(ExperimentalMaterial3Api::class)

package ir.moodz.sarafkoochooloo.presentation.currency

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.moodz.sarafkoochooloo.domain.repository.CurrenciesRepository
import ir.moodz.sarafkoochooloo.domain.util.asUiText
import ir.moodz.sarafkoochooloo.domain.util.onError
import ir.moodz.sarafkoochooloo.domain.util.onSuccess
import ir.moodz.sarafkoochooloo.navigation.Destination
import ir.moodz.sarafkoochooloo.navigation.Navigator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CurrencyViewModel(
    private val repository: CurrenciesRepository,
    private val navigator: Navigator
) : ViewModel() {

    private val _events = Channel<CurrencyEvent>()
    val events = _events.receiveAsFlow()

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CurrencyState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                fetchCurrencies()
                repository.getCurrencies()
                    .onEach {  currencies ->
                        _state.update { it.copy(currenciesWithToman = currencies) }
                    }
                    .map { currencies ->
                        currencies.filterNot { it.info.id == 0 }
                    }
                    .onEach { currencies ->
                        _state.update { it.copy(currencies = currencies) }
                    }.launchIn(viewModelScope)
                hasLoadedInitialData = true
            }
            detectScrollingDown()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CurrencyState()
        )

    fun onAction(action: CurrencyAction) {
        when (action) {
            CurrencyAction.OnPullDownRefresh -> fetchCurrencies()
            CurrencyAction.OnToggleConvertCurrencyModal -> toggleConvertCurrencyModal()
            is CurrencyAction.OnSelectDestinationCurrency -> {
                _state.update { it.copy(targetCurrencyId = action.currencyId) }
                calculateDestinationCurrency()
            }

            is CurrencyAction.OnSelectStartingCurrency -> {
                _state.update { it.copy(startingCurrencyId = action.currencyId) }
                calculateDestinationCurrency()
            }

            CurrencyAction.OnSwapConvertingCurrenciesClick -> swapSelectedCurrencies()
            is CurrencyAction.OnStartingCurrencyAmountChange -> {
                _state.update { it.copy(startingCurrencyAmount = action.amount) }
                calculateDestinationCurrency(action.amount)
            }
        }
    }

    private fun calculateDestinationCurrency(amount: String = state.value.startingCurrencyAmount) {

        if (amount.isBlank()) {
            _state.update { it.copy(destinationCurrencyAmount = "") }
            return
        }

        val startingCurrencyRate = state.value.currenciesWithToman
            .find { it.info.id == state.value.startingCurrencyId }?.currentPrice?.toLong() ?: return

        val targetCurrencyRate =  state.value.currenciesWithToman
            .find { it.info.id == state.value.targetCurrencyId }?.currentPrice?.toLong() ?: return

        val destinationCurrencyRate = (startingCurrencyRate * amount.toLong()) / targetCurrencyRate

        if (destinationCurrencyRate > 0) {
            _state.update {
                it.copy(destinationCurrencyAmount = destinationCurrencyRate.toString())
            }
        }
    }

    private fun swapSelectedCurrencies() {
        _state.update {
            it.copy(
                startingCurrencyId = state.value.targetCurrencyId,
                targetCurrencyId = state.value.startingCurrencyId,
                destinationCurrencyAmount = state.value.startingCurrencyAmount,
                startingCurrencyAmount = state.value.destinationCurrencyAmount
            )
        }
    }

    private fun toggleConvertCurrencyModal() {
        _state.update {
            it.copy(
                isConvertCurrencyModalVisible = !state.value.isConvertCurrencyModalVisible
            )
        }
    }

    private fun navigateToConvertScreen() {
        viewModelScope.launch {
            navigator.navigateTo(Destination.Convert)
        }
    }

    private fun fetchCurrencies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.fetchCurrencies()
                .onSuccess { _state.update { it.copy(isLoading = false) } }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _events.send(CurrencyEvent.Error(message = error.asUiText()))
                }
        }
    }

    private fun detectScrollingDown() {

        var previousIndex = 0
        var previousScrollOffset = 0

        snapshotFlow {
            Pair(
                state.value.lazyListState.firstVisibleItemIndex,
                state.value.lazyListState.firstVisibleItemScrollOffset
            )
        }.onEach { (currentIndex, currentScrollOffset) ->
            val isScrollingDown = when {
                currentIndex > previousIndex -> true
                currentIndex < previousIndex -> false
                else -> currentScrollOffset > previousScrollOffset
            }

            _state.update { it.copy(isScrollingDown = isScrollingDown) }

            previousIndex = currentIndex
            previousScrollOffset = currentScrollOffset

        }.launchIn(viewModelScope)
    }
}