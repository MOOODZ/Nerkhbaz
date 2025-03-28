@file:OptIn(ExperimentalMaterial3Api::class)

package ir.moodz.sarafkoochooloo.presentation.currency

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.moodz.sarafkoochooloo.R
import ir.moodz.sarafkoochooloo.domain.model.Currency
import ir.moodz.sarafkoochooloo.domain.model.CurrencyInfo
import ir.moodz.sarafkoochooloo.presentation.currency.component.ConvertCurrencyModalBottomSheet
import ir.moodz.sarafkoochooloo.presentation.util.ObserveAsEvents
import ir.moodz.sarafkoochooloo.presentation.util.toThousandSeparator
import ir.moodz.sarafkoochooloo.theme.LightestGrayColor
import ir.moodz.sarafkoochooloo.theme.NerkhbazTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CurrencyRoot(
    viewModel: CurrencyViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CurrencyScreen(
        state = state,
        event = viewModel.events,
        onAction = viewModel::onAction
    )
}

@Composable
fun CurrencyScreen(
    state: CurrencyState,
    event: Flow<CurrencyEvent>,
    onAction: (CurrencyAction) -> Unit,
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    ObserveAsEvents(event) { event ->
        when(event){
            is CurrencyEvent.Error -> {
                scope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context),
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    if (state.isConvertCurrencyModalVisible){
        ConvertCurrencyModalBottomSheet(
            onDismiss = { onAction(CurrencyAction.OnToggleConvertCurrencyModal) },
            sheetState = sheetState,
            state = state,
            onAction = onAction
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) { snackBarData ->
                NerkhbazSnackbarError(
                    message = snackBarData.visuals.message
                )
            }
        },
        content = { innerPadding ->
            PullToRefreshBox(
                state = state.pullToRefreshState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                isRefreshing = state.isLoading,
                onRefresh = { onAction(CurrencyAction.OnPullDownRefresh) }
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
                    LazyColumn(
                        state = state.lazyListState,
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = state.currencies,
                            key = { it.info.title }
                        ) { currency ->
                            val animatedCurrentPrice by animateIntAsState(
                                targetValue = currency.currentPrice,
                                animationSpec = tween(durationMillis = 800)
                            )
                            ListItem(
                                modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                                shadowElevation = 16.dp,
                                headlineContent = {
                                    Text(
                                        text = stringResource(id = currency.info.stringResId),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                },
                                leadingContent = {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(MaterialTheme.colorScheme.surfaceContainer),
                                        contentAlignment = Alignment.Center,
                                        content = {
                                            Icon(
                                                imageVector = Icons.Default.Paid,
                                                contentDescription = null,
                                                tint = LightestGrayColor,
                                                modifier = Modifier.padding(
                                                    vertical = 8.dp,
                                                    horizontal = 12.dp
                                                )
                                            )
                                        }
                                    )
                                },
                                trailingContent = {
                                    Text(
                                        text = stringResource(
                                            R.string.toman,
                                            animatedCurrentPrice.toThousandSeparator()
                                        ),
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            )
                        }
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            AnimatedVisibility(
                visible = !state.isScrollingDown && state.currencies.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Button (
                    onClick = { onAction(CurrencyAction.OnToggleConvertCurrencyModal) },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.background,
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.currency_convert),
                            style = MaterialTheme.typography.titleLarge
                        )
                        Icon(
                            imageVector = Icons.Default.CurrencyExchange,
                            contentDescription = stringResource(id = R.string.currency_convert),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun NerkhbazSnackbarError (
    modifier: Modifier = Modifier,
    message: String
) {
    Snackbar(
        shape = RoundedCornerShape(16.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun Preview() {
    NerkhbazTheme {
        CurrencyScreen(
            state = CurrencyState(
                currencies = listOf(
                    Currency(
                        info = CurrencyInfo.UnitedStatesDollar,
                        currentPrice = 2000000,
                        updatedDate = "1403/02/22",
                    )
                )
            ),
            event = flowOf(),
            onAction = {}
        )
    }
}