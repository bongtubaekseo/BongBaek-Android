package com.bongtu.baekseo.core.designsystem.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

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
    content: @Composable () -> Unit,
) {
    ProvideBongBaekColorsAndTypography(
        colors = defaultBongBaekColors,
        typography = defaultBongBaekTypography,
    ) {
        val view = LocalView.current
        if (!view.isInEditMode) {
            SideEffect {
                (view.context as Activity).window.run {
                    WindowCompat.getInsetsController(
                        this,
                        view
                    ).isAppearanceLightStatusBars = false
                }
            }
        }
        MaterialTheme(
            content = content,
        )
    }
}

