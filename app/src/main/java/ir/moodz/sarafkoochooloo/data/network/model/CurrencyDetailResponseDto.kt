package ir.moodz.sarafkoochooloo.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrenciesDetailResponseDto(
    @SerialName("data") val currencyDetailData: CurrencyDetailDataDto,
    @SerialName("response") val responseDtoInfo: ApiResponseDetailDto
)

@Serializable
data class CurrencyDetailDataDto(
    @SerialName("detail_prices") val detailedCurrencyPrices: List<PriceDto>,
)
