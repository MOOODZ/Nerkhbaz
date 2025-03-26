package ir.moodz.sarafkoochooloo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.moodz.sarafkoochooloo.domain.model.CurrencyType

@Entity(tableName = "Currencies")
data class CurrencyEntity(
    val id: Int,
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val currentPrice: Long,
    val updatedTime: String,
    val type: CurrencyType
)
