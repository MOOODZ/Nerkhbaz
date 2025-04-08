package ir.moodz.sarafkoochooloo.domain.remote

import ir.moodz.sarafkoochooloo.domain.model.CheckUpdate
import ir.moodz.sarafkoochooloo.domain.model.currency.Currency
import ir.moodz.sarafkoochooloo.domain.util.DataError
import ir.moodz.sarafkoochooloo.domain.util.Result

interface RemoteDataSource {
    suspend fun getCurrencies(selectedCurrency: String) : Result<List<Currency>, DataError.Network>
    suspend fun getCurrencyInfoByDays(currencyTitle: String) : Result<List<Currency> , DataError.Network>
    suspend fun isAppVersionValid(versionCode: Int) : Result<CheckUpdate , DataError.Network>
}