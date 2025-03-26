@file:OptIn(ExperimentalMaterial3Api::class)

package ir.moodz.sarafkoochooloo.presentation.main

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import ir.moodz.sarafkoochooloo.domain.model.Currency

data class MainState(
    val currencies: List<Currency> = emptyList(),
    val isLoading: Boolean = false,
    val pullToRefreshState: PullToRefreshState = PullToRefreshState(),
    val lazyGridState: LazyGridState = LazyGridState()
)