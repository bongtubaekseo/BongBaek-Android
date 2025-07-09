package com.bongtu.baekseo.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme.colors

// Primary
val primaryNormal = Color(0xFF6E7FFF)
val primaryStrong = Color(0xFF502EFF)
val primaryLight = Color(0xFFDFDCFB)
val primaryGradient = Color(0xFF807FFF)
val primaryBackground = Color(0x1A6E7FFF)

// Secondary
val secondaryRed = Color(0xFFFF9AA7)

// Gray Scale
val gray900 = Color(0xFF141516)
val gray850 = Color(0xFF171922)
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
val transparent = Color(0x00000000)
val kakaoYellow = Color(0xFFFEE500)

// Gradient
val gradientSlider = Color(0xFF8B5CF6)
val gradientEnvelopeStart = Color(0xE6A6BEF3)
val gradientEnvelopeEnd = Color(0xE6D3D9FF)
val gradientExpenseStart = Color(0xFF4E62FF)
val gradientExpenseEnd = Color(0xFF502EFF)

// Page Indicator
val pageIndicator = Color(0x4DFFFFFF)

@Immutable
data class BongBaekColors(
    val primaryNormal: Color,
    val primaryStrong: Color,
    val primaryLight: Color,
    val primaryGradient: Color,
    val primaryBackground: Color,
    val secondaryRed: Color,
    val gray900: Color,
    val gray850: Color,
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
    val transparent: Color,
    val kakaoYellow: Color,
    val gradientSlider: Color,
    val gradientEnvelopeStart: Color,
    val gradientEnvelopeEnd: Color,
    val gradientExpenseStart: Color,
    val gradientExpenseEnd: Color,
    val pageIndicator: Color,
)

val defaultBongBaekColors = BongBaekColors(
    primaryNormal = primaryNormal,
    primaryStrong = primaryStrong,
    primaryLight = primaryLight,
    primaryGradient = primaryGradient,
    primaryBackground = primaryBackground,
    secondaryRed = secondaryRed,
    gray900 = gray900,
    gray850 = gray850,
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
    transparent = transparent,
    kakaoYellow = kakaoYellow,
    gradientSlider = gradientSlider,
    gradientEnvelopeStart = gradientEnvelopeStart,
    gradientEnvelopeEnd = gradientEnvelopeEnd,
    gradientExpenseStart = gradientExpenseStart,
    gradientExpenseEnd = gradientExpenseEnd,
    pageIndicator = pageIndicator,
)

val LocalBongBaekColors = staticCompositionLocalOf { defaultBongBaekColors }

@Preview(showBackground = true)
@Composable
private fun BongBaekColorsPreview() {
    BongBaekTheme {
        Column {
            listOf(
                colors.primaryNormal,
                colors.primaryStrong,
                colors.primaryLight,
                colors.primaryGradient,
                colors.primaryBackground,
                colors.secondaryRed,
                colors.gray900,
                colors.gray850,
                colors.gray800,
                colors.gray750,
                colors.gray700,
                colors.gray600,
                colors.gray500,
                colors.gray400,
                colors.gray300,
                colors.gray200,
                colors.gray100,
                colors.lineNormal,
                colors.white,
                colors.black,
                colors.transparent,
                colors.kakaoYellow,
                colors.gradientSlider,
                colors.gradientEnvelopeStart,
                colors.gradientEnvelopeEnd,
                colors.gradientExpenseStart,
                colors.gradientExpenseEnd,
                colors.pageIndicator,
            ).forEach { color ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(color)
                            .padding(end = 8.dp),
                    )
                }
            }
        }
    }
}
