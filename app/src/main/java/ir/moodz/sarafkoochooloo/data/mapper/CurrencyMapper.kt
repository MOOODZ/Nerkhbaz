package ir.moodz.sarafkoochooloo.data.mapper

import ir.moodz.sarafkoochooloo.data.local.entity.CurrencyEntity
import ir.moodz.sarafkoochooloo.data.mapper.util.DateMapper
import ir.moodz.sarafkoochooloo.data.network.model.PriceDto
import ir.moodz.sarafkoochooloo.domain.model.currency.Currency
import ir.moodz.sarafkoochooloo.domain.model.currency.CurrencyInfo

fun PriceDto.toCurrency() : Currency {
    return Currency(
        info = CurrencyInfo.fromTitle(currencyTitle),
        // The received prices are in Rials which we want to convert them to Toman
        currentPrice = currentPrice.dropLast(1).toInt(),
        updatedDate = DateMapper.mapPersianDateToPair(updatedDate),
    )
}

fun Currency.toCurrencyEntity() : CurrencyEntity {
    return CurrencyEntity(
        id = info.id,
        title = info.title,
        currentPrice = currentPrice,
        updatedTime = DateMapper.mapPairToPersianDate(updatedDate),
        type = info.type
    )
}

fun CurrencyEntity.toCurrency() : Currency {
    return Currency(
        info = CurrencyInfo.fromTitle(title),
        currentPrice = currentPrice,
        updatedDate = DateMapper.mapPersianDateToPair(updatedTime),
    )
}

