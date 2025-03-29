@file:OptIn(ExperimentalMaterial3Api::class)

package ir.moodz.sarafkoochooloo.presentation.currency.component

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import ir.moodz.sarafkoochooloo.R
import ir.moodz.sarafkoochooloo.domain.model.CurrencyInfo
import ir.moodz.sarafkoochooloo.presentation.currency.CurrencyAction
import ir.moodz.sarafkoochooloo.presentation.currency.CurrencyState
import ir.moodz.sarafkoochooloo.presentation.util.DigitsNumber
import ir.moodz.sarafkoochooloo.presentation.util.rememberCurrencyVisualTransformation
import ir.moodz.sarafkoochooloo.theme.NerkhbazTheme

@Composable
fun ConvertCurrencyModalBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    state: CurrencyState,
    onAction: (CurrencyAction) -> Unit,
    onDismiss: () -> Unit
) {

    val currencyIds = remember {
        state.currenciesWithToman
            .map { it.info.id }
            .sorted()
    }

    var isStartingCurrencyModalVisible by remember { mutableStateOf(false) }
    val startingCurrencyModalSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    if (isStartingCurrencyModalVisible) {
        SelectCurrencyModal(
            currencies = currencyIds,
            initialCurrency = state.startingCurrencyId,
            sheetState = startingCurrencyModalSheetState,
            onCurrencySelect = {
                onAction(CurrencyAction.OnSelectStartingCurrency(it))
                isStartingCurrencyModalVisible = false
            },
            onDismiss = { isStartingCurrencyModalVisible = false }
        )
    }

    var isDestinationCurrencyModalVisible by remember { mutableStateOf(false) }
    val destinationCurrencyModalSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    if (isDestinationCurrencyModalVisible) {
        SelectCurrencyModal(
            currencies = currencyIds,
            initialCurrency = state.targetCurrencyId,
            sheetState = destinationCurrencyModalSheetState,
            onCurrencySelect = {
                onAction(CurrencyAction.OnSelectDestinationCurrency(it))
                isDestinationCurrencyModalVisible = false
            },
            onDismiss = { isDestinationCurrencyModalVisible = false }
        )
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onDismiss() },
        dragHandle = {}
    ) {
        Scaffold(
            topBar = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    TopAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        title = {
                            Text(
                                text = stringResource(id = R.string.currency_convert),
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = { onDismiss() },
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close"
                                )
                            }
                        }

                    )
                }
            },
            content = { innerPadding ->
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.weight(1f))

                    CurrencyBox(
                        title = stringResource(id = R.string.starting_currency),
                        currentAmount = state.startingCurrencyAmount,
                        selectedCurrency = state.startingCurrencyId,
                        onAmountChange = {
                            onAction(CurrencyAction.OnStartingCurrencyAmountChange(it))
                        },
                        onCurrencyChangeClick = {
                            isStartingCurrencyModalVisible = true
                        }
                    )
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0x33CDF040),
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(12.dp),
                        onClick = {
                            onAction(CurrencyAction.OnSwapConvertingCurrenciesClick)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapVert,
                            contentDescription = "Swap currencies",
                            modifier = Modifier.size(35.dp)
                        )
                    }

                    CurrencyBox(
                        title = stringResource(id = R.string.destination_currency),
                        currentAmount = state.destinationCurrencyAmount,
                        selectedCurrency = state.targetCurrencyId,
                        onAmountChange = {},
                        isReadOnly = true,
                        onCurrencyChangeClick = {
                            isDestinationCurrencyModalVisible = true
                        }
                    )
                    Spacer(modifier = Modifier.weight(10f))
                }
            }
        )
    }
}

@Composable
private fun SelectCurrencyModal(
    currencies: List<Int>,
    initialCurrency: Int,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onCurrencySelect: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedCurrency by remember { mutableIntStateOf(initialCurrency) }
    val itemHeight = 70.dp
    val numberOfDisplayedItems = 3
    val itemHalfHeight = LocalDensity.current.run { itemHeight.toPx() / 2f }
    val scrollState = rememberLazyListState(0)
    var lastSelectedIndex by remember { mutableIntStateOf(0) }
    var itemsState by remember { mutableStateOf(currencies) }

    LaunchedEffect(currencies) {
        var targetIndex = currencies.indexOf(initialCurrency)
        itemsState = currencies
        lastSelectedIndex = targetIndex
        scrollState.scrollToItem(targetIndex)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                state = scrollState,
                modifier = Modifier.height(itemHeight * numberOfDisplayedItems),
                flingBehavior = rememberSnapFlingBehavior(scrollState)
            ) {
                item {
                    Spacer(modifier = Modifier.height(itemHeight))
                }
                itemsIndexed(currencies) { index, currency ->
                    val isSelected = index == lastSelectedIndex
                    val item = itemsState[index % itemsState.size]
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(itemHeight)
                            .onGloballyPositioned { coordinates ->
                                val y = coordinates.positionInParent().y - itemHalfHeight
                                val parentHalfHeight =
                                    (coordinates.parentCoordinates?.size?.height ?: 0) / 2f
                                val isSelected =
                                    (y > parentHalfHeight - itemHalfHeight && y < parentHalfHeight + itemHalfHeight)
                                if (isSelected && lastSelectedIndex != index) {
                                    selectedCurrency = item
                                    lastSelectedIndex = index
                                }
                            }, content = {
                            Text(
                                text = stringResource(
                                    id = CurrencyInfo.fromInt(currency).stringResId
                                ),
                                style = if (isSelected) {
                                    MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                                } else {
                                    MaterialTheme.typography.bodyLarge.copy(
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                                    )
                                },
                                textAlign = TextAlign.Center
                            )
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(itemHeight))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { onCurrencySelect(selectedCurrency) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.background
                ),
                contentPadding = PaddingValues(16.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.select),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun CurrencyBox(
    modifier: Modifier = Modifier,
    title: String,
    selectedCurrency: Int,
    currentAmount: String,
    onAmountChange: (String) -> Unit,
    onCurrencyChangeClick: () -> Unit,
    isReadOnly: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier.weight(2f),
                    value = currentAmount,
                    onValueChange = { amount ->
                        if (!amount.startsWith("0") && amount.isDigitsOnly() && amount.length <= 11) {
                            onAmountChange(amount)
                        }
                    },
                    readOnly = isReadOnly,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        textDirection = TextDirection.Ltr,
                        textAlign = TextAlign.End
                    ),
                    placeholder = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    visualTransformation = rememberCurrencyVisualTransformation()
                )
                Button(
                    modifier = Modifier.weight(1.2f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    shape = RoundedCornerShape(
                        topEnd = 10.dp,
                        bottomEnd = 10.dp
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 12.dp,
                        vertical = 16.dp
                    ),
                    onClick = { onCurrencyChangeClick() }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = stringResource(
                                id = CurrencyInfo.fromInt(selectedCurrency).stringResId
                            ),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "$title change"
                        )
                    }
                }
            }
        }
        Text(
            text = DigitsNumber.numberToWords(number = currentAmount),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun ConvertCurrencyModalBottomSheetPreview() {
    NerkhbazTheme {
        ConvertCurrencyModalBottomSheet(
            onDismiss = {},
            state = CurrencyState(),
            onAction = {}
        )
    }
}