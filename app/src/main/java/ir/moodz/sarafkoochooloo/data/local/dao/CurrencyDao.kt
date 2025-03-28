package ir.moodz.sarafkoochooloo.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import ir.moodz.sarafkoochooloo.data.local.entity.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Upsert
    suspend fun upsertCurrencies(currencies: List<CurrencyEntity>)

    @Query("SELECT * FROM Currencies ORDER BY id ASC")
    fun getCurrencies(): Flow<List<CurrencyEntity>>
}