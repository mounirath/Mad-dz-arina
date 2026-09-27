package com.agon.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryDZ,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB8F0CB),
    onPrimaryContainer = Color(0xFF00210F),
    secondary = Color(0xFF506352),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD3E8D2),
    onSecondaryContainer = Color(0xFF0E1F10),
    tertiary = AccentGold,
    onTertiary = Color(0xFF3A2F00),
    tertiaryContainer = Color(0xFFFFE08D),
    error = ErrorRed,
    background = BackgroundLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondaryLight,
    outline = Color(0xFF72796F),
    outlineVariant = Color(0xFFC2C8BC)
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryLight,
    onPrimary = Color(0xFF00391D),
    primaryContainer = PrimaryDark,
    onPrimaryContainer = Color(0xFFB8F0CB),
    secondary = Color(0xFFB7CCB6),
    onSecondary = Color(0xFF233425),
    secondaryContainer = Color(0xFF394B3B),
    onSecondaryContainer = Color(0xFFD3E8D2),
    tertiary = Color(0xFFDBC66E),
    onTertiary = Color(0xFF3A2F00),
    tertiaryContainer = Color(0xFF534500),
    error = Color(0xFFFFB4AB),
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,
    outline = Color(0xFF8C9388),
    outlineVariant = Color(0xFF424940)
)

@Composable
fun AgonAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(colorScheme = colorScheme, content = content)
}
