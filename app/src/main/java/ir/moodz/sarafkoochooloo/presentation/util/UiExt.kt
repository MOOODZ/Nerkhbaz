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





