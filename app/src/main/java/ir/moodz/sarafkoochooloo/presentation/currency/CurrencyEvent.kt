package ir.moodz.sarafkoochooloo.presentation.currency

import ir.moodz.sarafkoochooloo.presentation.util.UiText

sealed interface CurrencyEvent {
    data class Error(val message: UiText) : CurrencyEvent
}