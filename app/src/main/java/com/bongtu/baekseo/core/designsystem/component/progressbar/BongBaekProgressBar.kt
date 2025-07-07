package com.bongtu.baekseo.core.designsystem.component.progressbar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme


/**
 * Bong baek progress bar
 *
 * Material3에서 기본 제공하는 progress bar가 아닌 custom progress bar
 *
 * @param progress
 * @param backgroundColor
 * @param progressColor
 * @param height
 * @param cornerRadius
 */
@Composable
fun BongBaekProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color = BongBaekTheme.colors.gray700,
    progressColor: Color = BongBaekTheme.colors.primaryNormal,
    height: Dp = 4.dp,
    cornerRadius: Dp = 1000.dp,
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
    ) {
        val barWidth = size.width
        val barHeight = size.height

        drawRoundRect(
            color = backgroundColor,
            size = Size(barWidth, barHeight),
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx()),
        )

        drawRoundRect(
            color = progressColor,
            size = Size(barWidth * progress.coerceIn(0f, 1f), barHeight),
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx()),
        )
    }
}

@Preview
@Composable
private fun BongBaekProgressBarPreview() {
    BongBaekTheme {
        BongBaekProgressBar(
            progress = 0.5f,
            modifier = Modifier,
        )
    }
}
