package ir.moodz.sarafkoochooloo.presentation.currency.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ir.moodz.sarafkoochooloo.R
import ir.moodz.sarafkoochooloo.theme.NerkhbazTheme

@Composable
fun UpdateModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onUpdateClick: () -> Unit
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Ltr
    ) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.RocketLaunch,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = stringResource(R.string.update_title),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.RocketLaunch,
                            contentDescription = null,
                            modifier = Modifier.graphicsLayer(scaleX = -1f),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(Modifier.height(24.dp))
                    Text(
                        text = stringResource(R.string.update_detail),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = { onUpdateClick() },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.background,
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.update),
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = 14.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun UpdateModalPreview() {
    NerkhbazTheme {
        UpdateModal(
            onUpdateClick = {},
            onDismiss = {}
        )
    }
}