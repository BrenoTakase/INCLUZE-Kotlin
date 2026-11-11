package com.aether.incluze.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = IncluzeBlue,
    onPrimary = Color.White,
    primaryContainer = IncluzeLightBlue,
    secondary = IncluzeOrange,
    background = SurfaceGray,
    surface = Color.White,
    onBackground = TextDark,
    onSurface = TextDark,
    error = Color(0xFFE74C3C)
)

@Composable
fun IncluzeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}
