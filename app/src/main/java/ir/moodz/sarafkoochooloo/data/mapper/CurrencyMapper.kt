package ir.moodz.sarafkoochooloo.data.mapper

import ir.moodz.sarafkoochooloo.data.local.entity.CurrencyEntity
import ir.moodz.sarafkoochooloo.data.network.model.PriceDto
import ir.moodz.sarafkoochooloo.domain.model.Currency
import ir.moodz.sarafkoochooloo.domain.model.CurrencyInfo

fun PriceDto.toCurrency() : Currency {
    return Currency(
        info = CurrencyInfo.fromTitle(currencyTitle),
        currentPrice = currentPrice.dropLast(1).toInt(),
        updatedDate = timestamp,
    )
}

fun Currency.toCurrencyEntity() : CurrencyEntity {
    return CurrencyEntity(
        id = info.id,
        title = info.title,
        currentPrice = currentPrice,
        updatedTime = updatedDate,
        type = info.type
    )
}

fun CurrencyEntity.toCurrency() : Currency {
    return Currency(
        info = CurrencyInfo.fromTitle(title),
        currentPrice = currentPrice,
        updatedDate = updatedTime,
    )
}

