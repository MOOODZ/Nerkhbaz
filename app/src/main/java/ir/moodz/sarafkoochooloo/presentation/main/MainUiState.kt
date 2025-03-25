package ir.moodz.sarafkoochooloo.presentation.main

import ir.moodz.sarafkoochooloo.domain.model.Currency

data class MainState(
    val currencies: List<Currency> = emptyList(),
)