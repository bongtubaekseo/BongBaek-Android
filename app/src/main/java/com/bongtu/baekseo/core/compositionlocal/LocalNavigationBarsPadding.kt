package com.bongtu.baekseo.core.compositionlocal

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.union
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalBottomNavigationBarsPadding = staticCompositionLocalOf {
    PaddingValues()
}

val WindowInsets.Companion.safeDrawingWithBottomNavBar: WindowInsets
    @Composable
    get() = WindowInsets.safeDrawing.union(
        WindowInsets(
            bottom = LocalBottomNavigationBarsPadding.current.calculateBottomPadding(),
        ),
    )
