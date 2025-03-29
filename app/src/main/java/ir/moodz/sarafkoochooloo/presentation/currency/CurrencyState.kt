@file:OptIn(ExperimentalMaterial3Api::class)

package ir.moodz.sarafkoochooloo.presentation.currency

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import ir.moodz.sarafkoochooloo.domain.model.Currency
import ir.moodz.sarafkoochooloo.domain.model.CurrencyInfo

data class CurrencyState(
    val currencies: List<Currency> = emptyList(),
    val currenciesWithToman: List<Currency> = emptyList(),
    val isLoading: Boolean = false,
    val pullToRefreshState: PullToRefreshState = PullToRefreshState(),
    val lazyListState: LazyListState = LazyListState(),
    val isScrollingDown: Boolean = false,
    val isConvertCurrencyModalVisible: Boolean = false,
    val startingCurrencyId: Int = CurrencyInfo.UnitedStatesDollar.id,
    val targetCurrencyId: Int = CurrencyInfo.IranToman.id,
    val startingCurrencyAmount: String = "",
    val destinationCurrencyAmount: String = ""
)