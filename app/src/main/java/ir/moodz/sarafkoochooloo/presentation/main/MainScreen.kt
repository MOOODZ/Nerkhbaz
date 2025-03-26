@file:OptIn(ExperimentalMaterial3Api::class)

package ir.moodz.sarafkoochooloo.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.moodz.sarafkoochooloo.R
import ir.moodz.sarafkoochooloo.domain.model.Currency
import ir.moodz.sarafkoochooloo.domain.model.CurrencyType
import ir.moodz.sarafkoochooloo.presentation.main.component.CurrencyItem
import ir.moodz.sarafkoochooloo.theme.NerkhbazTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainRoot(
    viewModel: MainViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun MainScreen(
    state: MainState,
    onAction: (MainAction) -> Unit,
) {
    Scaffold(
        topBar = {

        },
        content = { innerPadding ->
            PullToRefreshBox(
                state = state.pullToRefreshState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                isRefreshing = state.isLoading,
                onRefresh = { onAction(MainAction.OnPullDownRefresh) }
            ) {
                if (state.currencies.isEmpty() && !state.isLoading) {
                    Text(
                        text = stringResource(id = R.string.no_item_found),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyVerticalGrid(
                        state = state.lazyGridState,
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = state.currencies,
                            key = { it.title }
                        ) { currency ->
                            CurrencyItem(currency = currency)
                        }
                    }
                }
            }
        },
        bottomBar = {

        }
    )
}

@Preview
@Composable
private fun Preview() {
    NerkhbazTheme {
        MainScreen(
            state = MainState(
                currencies = listOf(
                    Currency(
                        title = "USD",
                        currentPrice = 2000000,
                        updatedDate = "2333333",
                        type = CurrencyType.CURRENCY
                    )
                )
            ),
            onAction = {}
        )
    }
}