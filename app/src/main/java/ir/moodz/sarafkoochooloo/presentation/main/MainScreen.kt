package ir.moodz.sarafkoochooloo.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.moodz.sarafkoochooloo.domain.model.Currency
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
    if (state.currencies.isEmpty()){
        Text(
            text = "List is empty",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxSize()
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            items(state.currencies) { currency ->
                ListItem(
                    headlineContent = {
                        Text(
                            text = currency.title,
                            color = Color.Black
                        )
                    },
                    supportingContent = {
                        Text(
                            text = currency.currentPrice,
                            color = Color.Black
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NerkhbazTheme {
        MainScreen(
            state = MainState(
                currencies = listOf(
                    Currency(
                        id = 2,
                        title = "USD",
                        currentPrice = "2131233",
                        minPrice = "23333",
                        maxPrice = "23333",
                        updatedDate = "2333333"
                    )
                )
            ),
            onAction = {}
        )
    }
}