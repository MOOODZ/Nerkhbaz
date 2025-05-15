package ir.moodz.sarafkoochooloo.presentation.currency

import androidx.compose.foundation.lazy.LazyListState
import ir.moodz.sarafkoochooloo.domain.model.currency.Currency
import ir.moodz.sarafkoochooloo.domain.model.currency.CurrencyDetail
import ir.moodz.sarafkoochooloo.domain.model.currency.CurrencyInfo

data class CurrencyState(
    val currencies: List<Currency> = emptyList(),
    val currenciesWithToman: List<Currency> = emptyList(),
    val currencyIds: List<Int> = emptyList(),
    val isLoading: Boolean = false,
    val lazyListState: LazyListState = LazyListState(),
    val isScrollingDown: Boolean = false,
    val isConvertCurrencyModalVisible: Boolean = false,
    val startingCurrencyId: Int = CurrencyInfo.UnitedStatesDollar.id,
    val targetCurrencyId: Int = CurrencyInfo.IranToman.id,
    val startingCurrencyAmount: String = "",
    val destinationCurrencyAmount: String = "",
    val isStartingCurrencyModalVisible: Boolean = false,
    val isDestinationCurrencyModalVisible: Boolean = false,
    val selectedCurrencyDays: List<CurrencyDetail> = emptyList(),
    val isChartLoading: Boolean = false,
    val isChartModalVisible: Boolean = false,
    val selectedDetailCurrency: Currency? = null,
    val isAppNeedToUpdate: Boolean = false,
    val updateUrl: String = "",
    val isSourceModalVisible: Boolean = false,
    )