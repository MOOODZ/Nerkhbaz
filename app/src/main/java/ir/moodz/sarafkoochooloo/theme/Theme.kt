package ir.moodz.sarafkoochooloo.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimaryColor,
    background = Color.Black,
    onBackground = Color.White,
    onPrimaryContainer = Color.Black,
    surfaceContainer = LighterGrayColor,
    tertiaryContainer = LightGrayColor,
    onSecondaryContainer = Gray_300_Dark,
    onTertiaryContainer = LightestGrayColor,
    error = ErrorColor
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimaryColor,
    background = Color.White,
    onBackground = Color.Black,
    onPrimaryContainer = Color.Black,
    surfaceContainer = Color(0xFFF5F5F5),         // light surface for cards/lists
    onSurfaceVariant = Color(0xFF666666),
    onSecondaryContainer = Gray_300_Light,
    tertiaryContainer = Color(0xFFE0E0E0),         // soft gray for containers
    onTertiaryContainer = Color(0xFF333333),       // strong text color on gray
    error = ErrorColor
)

@Composable
fun NerkhbazTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        isDarkMode -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}