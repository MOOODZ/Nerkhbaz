@file:OptIn(ExperimentalMaterial3Api::class)

package ir.moodz.sarafkoochooloo.presentation.currency

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import ir.moodz.sarafkoochooloo.domain.model.Currency

data class CurrencyState(
    val currencies: List<Currency> = emptyList(),
    val isLoading: Boolean = false,
    val pullToRefreshState: PullToRefreshState = PullToRefreshState(),
    val lazyListState: LazyListState = LazyListState(),
    val isScrollingDown: Boolean = false,
    val isConvertCurrencyModalVisible: Boolean = false,
    val selectedStartingCurrency: String? = null,
    val selectedDestinationCurrency: String? = null,
    val startingCurrencyAmount: String = "",
    val destinationCurrencyAmount: String = ""
)