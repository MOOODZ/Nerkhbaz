package ir.moodz.sarafkoochooloo.data.mapper

import ir.moodz.sarafkoochooloo.data.local.entity.CurrencyEntity
import ir.moodz.sarafkoochooloo.data.mapper.util.DateMapper
import ir.moodz.sarafkoochooloo.data.network.model.PriceDto
import ir.moodz.sarafkoochooloo.domain.model.currency.Currency
import ir.moodz.sarafkoochooloo.domain.model.currency.CurrencyDetail
import ir.moodz.sarafkoochooloo.domain.model.currency.CurrencyInfo

fun PriceDto.toCurrency() : Currency {
    return Currency(
        info = CurrencyInfo.fromTitle(currencyTitle),
        // The received prices are in Rials which we want to convert them to Toman
        currentPrice = currentPrice.dropLast(1).toInt(),
        date = DateMapper.mapPersianDateToPair(updatedDate),
    )
}

fun PriceDto.toCurrencyDetail() : CurrencyDetail {
    return CurrencyDetail(
        sortId = id,
        info = CurrencyInfo.fromTitle(currencyTitle),
        currentPrice = currentPrice.dropLast(1).toInt(),
        date = DateMapper.mapPersianDateToPair(updatedDate),
        dateInWords = DateMapper.mapPersianDateToNumberDate(updatedDate)
    )
}

fun Currency.toCurrencyEntity() : CurrencyEntity {
    return CurrencyEntity(
        id = info.id,
        title = info.title,
        currentPrice = currentPrice,
        updatedTime = DateMapper.mapPairToPersianDate(date),
        type = info.type
    )
}

fun CurrencyEntity.toCurrency() : Currency {
    return Currency(
        info = CurrencyInfo.fromTitle(title),
        currentPrice = currentPrice,
        date = DateMapper.mapPersianDateToPair(updatedTime),
    )
}

