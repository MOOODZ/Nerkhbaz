package ir.moodz.sarafkoochooloo.domain.repository

import ir.moodz.sarafkoochooloo.domain.model.Currency
import ir.moodz.sarafkoochooloo.domain.util.DataError
import ir.moodz.sarafkoochooloo.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface CurrenciesRepository {
    fun getCurrencies() : Flow<List<Currency>>
    fun getCurrenciesWithToman() : Flow<List<Currency>>
    suspend fun fetchCurrencies() : Result<Unit,DataError>
    suspend fun getCurrenciesByDays(currencyTitle: String) : Result<List<Currency>, DataError>
}