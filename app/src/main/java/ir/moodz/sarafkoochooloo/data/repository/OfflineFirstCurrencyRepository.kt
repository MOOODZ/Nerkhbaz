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
import kotlinx.coroutines.flow.map

class OfflineFirstCurrencyRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val applicationScope: CoroutineScope
) : CurrenciesRepository {

    override fun getCurrencies(): Flow<List<Currency>> {
        return localDataSource.getCurrencies()
    }

    override fun getCurrenciesWithToman(): Flow<List<Currency>> {
        return localDataSource.getCurrencies().map { currencies ->

            val mutableCurrencies = currencies.toMutableList()
            // Added Toman as our default currency in order to converting
            mutableCurrencies.add(
                Currency(
                    info = CurrencyInfo.IranToman,
                    currentPrice = 1,
                    updatedDate = ""
                )
            )
            // Needed different sorting for converting
            mutableCurrencies.sortedWith(
                compareByDescending<Currency> { it.info.type }
                    .thenBy { it.info.id }
            )
        }
    }

    override suspend fun fetchCurrencies(): Result<Unit, DataError> {
        return when (val result = remoteDataSource.getPrices(selectedCurrency = "USD")) {
            is Result.Success -> {
                val currencies = removeInaccurateCurrencies(currencies = result.data)
                applicationScope.async {
                    localDataSource.upsertCurrencies(currencies)
                }.await()
                Result.Success(Unit)
            }

            is Result.Error -> Result.Error(result.error)
        }
    }

    private fun removeInaccurateCurrencies(currencies: List<Currency>): List<Currency> {
        val currencies = currencies.toMutableList()

        currencies.removeIf { it.info.id == CurrencyInfo.OunceGold.id }
        currencies.removeIf { it.info.id == CurrencyInfo.SyrianPound.id }
        currencies.removeIf { it.info.id == CurrencyInfo.IraqiDinar.id }

        return currencies
    }
}