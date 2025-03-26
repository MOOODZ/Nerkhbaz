package ir.moodz.sarafkoochooloo.presentation.util

import android.icu.text.DecimalFormat
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.core.text.isDigitsOnly
import ir.moodz.sarafkoochooloo.presentation.util.DigitsNumber.persianToEnglish


private object CurrencyVisualTransformation: VisualTransformation {
    val numberFormatter = DecimalFormat("#,###")
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text.trim()
        if (originalText.isEmpty()) {
            return TransformedText(text, OffsetMapping.Identity)
        }
        if (originalText.isDigitsOnly().not()) {
            return TransformedText(text, OffsetMapping.Identity)
        }
        val formattedText = numberFormatter.format(originalText.toLong()).persianToEnglish()
        return TransformedText(
            AnnotatedString(formattedText),
            CurrencyOffsetMapping(originalText, formattedText)
        )
    }
}

@Composable
fun rememberCurrencyVisualTransformation(): VisualTransformation {
    return  CurrencyVisualTransformation
}


private class CurrencyOffsetMapping(originalText: String, formattedText: String) : OffsetMapping {
    private val originalLength: Int = originalText.length
    private val indexes = findDigitIndexes(originalText, formattedText)

    private fun findDigitIndexes(firstString: String, secondString: String): List<Int> {
        val digitIndexes = mutableListOf<Int>()
        var currentIndex = 0
        for (digit in firstString) {
            val index = secondString.indexOf(digit, currentIndex)
            if (index != -1) {
                digitIndexes.add(index)
                currentIndex = index + 1
            } else {
                return emptyList()
            }
        }
        return digitIndexes
    }

    override fun originalToTransformed(offset: Int): Int {
        if (offset >= originalLength) {
            return indexes.last() + 1
        }
        return indexes[offset]
    }

    override fun transformedToOriginal(offset: Int): Int {
        return indexes.indexOfFirst { it >= offset }.takeIf { it != -1 } ?: originalLength
    }
}