package ir.moodz.sarafkoochooloo.domain.model

data class Currency(
    val id: Int,
    val title: String,
    val currentPrice: String,
    val maxPrice: String,
    val minPrice: String,
    val updatedDate: String
)