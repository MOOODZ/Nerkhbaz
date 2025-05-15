@file:OptIn(ExperimentalMaterial3Api::class)

package ir.moodz.sarafkoochooloo.presentation.currency.component

import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.core.content.res.ResourcesCompat
import ir.moodz.sarafkoochooloo.R
import ir.moodz.sarafkoochooloo.domain.model.currency.Currency
import ir.moodz.sarafkoochooloo.domain.model.currency.CurrencyDetail
import ir.moodz.sarafkoochooloo.domain.model.currency.CurrencyInfo
import ir.moodz.sarafkoochooloo.presentation.currency.util.MockData
import ir.moodz.sarafkoochooloo.presentation.util.toThousandSeparator
import ir.moodz.sarafkoochooloo.theme.ChartGreenDark
import ir.moodz.sarafkoochooloo.theme.ChartGreenLight
import ir.moodz.sarafkoochooloo.theme.NerkhbazTheme

@Composable
fun ChartModal(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismiss: () -> Unit = {},
    currencies: List<CurrencyDetail> = emptyList(),
    selectedCurrency: Currency?,
    isLoading: Boolean = false,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
        dragHandle = {}
    ) {
        Scaffold(
            topBar = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    TopAppBar(
                        title = {
                            if (selectedCurrency != null) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = stringResource(selectedCurrency.info.stringResId),
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        if (selectedCurrency.info.iconResId != null) {
                                            Icon(
                                                painter = painterResource(id = selectedCurrency.info.iconResId),
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                                                modifier = Modifier.size(25.dp)
                                            )
                                        } else {
                                            Icon(
                                                imageVector = Icons.Default.Paid,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                                                modifier = Modifier.size(25.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = stringResource(
                                            R.string.last_price,
                                            selectedCurrency.currentPrice.toThousandSeparator()
                                        ),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
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
            contentWindowInsets = WindowInsets.safeDrawing.exclude(WindowInsets.statusBars),
            content = { paddingValues ->
                if (isLoading || currencies.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        ChartCanvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f)
                                .padding(top = 56.dp)
                                .padding(horizontal = 16.dp),
                            currencies = currencies
                        )
                        Spacer(Modifier.height(24.dp))
                        LazyColumn(
//                            contentPadding = paddingValues,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(3f)
                        ) {
                            item {
                                Text(
                                    text = stringResource(R.string.price_in_last_thirty_days),
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    style = MaterialTheme.typography.titleLarge,
                                    textAlign = TextAlign.Center
                                )
                            }
                            item {
                                HorizontalDivider(
                                    thickness = 3.dp,
                                )
                            }
                            itemsIndexed(
                                items = currencies,
                            ) { index, currency ->


                                ListItem(
                                    modifier = modifier
                                        .padding(vertical = 4.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    colors = ListItemDefaults.colors(
                                        containerColor = MaterialTheme.colorScheme.background
                                    ),
                                    shadowElevation = 12.dp,
                                    headlineContent = {
                                        Text(
                                            text = stringResource(
                                                R.string.toman,
                                                currency.currentPrice.toThousandSeparator()
                                            ),
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )

                                    },
                                    trailingContent = {
                                        Text(
                                            text = currency.dateInWords,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onSecondaryContainer
                                        )
                                    }
                                )
                                if (index < currencies.lastIndex) {
                                    HorizontalDivider()
                                }
                            }
                        }
                    }
                }
            }
        )

    }
}

@Composable
private fun ChartCanvas(
    modifier: Modifier = Modifier,
    currencies: List<CurrencyDetail>
) {
    val graphColor = if (isSystemInDarkTheme()) ChartGreenDark else ChartGreenLight
    val spacing = LocalConfiguration.current.screenHeightDp * 0.15f
    val transparentGraphColor = remember {
        graphColor.copy(alpha = 0.5f)
    }
    val upperValue = remember(currencies) {
        currencies.maxOfOrNull { it.currentPrice }?.toFloat() ?: 0F
    }
    val lowerValue = remember(currencies) {
        currencies.minOfOrNull { it.currentPrice }?.toFloat() ?: 0F
    }
    val density = LocalDensity.current
    val customTypeface = Typeface.create(
        /* family = */ ResourcesCompat.getFont(LocalContext.current, R.font.iransans_bold),
        /* style = */ Typeface.BOLD
    )
    val textColor = MaterialTheme.colorScheme.onBackground.toArgb()

    // TODO : Handle texts with composables with draw text instead of Android TextPaint
    val textPaint = remember(density) {
        Paint().apply {
            color = textColor
            textAlign = Paint.Align.CENTER
            typeface = customTypeface
            textSize = density.run { 12.sp.toPx() }
        }
    }
    Canvas(modifier = modifier) {
        // Draw date labels on x-axis
        val spacePerDay = (size.width - spacing) / currencies.size
        (0 until currencies.size - 0 step 7).forEach { i ->
            val info = currencies[i]
            val month = info.date.first.toString()
            val day = info.date.second.toString()
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    /* text = */ "$month/$day",
                    /* x = */ spacing + i * spacePerDay,
                    /* y = */ size.height - 5,
                    /* paint = */ textPaint
                )
            }
        }
        // Draw price labels on y-axis
        val priceStep = (upperValue - lowerValue) / 3f
        (0..4).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    /* text = */ (lowerValue + priceStep * i).toThousandSeparator(),
                    /* x = */ 30f,
                    /* y = */ size.height - spacing - i * size.height / 3f,
                    /* paint = */ textPaint
                )
            }
        }
        var previousY = 0f
        var previousX = 0f
        val strokePath = Path().apply {
            val height = size.height
            currencies.fastForEachIndexed { index, currency ->
                val nextCurrency =
                    currencies.getOrNull(index + 1) ?: currencies.last()

                val leftRatio =
                    (currency.currentPrice - lowerValue) / (upperValue - lowerValue)
                val rightRatio =
                    (nextCurrency.currentPrice - lowerValue) / (upperValue - lowerValue)

                val x1 = spacing + index * spacePerDay
                val y1 = height - spacing - (leftRatio * height).toFloat()
                val x2 = spacing + (index + 1) * spacePerDay
                val y2 = height - spacing - (rightRatio * height).toFloat()


                if (index == 0) {
                    moveTo(x1, y1)
                }

                previousY = (y1 + y2) / 2f
                previousX = (x1 + x2) / 2f

                quadraticTo(
                    x1 = x1,
                    y1 = y1,
                    x2 = previousX,
                    y2 = previousY
                )
            }
        }

        val fillPath = android.graphics.Path(strokePath.asAndroidPath())
            .asComposePath()
            .apply {
                lineTo(
                    x = previousX,
                    y = size.height - spacing
                )
                lineTo(
                    x = spacing,
                    y = size.height - spacing
                )
                close()
            }
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor,
                    Color.Transparent
                ),
                endY = size.height - spacing
            )
        )
        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
    }
}

@Preview(
//    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showSystemUi = true,
    showBackground = true
)
@Composable
private fun ChartPreview() {
    NerkhbazTheme {

        ChartModal(
            sheetState = SheetState(
                skipPartiallyExpanded = true,
                initialValue = SheetValue.Expanded,
                positionalThreshold = { 300f },
                velocityThreshold = { 0f }
            ),
            selectedCurrency = Currency(
                info = CurrencyInfo.UnitedStatesDollar,
                currentPrice = 1000000000,
                date = Pair(1, 1)
            ),
            currencies = MockData.chart
        )
    }
}