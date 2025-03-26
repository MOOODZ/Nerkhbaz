package ir.moodz.sarafkoochooloo.presentation.util

import android.icu.text.DecimalFormat
import android.icu.text.DecimalFormatSymbols

object DigitsNumber {

    fun String.numberToCurrency(): String {
        if (this.isEmpty() || this == "") {
            return ""
        }
        val convertCurrency = convertCurrencyToNumber(this).persianToEnglish()
        println("Inpuet number englishNumber $convertCurrency")
        val symbols = DecimalFormatSymbols()
        symbols.groupingSeparator = ','
        val decimalFormat = DecimalFormat("############")
        decimalFormat.decimalFormatSymbols = symbols
        decimalFormat.groupingSize = 3
        decimalFormat.isGroupingUsed = true
        return decimalFormat.format(convertCurrency.toLong())
    }

    fun String.persianToEnglish(): String {
        var result = ""
        var en = '0'
        for (ch in this) {
            en = ch
            when (ch) {
                '۰' -> en = '0'
                '۱' -> en = '1'
                '۲' -> en = '2'
                '۳' -> en = '3'
                '۴' -> en = '4'
                '۵' -> en = '5'
                '۶' -> en = '6'
                '۷' -> en = '7'
                '۸' -> en = '8'
                '۹' -> en = '9'
            }
            result = "${result}$en"
        }
        return result
    }

    private fun convertCurrencyToNumber(amount: String): String = amount.replace(",", "")


    fun String.toPersianNumber(): String {
        var result = ""
        var en = '0'
        for (ch in this) {
            en = ch
            when (ch) {
                '0' -> en = '۰'
                '1' -> en = '۱'
                '2' -> en = '۲'
                '3' -> en = '۳'
                '4' -> en = '۴'
                '5' -> en = '۵'
                '6' -> en = '۶'
                '7' -> en = '۷'
                '8' -> en = '۸'
                '9' -> en = '۹'
            }
            result = "${result}$en"
        }
        return result
    }

}