package ir.moodz.sarafkoochooloo.domain.model

data class Currency(
    val info: CurrencyInfo,
    val currentPrice: Int,
    val updatedDate: String,
)

