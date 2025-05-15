package ir.moodz.sarafkoochooloo.data.mapper.util

object DateMapper{
    fun mapPersianDateToPair(date: String): Pair<Int, Int> {
        val persianMonths = mapOf(
            "فروردین" to 1,
            "اردیبهشت" to 2,
            "خرداد" to 3,
            "تیر" to 4,
            "مرداد" to 5,
            "شهریور" to 6,
            "مهر" to 7,
            "آبان" to 8,
            "آذر" to 9,
            "دی" to 10,
            "بهمن" to 11,
            "اسفند" to 12
        )

        val parts = date.split("/")
        if (parts.size == 3) {
            val day = parts[0].toIntOrNull() ?: 1
            val month = persianMonths[parts[1]] ?: 1
            return month to day
        }

        return 1 to 1
    }

    fun mapPersianDateToNumberDate(date: String) : String{
        val parts = date.split("/")
        if (parts.size == 3) {
            val year = parts[0]
            val month = when (parts[1]) {
                "فروردین" -> "01"
                "اردیبهشت" -> "02"
                "خرداد" -> "03"
                "تیر" -> "04"
                "مرداد" -> "05"
                "شهریور" -> "06"
                "مهر" -> "07"
                "آبان" -> "08"
                "آذر" -> "09"
                "دی" -> "10"
                "بهمن" -> "11"
                "اسفند" -> "12"
                else -> "00"
            }
            val day = parts[2].padEnd(2, '0')
            return "$day/$month/$year"
        }
        return ""
    }

    fun mapPairToPersianDate(monthDay: Pair<Int, Int>): String {
        val persianMonths = mapOf(
            1 to "فروردین",
            2 to "اردیبهشت",
            3 to "خرداد",
            4 to "تیر",
            5 to "مرداد",
            6 to "شهریور",
            7 to "مهر",
            8 to "آبان",
            9 to "آذر",
            10 to "دی",
            11 to "بهمن",
            12 to "اسفند"
        )

        val month = persianMonths[monthDay.first] ?: "فروردین"
        return "${monthDay.second}/$month/${monthDay.first}"
    }
}