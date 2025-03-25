package ir.moodz.sarafkoochooloo.domain.local

import ir.moodz.sarafkoochooloo.domain.model.Currency
import ir.moodz.sarafkoochooloo.domain.util.DataError
import ir.moodz.sarafkoochooloo.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getCurrencies(): Flow<List<Currency>>
    suspend fun upsertCurrencies(currencies: List<Currency>) : Result<List<Currency>, DataError.Local>
}