package com.example.liftoff.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LiftoffColorScheme = darkColorScheme(
    primary = LiftoffPrimary,
    background = LiftoffBackground,
    surface = LiftoffSurface,
    surfaceVariant = LiftoffSurfaceVariant,
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun LiftoffTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LiftoffColorScheme,
        content = content
    )
}