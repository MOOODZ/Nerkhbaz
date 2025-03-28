package ir.moodz.sarafkoochooloo.domain

import ir.moodz.sarafkoochooloo.domain.model.CurrencyInfo

interface Calculator {
    suspend fun getCurrencyRate(firstCurrency: CurrencyInfo, secondCurrency: CurrencyInfo): Double?
}