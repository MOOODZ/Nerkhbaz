package ir.moodz.sarafkoochooloo.domain.model

data class Currency(
    val id: Int,
    val title: String,
    val currentPrice: Long,
    val updatedDate: String,
    val type: CurrencyType
)

enum class CurrencyType {
    COMMODITY, CURRENCY;
    companion object {
        private val commodityKeys = listOf(
            "ROB",
            "GERAMI",
            "NIM",
            "SEKEE_EMAMI",
            "SEKE_BAHAR",
            "ONS",
            "GERAMI_18",
            "GERAMI_24",
            "MESGHAL"
        )
        fun determineCurrencyType(title: String): CurrencyType {
            return if (commodityKeys.contains(title)) COMMODITY else CURRENCY
        }
    }
}