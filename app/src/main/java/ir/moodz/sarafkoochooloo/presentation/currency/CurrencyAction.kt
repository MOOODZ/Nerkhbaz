package ir.moodz.sarafkoochooloo.presentation.currency

import ir.moodz.sarafkoochooloo.domain.model.Currency

sealed interface CurrencyAction {
    data object OnPullDownRefresh : CurrencyAction
    data object OnToggleConvertCurrencyModal : CurrencyAction
    data class OnSelectStartingCurrency(val currency: String) : CurrencyAction
    data class OnSelectDestinationCurrency(val currency: String) : CurrencyAction
    data object OnSwapConvertingCurrenciesClick : CurrencyAction
    data class OnStartingCurrencyAmountChange(val amount: String) : CurrencyAction
}