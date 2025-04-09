package ir.moodz.sarafkoochooloo.theme

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    background = Color.Black,
    onBackground = Color.White,
    onPrimaryContainer = Color.Black,
    surfaceContainer = LighterGrayColor,
    tertiaryContainer = LightGrayColor,
    onTertiaryContainer = LightestGrayColor,
    error = ErrorColor
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    background = Color.White,
    onBackground = Color.Black,
    onPrimaryContainer = Color.DarkGray,
    surfaceContainer = LighterGrayColor,
    onSurfaceVariant = LightestGrayColor,
    inverseOnSurface = Gray_300,
    tertiaryContainer = LightGrayColor,
    onTertiaryContainer = LightGrayColor,
    error = ErrorColor
)

@Composable
fun NerkhbazTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val statusBarLight = Color.Black.toArgb()
    val statusBarDark = Color.Black.toArgb()
    val navigationBarLight = Color.Black.toArgb()
    val navigationBarDark = Color.Black.toArgb()
    val activity = LocalActivity.current as ComponentActivity
    DisposableEffect(isDarkMode) {
        activity.enableEdgeToEdge(
            statusBarStyle = if (!isDarkMode) {
                SystemBarStyle.light(
                    statusBarLight,
                    statusBarDark
                )
            } else {
                SystemBarStyle.dark(
                    statusBarDark
                )
            },
            navigationBarStyle = if(!isDarkMode){
                SystemBarStyle.light(
                    navigationBarLight,
                    navigationBarDark
                )
            } else {
                SystemBarStyle.dark(navigationBarDark)
            }
        )

        onDispose { }
    }
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        isDarkMode -> DarkColorScheme
        else -> DarkColorScheme //LightColorScheme
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}