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

// Global
val white = Color(0xFFFFFFFF)
val black = Color(0xFF000000)
val charcoal = Color(0xFF3A3F4A)

// Brand
val primaryNormal = Color(0xFF6E7FFF)
val primaryStrong = Color(0xFF502EFF)
val primaryLight = Color(0xFFDFDCFB)
val primaryBackground16 = Color(0x296E7FFF)
val primaryBackground10 = Color(0x1A6E7FFF)
val secondaryNormal = Color(0xFFFF9AA7)
val secondaryStrong = Color(0xFFEA6D7D)

// GrayScale(Light)
val light100 = Color(0xFFF5F7FA)
val light150 = Color(0xFFF0F2F5)
val light200 = Color(0xFFEBEDF0)
val light300 = Color(0xFFDCDEE1)
val light400 = Color(0xFFC4C5C8)
val light500 = Color(0xFFABACAF)
val light600 = Color(0xFF939496)
val light700 = Color(0xFF494A4B)
val light800 = Color(0xFF313132)
val light900 = Color(0xFF181819)

// GrayScale(Dark)
val dark100 = Color(0xFFDADBDB)
val dark200 = Color(0xFFB7B9BA)
val dark300 = Color(0xFF949697)
val dark400 = Color(0xFF717374)
val dark500 = Color(0xFF4E5051)
val dark600 = Color(0xFF3B3C3D)
val dark700 = Color(0xFF28292A)
val dark750 = Color(0xFF262835)
val dark800 = Color(0xFF1B1C1D)
val dark900 = Color(0xFF141516)

// Gradients
val splashEnd = Color(0xFF171922)
val envelopeStart = Color(0xFFA6BEF3)
val envelopeEnd = Color(0xFFD3D9FF)
val recommendResultStart = Color(0xFF4E62FF)
val recordEnd = Color(0xFF6F53FF)
val cardStart = Color(0x001B1C1D)

// Others
val kakaoYellow = Color(0xFFFEE500)
val googleBlack = Color(0xFF1F1F1F)
val googleGray = Color(0xFF747775)
val transparent = Color(0x00000000)

@Immutable
data class BongBaekColors(
    val txtDisplayPrimary: Color,
    val txtDisplaySecondary: Color,
    val txtDisplayTertiary: Color,
    val txtDisplaySubtle: Color,
    val txtFieldValue: Color,
    val txtFieldPlaceholder: Color,
    val txtInteractiveInverse: Color,
    val txtInteractivePrimary: Color,
    val txtInteractiveSecondary: Color,
    val txtStatusDisabled: Color,
    val gnbDisplayBase: Color,
    val bgDisplayPrimary: Color,
    val bgDisplaySecondary: Color,
    val bgDisplayCard: Color,
    val bgDisplayChips: Color,
    val bgDisplayRange: Color,
    val bgFieldPrimary: Color,
    val bgFieldSecondary: Color,
    val btnInteractiveInput: Color,
    val btnInteractivePrimary: Color,
    val btnInteractiveSecondary: Color,
    val btnInteractiveTertiary: Color,
    val btnInteractiveSwitch: Color,
    val btnInteractiveAccent: Color,
    val btnInteractiveDisabled: Color,
    val menuSelectedPrimary: Color,
    val iconInteractiveDefault: Color,
    val iconInteractiveInverse: Color,
    val iconFocusedPrimary: Color,
    val iconFocusedSecondary: Color,
    val iconDisabledPrimary: Color,
    val iconDisabledSecondary: Color,
    val borderDisplayTitle: Color,
    val borderDividerPrimary: Color,
    val borderFieldDefault: Color,
    val borderFieldFilled: Color,
    val borderInteractiveSwitch: Color,
    val statusFocused: Color,
    val statusError: Color,
    val splashStart: Color,
    val splashEnd: Color,
    val envelopeStart: Color,
    val envelopeEnd: Color,
    val recommendResultStart: Color,
    val recommendResultEnd: Color,
    val recordStart: Color,
    val recordEnd: Color,
    val cardStart: Color,
    val cardEnd: Color,
    val kakaoYellow: Color,
    val googleWhite: Color,
    val googleBlack: Color,
    val googleGray: Color,
    val black: Color,
    val transparent: Color,
    val splashTitle: Color,
    val splashBg: Color,
    val isLight: Boolean,
)

val lightBongBaekColors = BongBaekColors(
    txtDisplayPrimary = black,
    txtDisplaySecondary = light900,
    txtDisplayTertiary = light600,
    txtDisplaySubtle = primaryNormal,
    txtFieldValue = black,
    txtFieldPlaceholder = light600,
    txtInteractiveInverse = white,
    txtInteractivePrimary = light800,
    txtInteractiveSecondary = light700,
    txtStatusDisabled = light500,
    gnbDisplayBase = light150,
    bgDisplayPrimary = white,
    bgDisplaySecondary = light100,
    bgDisplayCard = light200,
    bgDisplayChips = primaryBackground16,
    bgDisplayRange = light300,
    bgFieldPrimary = light100,
    bgFieldSecondary = light200,
    btnInteractiveInput = white,
    btnInteractivePrimary = light100,
    btnInteractiveSecondary = light200,
    btnInteractiveTertiary = light200,
    btnInteractiveSwitch = light300,
    btnInteractiveAccent = primaryStrong,
    btnInteractiveDisabled = primaryBackground16,
    menuSelectedPrimary = light700,
    iconInteractiveDefault = light600,
    iconInteractiveInverse = white,
    iconFocusedPrimary = primaryNormal,
    iconFocusedSecondary = primaryLight,
    iconDisabledPrimary = light500,
    iconDisabledSecondary = light400,
    borderDisplayTitle = light100,
    borderDividerPrimary = light300,
    borderFieldDefault = light300,
    borderFieldFilled = light500,
    borderInteractiveSwitch = light200,
    statusFocused = primaryNormal,
    statusError = secondaryNormal,
    splashStart = dark750,
    splashEnd = splashEnd,
    envelopeStart = envelopeStart,
    envelopeEnd = envelopeEnd,
    recommendResultStart = recommendResultStart,
    recommendResultEnd = primaryStrong,
    recordStart = primaryNormal,
    recordEnd = recordEnd,
    cardStart = cardStart,
    cardEnd = dark800,
    kakaoYellow = kakaoYellow,
    googleWhite = white,
    googleBlack = googleBlack,
    googleGray = googleGray,
    black = black,
    transparent = transparent,
    splashTitle = white,
    splashBg = dark800,
    isLight = true,
)

val darkBongBaekColors = BongBaekColors(
    txtDisplayPrimary = white,
    txtDisplaySecondary = dark100,
    txtDisplayTertiary = dark400,
    txtDisplaySubtle = primaryLight,
    txtFieldValue = white,
    txtFieldPlaceholder = dark400,
    txtInteractiveInverse = white,
    txtInteractivePrimary = dark100,
    txtInteractiveSecondary = dark200,
    txtStatusDisabled = dark500,
    gnbDisplayBase = dark700,
    bgDisplayPrimary = dark900,
    bgDisplaySecondary = dark800,
    bgDisplayCard = dark750,
    bgDisplayChips = primaryBackground10,
    bgDisplayRange = dark500,
    bgFieldPrimary = dark800,
    bgFieldSecondary = dark750,
    btnInteractiveInput = primaryBackground10,
    btnInteractivePrimary = dark800,
    btnInteractiveSecondary = dark700,
    btnInteractiveTertiary = dark750,
    btnInteractiveSwitch = dark400,
    btnInteractiveAccent = primaryStrong,
    btnInteractiveDisabled = primaryBackground10,
    menuSelectedPrimary = white,
    iconInteractiveDefault = white,
    iconInteractiveInverse = white,
    iconFocusedPrimary = primaryNormal,
    iconFocusedSecondary = primaryLight,
    iconDisabledPrimary = dark500,
    iconDisabledSecondary = dark400,
    borderDisplayTitle = black,
    borderDividerPrimary = charcoal,
    borderFieldDefault = charcoal,
    borderFieldFilled = white,
    borderInteractiveSwitch = dark100,
    statusFocused = primaryNormal,
    statusError = secondaryStrong,
    splashStart = dark750,
    splashEnd = splashEnd,
    envelopeStart = envelopeStart,
    envelopeEnd = envelopeEnd,
    recommendResultStart = recommendResultStart,
    recommendResultEnd = primaryStrong,
    recordStart = primaryNormal,
    recordEnd = recordEnd,
    cardStart = cardStart,
    cardEnd = dark800,
    kakaoYellow = kakaoYellow,
    googleWhite = white,
    googleBlack = googleBlack,
    googleGray = googleGray,
    black = black,
    transparent = transparent,
    splashTitle = white,
    splashBg = dark800,
    isLight = false,
)

val LocalBongBaekColors = staticCompositionLocalOf { lightBongBaekColors }

@Preview(showBackground = true)
@Composable
private fun BongBaekColorsPreview() {
    BongBaekTheme {
        Column {
            listOf(
                white,
                black,
                charcoal,
                primaryNormal,
                primaryStrong,
                primaryLight,
                primaryBackground16,
                primaryBackground10,
                secondaryNormal,
                secondaryStrong,
                light100,
                light150,
                light200,
                light300,
                light400,
                light500,
                light600,
                light700,
                light800,
                light900,
                dark100,
                dark200,
                dark300,
                dark400,
                dark500,
                dark600,
                dark700,
                dark750,
                dark800,
                dark900,
                splashEnd,
                envelopeStart,
                envelopeEnd,
                recommendResultStart,
                recordEnd,
                recordEnd,
                kakaoYellow,
                transparent,
            ).chunked(6).forEach { color ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                ) {
                    color.forEach {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(it)
                                .padding(end = 8.dp),
                        )
                    }
                }
            }
        }
    }
}
