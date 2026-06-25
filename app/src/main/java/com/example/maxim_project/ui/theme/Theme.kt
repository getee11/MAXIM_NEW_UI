package com.example.maxim_project.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val MaximColorScheme = lightColorScheme(
    primary = MaximYellow,
    onPrimary = TextPrimary,
    primaryContainer = YellowLight,
    onPrimaryContainer = MaximDarkGold,
    secondary = MaximGold,
    onSecondary = TextPrimary,
    secondaryContainer = GoldLight,
    onSecondaryContainer = MaximDarkGold,
    tertiary = Blue,
    onTertiary = Canvas,
    tertiaryContainer = BlueLight,
    onTertiaryContainer = Blue,
    error = Error,
    onError = Canvas,
    errorContainer = ErrorLight,
    onErrorContainer = Error,
    background = Canvas,
    onBackground = TextPrimary,
    surface = Canvas,
    onSurface = TextPrimary,
    surfaceVariant = Surface,
    onSurfaceVariant = TextBody,
    outline = Hairline,
    outlineVariant = TextDim,
    inverseSurface = TextPrimary,
    inverseOnSurface = Canvas,
)

@Composable
fun MaximTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Canvas.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = MaximColorScheme,
        typography = MaximTypography,
        content = content
    )
}