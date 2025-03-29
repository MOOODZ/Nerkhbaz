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

    fun numberToWords(number: String): String {

        if (number.isEmpty()) return ""

        val units = arrayOf(
            "", "یک", "دو", "سه", "چهار", "پنج", "شش", "هفت", "هشت", "نه", "ده",
            "یازده", "دوازده", "سیزده", "چهارده", "پانزده", "شانزده", "هفده", "هجده", "نوزده"
        )

        val tens = arrayOf(
            "", "", "بیست", "سی", "چهل", "پنجاه", "شصت", "هفتاد", "هشتاد", "نود"
        )

        val hundreds = arrayOf(
            "", "صد", "دویست", "سیصد", "چهارصد", "پانصد", "ششصد", "هفتصد", "هشتصد", "نهصد"
        )

        val thousands = arrayOf("", "هزار", "میلیون", "میلیارد", "تریلیون" , "کوادریلیون", "کوینتیلیون" , "سیکستیلون")

        fun convertChunk(input: Int): String {
            val parts = mutableListOf<String>()

            var num = input

            if (num >= 100) {
                parts.add(hundreds[num / 100])
                num %= 100
            }

            if (num >= 20) {
                parts.add(tens[num / 10])
                num %= 10
            }

            if (num in 1..19) {
                parts.add(units[num])
            }

            return parts.joinToString(" و ")
        }

        var num = number.toLong()// / 10
        var chunkIndex = 0
        val words = mutableListOf<String>()

        while (num > 0) {
            val chunk = (num % 1000).toInt()
            if (chunk > 0) {
                val chunkWords = convertChunk(chunk)
                words.add(0, if (chunkIndex > 0) "$chunkWords ${thousands[chunkIndex]}" else chunkWords)
            }
            num /= 1000
            chunkIndex++
        }

        return words.joinToString(" و ")
    }

}