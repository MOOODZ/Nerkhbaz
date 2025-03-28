package ir.moodz.sarafkoochooloo.data

import ir.moodz.sarafkoochooloo.domain.Calculator
import ir.moodz.sarafkoochooloo.domain.model.CurrencyInfo
import ir.moodz.sarafkoochooloo.domain.repository.CurrenciesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.withContext
import timber.log.Timber

class CurrencyCalculator(
    private val repository: CurrenciesRepository
) : Calculator {
    override suspend fun getCurrencyRate(
        firstCurrency: CurrencyInfo,
        secondCurrency: CurrencyInfo
    ): Double? {
        return withContext(Dispatchers.Default) {
            try {
                require(firstCurrency.id != secondCurrency.id) {
                    "Cannot calculate rate between identical currencies"
                }

                val currencies = withContext(Dispatchers.IO) { repository.getCurrencies().last() }

                val currencyMap = currencies.associateBy { it.info.id }

                val firstRate = currencyMap[firstCurrency.id]?.currentPrice?.toDouble()
                    ?: throw IllegalStateException("Currency not found: ${firstCurrency.id}")
                val secondRate = currencyMap[secondCurrency.id]?.currentPrice?.toDouble()
                    ?: throw IllegalStateException("Currency not found: ${secondCurrency.id}")

                require(secondRate != 0.0) { "Second currency rate cannot be zero" }

                firstRate / secondRate
            } catch (e: Exception) {
                ensureActive()
                Timber.e(e)
                null
            }
        }

    }
}