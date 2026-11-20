package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = WinBlueSecondary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF004578),
    onPrimaryContainer = Color(0xFFD0E4FF),
    secondary = WinTealTertiary,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF004F55),
    onSecondaryContainer = Color(0xFF70F3FF),
    background = WinDarkBackground,
    surface = WinDarkSurface,
    surfaceVariant = WinDarkSurfaceVariant,
    onBackground = Color(0xFFF0F4F8),
    onSurface = Color(0xFFF0F4F8),
    onSurfaceVariant = Color(0xFFC4D1E5)
)

private val LightColorScheme = lightColorScheme(
    primary = WinBluePrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD4E7FF),
    onPrimaryContainer = Color(0xFF001C38),
    secondary = WinBlueSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD1ECFF),
    onSecondaryContainer = Color(0xFF001F38),
    background = WinLightBackground,
    surface = WinLightSurface,
    surfaceVariant = WinLightSurfaceVariant,
    onBackground = Color(0xFF101928),
    onSurface = Color(0xFF101928),
    onSurfaceVariant = Color(0xFF475467)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep Microsoft branding cohesive by default
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
