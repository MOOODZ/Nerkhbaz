package ir.moodz.sarafkoochooloo.domain.remote

import ir.moodz.sarafkoochooloo.domain.model.Currency
import ir.moodz.sarafkoochooloo.domain.util.DataError
import ir.moodz.sarafkoochooloo.domain.util.Result

interface RemoteDataSource {
    suspend fun getPrices(selectedCurrency: String) : Result<List<Currency>, DataError.Network>
}