package ir.moodz.sarafkoochooloo.data.local.converter

import androidx.room.TypeConverter
import ir.moodz.sarafkoochooloo.domain.model.CurrencyType

class Converter {
    @TypeConverter
    fun fromCurrencyType(value: CurrencyType): String {
        return value.name
    }
    @TypeConverter
    fun toCurrencyType(value: String): CurrencyType {
        return enumValueOf(value)
    }
}