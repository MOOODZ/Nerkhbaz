package ir.moodz.sarafkoochooloo.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesResponseDto(
    @SerialName("data") val currenciesDataDto: CurrenciesDataDto,
    @SerialName("response") val responseDtoInfo: ApiResponseDetailDto
)

@Serializable
data class CurrenciesDataDto(
    @SerialName("live_price") val livePriceDto: List<LivePriceDto>,
    @SerialName("detail_prices") val detailedPrices: List<DetailPriceDto>,
    @SerialName("prices") val priceDtoList: List<PriceDto>
)

@Serializable
data class LivePriceDto(
    @SerialName("current") val currentPrice: String
)

@Serializable
data class DetailPriceDto(
    @SerialName("ID") val id: String,
    @SerialName("title") val title: String,
    @SerialName("min") val minPrice: String,
    @SerialName("max") val maxPrice: String,
    @SerialName("current") val currentPrice: String,
    @SerialName("time") val updatedDate: String
)

@Serializable
data class PriceDto(
    @SerialName("ID") val id: String,
    @SerialName("title") val currencyTitle: String,
    @SerialName("min") val minimumPrice: String,
    @SerialName("max") val maximumPrice: String,
    @SerialName("current") val currentPrice: String,
    @SerialName("time") val updatedDate: String
)