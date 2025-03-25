package ir.moodz.sarafkoochooloo.data.mapper

import ir.moodz.sarafkoochooloo.data.local.entity.CurrencyEntity
import ir.moodz.sarafkoochooloo.data.network.model.DetailPriceDto
import ir.moodz.sarafkoochooloo.data.network.model.Price
import ir.moodz.sarafkoochooloo.domain.model.Currency

fun Price.toCurrency() : Currency {
    return Currency(
        id = id.toInt(),
        title = currencyTitle,
        currentPrice = currentPrice,
        maxPrice = maximumPrice,
        minPrice = minimumPrice,
        updatedDate = timestamp
    )
}

fun Currency.toCurrencyEntity() : CurrencyEntity {
    return CurrencyEntity(
        id = id,
        title = title,
        currentPrice = currentPrice,
        maxPrice = maxPrice,
        minPrice = minPrice,
        updatedTime = updatedDate
    )
}

fun CurrencyEntity.toCurrency() : Currency {
    return Currency(
        id = id,
        title = title,
        currentPrice = currentPrice,
        maxPrice = maxPrice,
        minPrice = minPrice,
        updatedDate = updatedTime
    )
}
