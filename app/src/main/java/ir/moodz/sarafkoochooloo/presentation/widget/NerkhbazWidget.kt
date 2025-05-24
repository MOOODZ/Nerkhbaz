package ir.moodz.sarafkoochooloo.presentation.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.width
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.glance.unit.FixedColorProvider
import ir.moodz.sarafkoochooloo.domain.model.currency.Currency
import ir.moodz.sarafkoochooloo.domain.repository.CurrenciesRepository
import ir.moodz.sarafkoochooloo.presentation.util.toThousandSeparator
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NerkhbazWidget() : GlanceAppWidget() , KoinComponent {

    val repository : CurrenciesRepository by inject()

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        repository.getCurrencies().collectLatest { currencies ->
            provideContent {
                NerkhbazWidgetContent(
                    modifier = GlanceModifier.fillMaxSize(),
                    currencies = emptyList()
                )
            }
        }
    }

}

class RefreshActionCallback() : ActionCallback , KoinComponent {

    val repository : CurrenciesRepository by inject()

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context,glanceId) {
            repository.fetchCurrencies()
        }
    }
}


@Composable
private fun NerkhbazWidgetContent(
    modifier: GlanceModifier = GlanceModifier,
    currencies: List<Currency>
) {
    Scaffold (
        modifier = modifier,
        titleBar = {
            Row(
                modifier = GlanceModifier.fillMaxWidth()
            ) {
                Text(
                    text = "Nerkhbaz"//stringResource(R.string.precise)
                )
                Spacer(GlanceModifier.width(4.dp))
                Button(
                    onClick = actionRunCallback(RefreshActionCallback::class.java),
                    text = "Refresh"
                )
            }
        },
        content = {
            LazyColumn(
                modifier = GlanceModifier.fillMaxSize()
            ) {
                items(currencies) { currency ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = currency.info.title,
                            style = TextStyle(
                                color = ColorProvider(Color.Black)
                            )
                        )
                        Spacer(GlanceModifier.width(4.dp))
                        Text(
                            text = currency.currentPrice.toThousandSeparator(),
                            style = TextStyle(
                                color = ColorProvider(Color.Black)
                            )
                        )
                    }
                }
            }
        }
    )

}