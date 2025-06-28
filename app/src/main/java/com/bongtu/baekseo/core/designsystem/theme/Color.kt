package com.bongtu.baekseo.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Primary
val primaryNormal = Color(0xFF0070F0)
val primaryStrong = Color(0xFF502EFF)
val primaryLight = Color(0xFFDFDCFB)

// Secondary
val secondaryRed = Color(0xFFFF9AA7)

// Gray Scale
val gray900 = Color(0xFF141516)
val gray800 = Color(0xFF1B1C1D)
val gray750 = Color(0xFF262835)
val gray700 = Color(0xFF28292A)
val gray600 = Color(0xFF3B3C3D)
val gray500 = Color(0xFF4E5051)
val gray400 = Color(0xFF717374)
val gray300 = Color(0xFF949697)
val gray200 = Color(0xFFB7B9BA)
val gray100 = Color(0xFFDADBDB)

// Common
val lineNormal = Color(0xFF3A3F4A)
val white = Color(0xFFFFFFFF)
val black = Color(0xFF000000)

@Immutable
data class BongBaekColors(
    val primaryNormal: Color,
    val primaryStrong: Color,
    val primaryLight: Color,
    val secondaryRed: Color,
    val gray900: Color,
    val gray800: Color,
    val gray750: Color,
    val gray700: Color,
    val gray600: Color,
    val gray500: Color,
    val gray400: Color,
    val gray300: Color,
    val gray200: Color,
    val gray100: Color,
    val lineNormal: Color,
    val white: Color,
    val black: Color,
)

val defaultBongBaekColors = BongBaekColors(
    primaryNormal = primaryNormal,
    primaryStrong = primaryStrong,
    primaryLight = primaryLight,
    secondaryRed = secondaryRed,
    gray900 = gray900,
    gray800 = gray800,
    gray750 = gray750,
    gray700 = gray700,
    gray600 = gray600,
    gray500 = gray500,
    gray400 = gray400,
    gray300 = gray300,
    gray200 = gray200,
    gray100 = gray100,
    lineNormal = lineNormal,
    white = white,
    black = black,
)

val LocalBongBaekColors = staticCompositionLocalOf { defaultBongBaekColors }