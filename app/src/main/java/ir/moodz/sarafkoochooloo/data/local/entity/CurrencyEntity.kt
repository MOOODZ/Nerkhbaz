package ir.moodz.sarafkoochooloo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Currencies")
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val currentPrice: String,
    val updatedTime: String,
    val maxPrice: String,
    val minPrice: String
)
