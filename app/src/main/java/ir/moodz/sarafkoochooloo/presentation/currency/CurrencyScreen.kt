@file:OptIn(ExperimentalMaterial3Api::class)

package ir.moodz.sarafkoochooloo.presentation.currency

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ir.moodz.sarafkoochooloo.R
import ir.moodz.sarafkoochooloo.domain.model.currency.Currency
import ir.moodz.sarafkoochooloo.domain.model.currency.CurrencyInfo
import ir.moodz.sarafkoochooloo.presentation.currency.component.ChartModal
import ir.moodz.sarafkoochooloo.presentation.currency.component.ConvertModal
import ir.moodz.sarafkoochooloo.presentation.currency.component.UpdateModal
import ir.moodz.sarafkoochooloo.presentation.util.ObserveAsEvents
import ir.moodz.sarafkoochooloo.presentation.util.toThousandSeparator
import ir.moodz.sarafkoochooloo.theme.Gray_300
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
        when (event) {
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

    val convertCurrencySheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    if (state.isConvertCurrencyModalVisible) {
        ConvertModal(
            onDismiss = { onAction(CurrencyAction.OnToggleConvertCurrencyModal) },
            sheetState = convertCurrencySheetState,
            state = state,
            onAction = onAction
        )
    }

    val currencyChartSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    if (state.isChartModalVisible) {
        ChartModal(
            sheetState = currencyChartSheetState,
            onDismiss = { onAction(CurrencyAction.OnToggleChartModalDismiss) },
            currencies = state.selectedCurrencyDays,
            selectedCurrency = state.selectedDetailCurrency,
            isLoading = state.isLoading
        )
    }

    val openAppUpdateIntent = Intent(
        Intent.ACTION_VIEW,
        state.updateUrl.toUri()
    )
    if (state.isAppNeedToUpdate) {
        UpdateModal(
            onDismiss = { /*Force Update can not be dismissed */ },
            onUpdateClick = {
                context.startActivity(openAppUpdateIntent)
            }
        )
    }

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
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
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "قیمت لحظه ای",
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Text(
                            text = "نرخ ارز، سکه و طلا",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->
            PullToRefreshBox(
                state = state.pullToRefreshState,
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
                    .padding(innerPadding),
                isRefreshing = state.isLoading,
                onRefresh = { onAction(CurrencyAction.OnPullDownRefresh) }
            ) {
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
                            animationSpec = tween(durationMillis = 1000)
                        )
                        ListItem(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable {
                                    onAction(CurrencyAction.OnCurrencyChartClick(currency))
                                },
                            shadowElevation = 12.dp,
                            headlineContent = {
                                Text(
                                    text = stringResource(
                                        R.string.toman,
                                        animatedCurrentPrice.toThousandSeparator()
                                    ),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                            },
                            leadingContent = {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(MaterialTheme.colorScheme.surfaceContainer),
                                    contentAlignment = Alignment.Center,
                                    content = {
                                        if (currency.info.iconResId != null) {
                                            Icon(
                                                painter = painterResource(id = currency.info.iconResId),
                                                contentDescription = null,
                                                tint = LightestGrayColor,
                                                modifier = Modifier
                                                    .padding(
                                                        vertical = 8.dp,
                                                        horizontal = 12.dp
                                                    )
                                                    .size(25.dp)
                                            )
                                        } else {
                                            Icon(
                                                imageVector = Icons.Default.Paid,
                                                contentDescription = null,
                                                tint = LightestGrayColor,
                                                modifier = Modifier
                                                    .padding(
                                                        vertical = 8.dp,
                                                        horizontal = 12.dp
                                                    )
                                                    .size(25.dp)
                                            )
                                        }
                                    }
                                )
                            },
                            trailingContent = {
                                Text(
                                    text = stringResource(id = currency.info.stringResId),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Gray_300
                                )
                            }
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.EndOverlay,
        floatingActionButton = {
            AnimatedVisibility(
                visible = !state.isScrollingDown && state.currencies.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(
                    onClick = { onAction(CurrencyAction.OnToggleConvertCurrencyModal) },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.background,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.size(64.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_exchange),
                        contentDescription = stringResource(id = R.string.currency_convert),
                        modifier = Modifier.size(24.dp)
                    )
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
        modifier = Modifier.padding(16.dp),
        shape = RoundedCornerShape(10.dp),
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
                        updatedDate = 12 to 12,
                    )
                )
            ),
            event = flowOf(),
            onAction = {}
        )
    }
}