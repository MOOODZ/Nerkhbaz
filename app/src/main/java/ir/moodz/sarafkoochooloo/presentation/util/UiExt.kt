package ir.moodz.sarafkoochooloo.presentation.util

import android.content.Context
import ir.moodz.sarafkoochooloo.R

fun Int.toThousandSeparator(): String {
    return this.toString()
        .reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()
}

fun Long?.toThousandSeparator(): String {
    return this?.toString()
        ?.reversed()
        ?.chunked(3)
        ?.joinToString(",")
        ?.reversed() ?: ""
}

fun String.toThousandSeparator(): String {
    return this
        .reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()
}

fun Double.toThousandSeparator(): String {
    return this.toLong().toString()
        .reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()
}

fun convertCurrencyTitleToText(title: String): UiText {
    return when (title.uppercase()) {
        "USD" -> UiText.StringResource(R.string.usd)
        "GEL" -> UiText.StringResource(R.string.gel)
        "AMD" -> UiText.StringResource(R.string.amd)
        "AZN" -> UiText.StringResource(R.string.azn)
        "RUB" -> UiText.StringResource(R.string.rub)
        "THB" -> UiText.StringResource(R.string.thb)
        "MYR" -> UiText.StringResource(R.string.myr)
        "HKD" -> UiText.StringResource(R.string.hkd)
        "SGD" -> UiText.StringResource(R.string.sgd)
        "PKR" -> UiText.StringResource(R.string.pkr)
        "INR" -> UiText.StringResource(R.string.inr)
        "SYP" -> UiText.StringResource(R.string.syp)
        "BHD" -> UiText.StringResource(R.string.bhd)
        "IQD" -> UiText.StringResource(R.string.iqd)
        "OMR" -> UiText.StringResource(R.string.omr)
        "QAR" -> UiText.StringResource(R.string.qar)
        "SAR" -> UiText.StringResource(R.string.sar)
        "KWD" -> UiText.StringResource(R.string.kwd)
        "NOK" -> UiText.StringResource(R.string.nok)
        "DKK" -> UiText.StringResource(R.string.dkk)
        "SEK" -> UiText.StringResource(R.string.sek)
        "AFN" -> UiText.StringResource(R.string.afn)
        "NZD" -> UiText.StringResource(R.string.nzd)
        "AUD" -> UiText.StringResource(R.string.aud)
        "GERAMI" -> UiText.StringResource(R.string.gerami)
        "CAD" -> UiText.StringResource(R.string.cad)
        "ROB" -> UiText.StringResource(R.string.rob)
        "JPY" -> UiText.StringResource(R.string.jpy)
        "NIM" -> UiText.StringResource(R.string.nim)
        "CNY" -> UiText.StringResource(R.string.cny)
        "SEKEE_EMAMI" -> UiText.StringResource(R.string.sekee_emami)
        "TRY" -> UiText.StringResource(R.string.try_turkey)
        "SEKE_BAHAR" -> UiText.StringResource(R.string.seke_bahar)
        "AED" -> UiText.StringResource(R.string.aed)
        "ONS" -> UiText.StringResource(R.string.ons)
        "GBP" -> UiText.StringResource(R.string.gbp)
        "GERAMI_18" -> UiText.StringResource(R.string.gerami_18)
        "EUR" -> UiText.StringResource(R.string.eur)
        "MESGHAL" -> UiText.StringResource(R.string.mesghal)
        "GERAMI_24" -> UiText.StringResource(R.string.gerami_24)
        else -> UiText.DynamicString(title)
    }
}

fun determineId(title: String): Int {
    return when (title.uppercase()) { // Convert to uppercase for case-insensitivity
        "USD" -> 1
        "GEL" -> 2
        "AMD" -> 3
        "AZN" -> 4
        "RUB" -> 5
        "THB" -> 6
        "MYR" -> 7
        "HKD" -> 8
        "SGD" -> 9
        "PKR" -> 10
        "INR" -> 11
        "SYP" -> 12
        "BHD" -> 13
        "IQD" -> 14
        "OMR" -> 15
        "QAR" -> 16
        "SAR" -> 17
        "KWD" -> 18
        "NOK" -> 19
        "DKK" -> 20
        "SEK" -> 21
        "AFN" -> 22
        "NZD" -> 23
        "AUD" -> 24
        "GERAMI" -> 25
        "CAD" -> 26
        "ROB" -> 27
        "JPY" -> 28
        "NIM" -> 29
        "CNY" -> 30
        "SEKEE_EMAMI" -> 31
        "TRY" -> 32
        "SEKE_BAHAR" -> 33
        "AED" -> 34
        "ONS" -> 35
        "GBP" -> 36
        "GERAMI_18" -> 37
        "EUR" -> 38
        "MESGHAL" -> 39
        "GERAMI_24" -> 40
        else -> -1 // Default value if title is not found
    }
}

