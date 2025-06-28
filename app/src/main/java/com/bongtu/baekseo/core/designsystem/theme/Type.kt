package com.bongtu.baekseo.core.designsystem.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.bongtu.baekseo.R.font.pretendard_bold
import com.bongtu.baekseo.R.font.pretendard_medium
import com.bongtu.baekseo.R.font.pretendard_regular
import com.bongtu.baekseo.R.font.pretendard_semibold
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme.typography


val PretendardBold =
    FontFamily(Font(pretendard_bold))
val PretendardSemiBold =
    FontFamily(Font(pretendard_semibold))
val PretendardMedium =
    FontFamily(Font(pretendard_medium))
val PretendardRegular =
    FontFamily(Font(pretendard_regular))

@Immutable
data class BongBaekTypography(
    val headBold26: TextStyle,
    val headBold24: TextStyle,
    val titleSemiBold20: TextStyle,
    val titleSemiBold18: TextStyle,
    val titleSemiBold16: TextStyle,
    val body1Medium16: TextStyle,
    val body1Medium14: TextStyle,
    val body2Regular16: TextStyle,
    val body2Regular14: TextStyle,
    val captionRegular12: TextStyle,
)

val defaultBongBaekTypography = BongBaekTypography(
    headBold26 = TextStyle(
        fontFamily = PretendardBold,
        fontSize = 26.sp,
        lineHeight = 1.3.em,
        letterSpacing = (-0.02).em,
    ),
    headBold24 = TextStyle(
        fontFamily = PretendardBold,
        fontSize = 24.sp,
        lineHeight = 1.3.em,
        letterSpacing = (-0.03).em,
    ),
    titleSemiBold20 = TextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 20.sp,
        lineHeight = 1.5.em,
        letterSpacing = (-0.03).em,
    ),
    titleSemiBold18 = TextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 18.sp,
        lineHeight = 1.5.em,
        letterSpacing = (-0.02).em,
    ),
    titleSemiBold16 = TextStyle(
        fontFamily = PretendardSemiBold,
        fontSize = 16.sp,
        lineHeight = 1.5.em,
        letterSpacing = (-0.02).em,
    ),
    body1Medium16 = TextStyle(
        fontFamily = PretendardMedium,
        fontSize = 16.sp,
        lineHeight = 1.5.em,
        letterSpacing = (-0.01).em,
    ),
    body1Medium14 = TextStyle(
        fontFamily = PretendardMedium,
        fontSize = 14.sp,
        lineHeight = 1.5.em,
        letterSpacing = (-0.01).em,
    ),
    body2Regular16 = TextStyle(
        fontFamily = PretendardRegular,
        fontSize = 16.sp,
        lineHeight = 1.5.em,
        letterSpacing = (-0.01).em,
    ),
    body2Regular14 = TextStyle(
        fontFamily = PretendardRegular,
        fontSize = 14.sp,
        lineHeight = 1.5.em,
        letterSpacing = (-0.01).em,
    ),
    captionRegular12 = TextStyle(
        fontFamily = PretendardRegular,
        fontSize = 12.sp,
        lineHeight = 1.3.em,
        letterSpacing = (-0.01).em,
    ),
)

val LocalBongBaekTypography = staticCompositionLocalOf { defaultBongBaekTypography }

@Preview(showBackground = true)
@Composable
fun BongBaekTypographyPreview() {
    BongBaekTheme {
        Column {
            Text(
                "BongBaekTypographyPreview",
                style = typography.headBold26,
            )
            Text(
                "BongBaekTypographyPreview",
                style = typography.headBold24,
            )
            Text(
                "BongBaekTypographyPreview",
                style = typography.titleSemiBold20,
            )
            Text(
                "BongBaekTypographyPreview",
                style = typography.titleSemiBold18,
            )
            Text(
                "BongBaekTypographyPreview",
                style = typography.titleSemiBold16,
            )
            Text(
                "BongBaekTypographyPreview",
                style = typography.body1Medium16,
            )
            Text(
                "BongBaekTypographyPreview",
                style = typography.body1Medium14,
            )
            Text(
                "BongBaekTypographyPreview",
                style = typography.body2Regular16,
            )
            Text(
                "BongBaekTypographyPreview",
                style = typography.body2Regular14,
            )
            Text(
                "BongBaekTypographyPreview",
                style = typography.captionRegular12,
            )
        }
    }
}