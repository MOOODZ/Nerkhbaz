package ir.moodz.sarafkoochooloo.presentation.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.moodz.sarafkoochooloo.R
import ir.moodz.sarafkoochooloo.domain.model.Currency
import ir.moodz.sarafkoochooloo.domain.model.CurrencyType
import ir.moodz.sarafkoochooloo.presentation.util.convertCurrencyTitleToText
import ir.moodz.sarafkoochooloo.presentation.util.toThousandSeparator
import ir.moodz.sarafkoochooloo.theme.NerkhbazTheme

@Composable
fun CurrencyItem(
    modifier: Modifier = Modifier,
    currency: Currency
) {
    val context = LocalContext.current

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp,
                    horizontal = 12.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box (
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .weight(1f),
            ) {
                Icon(
                    imageVector = Icons.Default.Money,
                    modifier = Modifier.padding(4.dp),
                    contentDescription = null
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.weight(2f)
            ) {
                Text(
                    text = stringResource(id = R.string.toman, currency.currentPrice.toThousandSeparator()),
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = convertCurrencyTitleToText(currency.title).asString(context),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun CurrencyItemPreview() {
    NerkhbazTheme {
        CurrencyItem(
            currency = Currency(
                title = "دلار آمریکا",
                currentPrice = 200000,
                updatedDate = "2333333",
                type = CurrencyType.CURRENCY
            )
        )
    }
}