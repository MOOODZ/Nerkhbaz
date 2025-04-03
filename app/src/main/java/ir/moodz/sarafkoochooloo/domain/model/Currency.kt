package ir.moodz.sarafkoochooloo.domain.model

typealias Day = Int
typealias Month = Int
data class Currency(
    val info: CurrencyInfo,
    val currentPrice: Int,
    val updatedDate: Pair<Month, Day>,
)

