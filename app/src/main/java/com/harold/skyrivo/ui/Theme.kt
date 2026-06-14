package com.harold.skyrivo.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SkyRivoColors = darkColorScheme(
    primary = Color(0xFF45C7FF),
    onPrimary = Color(0xFF001F2D),
    secondary = Color(0xFF8BD8FF),
    background = Color(0xFF07111F),
    surface = Color(0xFF0D1B2D),
    surfaceVariant = Color(0xFF16263A),
    onBackground = Color(0xFFEAF6FF),
    onSurface = Color(0xFFEAF6FF),
    onSurfaceVariant = Color(0xFFB8C7D9),
    error = Color(0xFFFFB4AB)
)

@Composable
fun SkyRivoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SkyRivoColors,
        typography = MaterialTheme.typography,
        content = content
    )
}
