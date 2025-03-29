package ir.moodz.sarafkoochooloo.domain.util

object CurrencyConverter {
    fun calculate(amount: Long, startingRate: Long, targetRate: Long): Long? {

        val result = (startingRate * amount) / targetRate

        return if (result > 0) result else null
    }
}