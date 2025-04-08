package ir.moodz.sarafkoochooloo.presentation.currency

import ir.moodz.sarafkoochooloo.domain.model.currency.Currency

sealed interface CurrencyAction {
    data object OnPullDownRefresh : CurrencyAction
    data object OnToggleConvertCurrencyModal : CurrencyAction
    data class OnSelectStartingCurrency(val currencyId: Int) : CurrencyAction
    data class OnSelectDestinationCurrency(val currencyId: Int) : CurrencyAction
    data object OnSwapConvertingCurrenciesClick : CurrencyAction
    data class OnStartingCurrencyAmountChange(val amount: String) : CurrencyAction
    data object OnToggleStartingCurrencyModal : CurrencyAction
    data object OnToggleDestinationCurrencyModal : CurrencyAction
    data class OnCurrencyChartClick(val currency: Currency) : CurrencyAction
    data object OnToggleChartModalDismiss : CurrencyAction
}