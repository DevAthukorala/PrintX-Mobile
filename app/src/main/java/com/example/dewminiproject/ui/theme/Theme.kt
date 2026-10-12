package com.example.dewminiproject.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PressOrange,
    secondary = MediumPurple,
    tertiary = FoilGold,
    background = DeepNavy,
    surface = DeepNavy,
    onPrimary = White,
    onSecondary = White,
    onTertiary = DeepNavy,
    onBackground = White,
    onSurface = White,
    error = ErrorRed,
    onError = White
)

private val LightColorScheme = lightColorScheme(
    primary = PressOrange,
    onPrimary = White,
    primaryContainer = OrangeTint,
    onPrimaryContainer = PressOrange,
    secondary = MediumPurple,
    onSecondary = White,
    secondaryContainer = LavenderTint,
    onSecondaryContainer = MediumPurple,
    tertiary = FoilGold,
    onTertiary = DeepNavy,
    tertiaryContainer = FoilGold.copy(alpha = 0.1f),
    onTertiaryContainer = DeepNavy,
    background = White,
    onBackground = DeepNavy,
    surface = White,
    onSurface = DeepNavy,
    surfaceVariant = OrangeTint,
    onSurfaceVariant = MutedPlum,
    outline = MutedPlum.copy(alpha = 0.12f),
    outlineVariant = MutedPlum.copy(alpha = 0.08f),
    error = ErrorRed,
    onError = White
)

@Composable
fun PrintXpressTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
