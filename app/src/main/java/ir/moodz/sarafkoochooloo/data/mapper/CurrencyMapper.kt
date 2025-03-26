package ir.moodz.sarafkoochooloo.data.mapper

import ir.moodz.sarafkoochooloo.data.local.entity.CurrencyEntity
import ir.moodz.sarafkoochooloo.data.network.model.PriceDto
import ir.moodz.sarafkoochooloo.domain.model.Currency
import ir.moodz.sarafkoochooloo.domain.model.CurrencyType

fun PriceDto.toCurrency() : Currency {
    return Currency(
        title = currencyTitle,
        currentPrice = currentPrice.dropLast(1).toLong(),
        updatedDate = timestamp,
        type = CurrencyType.determineCurrencyType(currencyTitle)
    )
}

fun Currency.toCurrencyEntity() : CurrencyEntity {
    return CurrencyEntity(
        title = title,
        currentPrice = currentPrice,
        updatedTime = updatedDate,
        type = type
    )
}

fun CurrencyEntity.toCurrency() : Currency {
    return Currency(
        title = title,
        currentPrice = currentPrice,
        updatedDate = updatedTime,
        type = type
    )
}

