package ir.moodz.sarafkoochooloo.data.local

import android.database.sqlite.SQLiteFullException
import ir.moodz.sarafkoochooloo.data.local.dao.CurrencyDao
import ir.moodz.sarafkoochooloo.data.mapper.toCurrency
import ir.moodz.sarafkoochooloo.data.mapper.toCurrencyEntity
import ir.moodz.sarafkoochooloo.domain.local.LocalDataSource
import ir.moodz.sarafkoochooloo.domain.model.Currency
import ir.moodz.sarafkoochooloo.domain.util.DataError
import ir.moodz.sarafkoochooloo.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

class RoomLocalDataSource(
    private val currencyDao: CurrencyDao
) : LocalDataSource  {
    override fun getCurrencies(): Flow<List<Currency>> {
        return currencyDao.getCurrencies().map { entities ->
            entities.map { it.toCurrency() }
        }
    }

    override suspend fun upsertCurrencies(currencies: List<Currency>): Result<List<Currency>, DataError.Local> {
        return try {
            val currencyEntities = currencies.map { it.toCurrencyEntity() }
            currencyDao.upsertCurrencies(currencyEntities)
            Result.Success(currencyEntities.map { it.toCurrency() })
        } catch (e: SQLiteFullException){
            Timber.e(e)
            Result.Error(DataError.Local.DISK_FULL)
        } catch (e: Exception){
            Timber.e(e)
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}