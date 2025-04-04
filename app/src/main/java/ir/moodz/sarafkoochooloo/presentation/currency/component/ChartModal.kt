@file:OptIn(ExperimentalMaterial3Api::class)

package ir.moodz.sarafkoochooloo.presentation.currency.component

import android.content.res.Configuration
import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.core.content.res.ResourcesCompat
import ir.moodz.sarafkoochooloo.R
import ir.moodz.sarafkoochooloo.domain.model.Currency
import ir.moodz.sarafkoochooloo.domain.model.CurrencyInfo
import ir.moodz.sarafkoochooloo.presentation.currency.util.MockData
import ir.moodz.sarafkoochooloo.presentation.util.toThousandSeparator
import ir.moodz.sarafkoochooloo.theme.NerkhbazTheme

@Composable
fun ChartModal(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismiss: () -> Unit = {},
    currencies: List<Currency> = emptyList(),
    selectedCurrency: Currency?,
    isLoading: Boolean = false,
    graphColor: Color = Color.Green
) {
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

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        dragHandle = {}
    ) {
        Scaffold(
            topBar = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    CenterAlignedTopAppBar(
                        title = {
                            if (selectedCurrency != null) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = stringResource(selectedCurrency.info.stringResId),
                                        style = MaterialTheme.typography.titleLarge
                                    )
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
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    if (isLoading || currencies.isEmpty()) {
                        CircularProgressIndicator()
                    } else {
                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(fraction = 0.7f)
                                .padding(horizontal = 16.dp)
                        ) {
                            // Draw date labels on x-axis
                            val spacePerDay = (size.width - spacing) / currencies.size
                            (0 until currencies.size - 0 step 7).forEach { i ->
                                val info = currencies[i]
                                val month = info.updatedDate.first.toString()
                                val day = info.updatedDate.second.toString()
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
                                        /* x = */ 15f,
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
                }
            }
//            bottomBar = {
//                Button(
//                    onClick = { onDismiss() },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = MaterialTheme.colorScheme.primary,
//                        contentColor = MaterialTheme.colorScheme.background
//                    ),
//                    contentPadding = PaddingValues(16.dp),
//                    shape = RoundedCornerShape(10.dp)
//                ) {
//                    Text(
//                        text = stringResource(id = R.string.back),
//                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
//                        textAlign = TextAlign.Center
//                    )
//                }
//            }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ChartPreview() {
    NerkhbazTheme {

        ChartModal(
            sheetState = SheetState(
                skipPartiallyExpanded = true,
                initialValue = SheetValue.Expanded,
                density = LocalDensity.current
            ),
            selectedCurrency = Currency(
                info = CurrencyInfo.UnitedStatesDollar,
                currentPrice = 1000000000,
                updatedDate = Pair(1, 1)
            ),
            currencies = MockData.chart
        )
    }
}