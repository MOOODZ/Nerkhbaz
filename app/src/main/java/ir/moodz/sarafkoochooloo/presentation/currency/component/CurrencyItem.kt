package ir.moodz.sarafkoochooloo.presentation.currency.component

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.moodz.sarafkoochooloo.R
import ir.moodz.sarafkoochooloo.domain.model.currency.Currency
import ir.moodz.sarafkoochooloo.domain.model.currency.CurrencyInfo
import ir.moodz.sarafkoochooloo.presentation.util.toThousandSeparator
import ir.moodz.sarafkoochooloo.theme.NerkhbazTheme

@Composable
fun CurrencyItem(
    modifier: Modifier = Modifier,
    currency: Currency,
    onClick: () -> Unit
) {
    val animatedCurrentPrice by animateIntAsState(
        targetValue = currency.currentPrice,
        animationSpec = tween(durationMillis = 1000)
    )
    ListItem(
        modifier = modifier
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick()
            },
        colors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background
        ),
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
                            tint = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier
                                .padding(
                                    vertical = 12.dp,
                                    horizontal = 16.dp
                                )
                                .size(25.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Paid,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier
                                .padding(
                                    vertical = 12.dp,
                                    horizontal = 16.dp
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
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    )
}

@Preview
@Composable
private fun CurrencyItemPreview() {
    NerkhbazTheme {
        CurrencyItem(
            currency = Currency(
                info = CurrencyInfo.UnitedStatesDollar,
                currentPrice = 5600000,
                date = Pair(12, 2)
            ),
            onClick = {}
        )
    }
}