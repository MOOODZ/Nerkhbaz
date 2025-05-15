package ir.moodz.sarafkoochooloo.domain.model.currency

data class CurrencyDetail(
    val sortId: String,
    val info: CurrencyInfo,
    val currentPrice: Int,
    val date: Pair<Month, Day>,
    val dateInWords: String = ""
)
