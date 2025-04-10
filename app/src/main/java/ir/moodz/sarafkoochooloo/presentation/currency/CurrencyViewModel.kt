@file:OptIn(ExperimentalMaterial3Api::class)

package ir.moodz.sarafkoochooloo.presentation.currency

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.snapshotFlow
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.moodz.sarafkoochooloo.BuildConfig
import ir.moodz.sarafkoochooloo.domain.model.currency.Currency
import ir.moodz.sarafkoochooloo.domain.model.currency.CurrencyInfo
import ir.moodz.sarafkoochooloo.domain.remote.RemoteDataSource
import ir.moodz.sarafkoochooloo.domain.repository.CurrenciesRepository
import ir.moodz.sarafkoochooloo.domain.util.CurrencyConverter
import ir.moodz.sarafkoochooloo.domain.util.asUiText
import ir.moodz.sarafkoochooloo.domain.util.onError
import ir.moodz.sarafkoochooloo.domain.util.onSuccess
import ir.moodz.sarafkoochooloo.navigation.Destination
import ir.moodz.sarafkoochooloo.navigation.Navigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class CurrencyViewModel(
    private val repository: CurrenciesRepository,
    private val navigator: Navigator,
    private val remoteDataSource: RemoteDataSource
) : ViewModel() {

    private val _events = Channel<CurrencyEvent>()
    val events = _events.receiveAsFlow()

    private var chartScope: Job? = null

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(CurrencyState())
    val state = _state
        .onStart {
            fetchCurrencies()
            if (!hasLoadedInitialData) {
                checkAppVersionValidationForUpdate()
                getMainCurrencies()
                getCurrenciesWithToman()
                updateScrollingDownBehavior()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CurrencyState()
        )

    fun onAction(action: CurrencyAction) {
        when (action) {

            CurrencyAction.OnPullDownRefresh -> fetchCurrencies()

            CurrencyAction.OnToggleConvertCurrencyModal -> {
                _state.update {
                    it.copy(
                        isConvertCurrencyModalVisible = !state.value.isConvertCurrencyModalVisible
                    )
                }
                clearDestinationCurrencyModalState()
            }

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
                val amount = action.amount

                if (isStartingCurrencyValid(amount)) {
                    _state.update { it.copy(startingCurrencyAmount = amount) }
                    calculateDestinationCurrency(amount)
                }
            }

            CurrencyAction.OnToggleDestinationCurrencyModal -> {
                _state.update {
                    it.copy(
                        isDestinationCurrencyModalVisible =
                            !state.value.isDestinationCurrencyModalVisible
                    )
                }
            }

            CurrencyAction.OnToggleStartingCurrencyModal -> {
                _state.update {
                    it.copy(
                        isStartingCurrencyModalVisible =
                            !state.value.isStartingCurrencyModalVisible
                    )
                }
            }

            is CurrencyAction.OnCurrencyChartClick -> getCurrenciesByDays(action.currency)

            CurrencyAction.OnToggleChartModalDismiss -> {
                chartScope?.cancel()
                _state.update {
                    it.copy(
                        isChartModalVisible = false,
                        selectedCurrencyDays = emptyList(),
                        selectedDetailCurrency = null
                    )
                }
            }

            CurrencyAction.OnToggleCurrencySourceInfoModal -> {
                _state.update { it.copy(isSourceModalVisible = !state.value.isSourceModalVisible) }
            }
        }
    }

    private fun checkAppVersionValidationForUpdate() {
        viewModelScope.launch {
            remoteDataSource.isAppVersionValid(
                versionCode = BuildConfig.VERSION_CODE
            )
                .onSuccess { result ->
                    _state.update { it.copy(
                        isAppNeedToUpdate = result.isUpdatedNeeded,
                        updateUrl = result.updateUrl
                    ) }
                }
                .onError {
                    // TODO : Handle update error
                }
        }
    }

    private fun getMainCurrencies() {
        repository.getCurrencies()
            .onEach { currencies ->
                _state.update { it.copy(currencies = currencies) }
            }.launchIn(viewModelScope)
    }

    private fun getCurrenciesWithToman() {
        repository.getCurrenciesWithToman()
            .onEach { currenciesWithToman ->
                _state.update {
                    it.copy(
                        currenciesWithToman = currenciesWithToman,
                        currencyIds = currenciesWithToman.map { it.info.id }
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getCurrenciesByDays(currency: Currency) {
        chartScope?.cancel()
        chartScope = viewModelScope.launch {
            _state.update {
                it.copy(
                    isChartLoading = true,
                    isChartModalVisible = true,
                    selectedDetailCurrency = currency
                )
            }
            repository.getCurrenciesByDays(currency.info.title)
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            isChartLoading = false,
                            selectedCurrencyDays = result
                        )
                    }
                    Timber.tag("CHART_DATA").d(result.toString())
                }
                .onError { error ->
                    _state.update {
                        it.copy(
                            isChartLoading = false,
                            isChartModalVisible = false
                        )
                    }
                    _events.send(CurrencyEvent.Error(message = error.asUiText()))
                }
        }
    }

    private fun clearDestinationCurrencyModalState() {
        _state.update {
            it.copy(
                startingCurrencyId = CurrencyInfo.UnitedStatesDollar.id,
                targetCurrencyId = CurrencyInfo.IranToman.id,
                startingCurrencyAmount = "",
                destinationCurrencyAmount = ""
            )
        }
    }

    private fun isStartingCurrencyValid(amount: String): Boolean {
        if (amount.startsWith("0")) {
            return false
        }
        if (!amount.isDigitsOnly()) {
            return false
        }
        if (amount.length >= 12) {
            return false
        }
        return true
    }

    private fun calculateDestinationCurrency(
        startingAmount: String = state.value.startingCurrencyAmount
    ) {

        if (startingAmount.isBlank()) {
            _state.update { it.copy(destinationCurrencyAmount = "") }
            return
        }

        val startingCurrencyRate = state.value.currenciesWithToman
            .find { it.info.id == state.value.startingCurrencyId }?.currentPrice?.toLong() ?: return

        val targetCurrencyRate = state.value.currenciesWithToman
            .find { it.info.id == state.value.targetCurrencyId }?.currentPrice?.toLong() ?: return

        val calculatedAmount =
            CurrencyConverter.calculate(
                amount = startingAmount.toLong(),
                startingRate = startingCurrencyRate,
                targetRate = targetCurrencyRate
            )

        calculatedAmount?.let { result ->
            _state.update {
                it.copy(destinationCurrencyAmount = result.toString())
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

    private fun updateScrollingDownBehavior() {

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