package ir.moodz.sarafkoochooloo.data.repository

import ir.moodz.sarafkoochooloo.domain.local.LocalDataSource
import ir.moodz.sarafkoochooloo.domain.model.Currency
import ir.moodz.sarafkoochooloo.domain.remote.RemoteDataSource
import ir.moodz.sarafkoochooloo.domain.repository.CurrenciesRepository
import ir.moodz.sarafkoochooloo.domain.util.DataError
import ir.moodz.sarafkoochooloo.domain.util.EmptyResult
import ir.moodz.sarafkoochooloo.domain.util.Result
import ir.moodz.sarafkoochooloo.domain.util.asEmptyDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class OfflineFirstCurrencyRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val applicationScope: CoroutineScope
): CurrenciesRepository {

    override fun getCurrencies(): Flow<List<Currency>> {
        return localDataSource.getCurrencies()
    }

    override suspend fun fetchCurrencies(): EmptyResult<DataError>{
        return when(val result = remoteDataSource.getPrices(selectedCurrency = "USD")) {
            is Result.Success -> {
                applicationScope.async {
                    localDataSource.upsertCurrencies(result.data).asEmptyDataResult()
                }.await()
            }
            is Result.Error -> result.asEmptyDataResult()
        }
    }
}