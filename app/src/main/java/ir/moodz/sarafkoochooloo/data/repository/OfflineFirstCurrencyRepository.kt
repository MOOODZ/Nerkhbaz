package ir.moodz.sarafkoochooloo.data.repository

import ir.moodz.sarafkoochooloo.domain.local.LocalDataSource
import ir.moodz.sarafkoochooloo.domain.model.Currency
import ir.moodz.sarafkoochooloo.domain.model.CurrencyInfo
import ir.moodz.sarafkoochooloo.domain.remote.RemoteDataSource
import ir.moodz.sarafkoochooloo.domain.repository.CurrenciesRepository
import ir.moodz.sarafkoochooloo.domain.util.DataError
import ir.moodz.sarafkoochooloo.domain.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow

class OfflineFirstCurrencyRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val applicationScope: CoroutineScope
) : CurrenciesRepository {

    override fun getCurrencies(): Flow<List<Currency>> {
        return localDataSource.getCurrencies()
    }

    override suspend fun fetchCurrencies(): Result<Unit, DataError> {
        return when (val result = remoteDataSource.getPrices(selectedCurrency = "USD")) {
            is Result.Success -> {
                applicationScope.async {

                    val currencies = result.data.toMutableList()

                    // We don't have toman on API so we manually adding it here for converting
                    currencies.add(
                        Currency(
                            info = CurrencyInfo.IranToman,
                            currentPrice = 1,
                            updatedDate = ""
                        )
                    )
                    // These currencies removed because of the inaccuracy
                    currencies.removeIf { it.info.id == CurrencyInfo.OunceGold.id }
                    currencies.removeIf { it.info.id == CurrencyInfo.SyrianPound.id }

                    localDataSource.upsertCurrencies(currencies)

                }.await()
                Result.Success(Unit)
            }

            is Result.Error -> Result.Error(result.error)
        }
    }
}