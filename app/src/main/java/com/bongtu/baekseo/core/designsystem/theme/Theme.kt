package com.bongtu.baekseo.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object BongBaekTheme {
    val colors: BongBaekColors
        @Composable
        @ReadOnlyComposable
        get() = LocalBongBaekColors.current

    val typography: BongBaekTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalBongBaekTypography.current
}

@Composable
fun ProvideBongBaekColorsAndTypography(
    colors: BongBaekColors,
    typography: BongBaekTypography,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalBongBaekColors provides colors,
        LocalBongBaekTypography provides typography,
        content = content,
    )
}

@Composable
fun BongBaekTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (isDarkTheme) darkBongBaekColors else lightBongBaekColors

    ProvideBongBaekColorsAndTypography(
        colors = colors,
        typography = defaultBongBaekTypography,
    ) {
        MaterialTheme(
            content = content,
        )
    }
}
