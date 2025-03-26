package ir.moodz.sarafkoochooloo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.moodz.sarafkoochooloo.data.local.converter.Converter
import ir.moodz.sarafkoochooloo.data.local.dao.CurrencyDao
import ir.moodz.sarafkoochooloo.data.local.entity.CurrencyEntity

@Database(
    entities = [CurrencyEntity::class],
    version = 2
)
@TypeConverters(Converter::class)
abstract class NerkhbazDatabase : RoomDatabase() {
    abstract val currencyDao: CurrencyDao
}