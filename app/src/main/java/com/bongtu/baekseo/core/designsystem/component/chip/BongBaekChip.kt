package com.bongtu.baekseo.core.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

/**
 * Bong baek filter chip
 *
 * 경조사 filtering 하기 위한 component
 *
 * @param event - 경조사 종류
 * @param isSelected - 선택 여부
 * @param onClick - click event
 */
@Composable
fun BongBaekFilterChip(
    event: EventType,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bongBaekColors = BongBaekTheme.colors
    val (backgroundColor, textColor) = remember(isSelected) {
        if (isSelected) bongBaekColors.gray100 to bongBaekColors.gray700
        else bongBaekColors.gray700 to bongBaekColors.gray300
    }

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(6.dp),
            )
            .padding(
                start = 16.dp,
                top = 5.dp,
                end = 16.dp,
                bottom = 7.dp,
            )
            .noRippleClickable(onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = event.label,
            style = BongBaekTheme.typography.body1Medium16,
            color = textColor,
        )
    }
}


/**
 * Bong baek label chip
 *
 * 경조사 label을 보여주기 위한 component
 *
 * @param event
 */
@Composable
fun BongBaekLabelChip(
    event: EventType,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = BongBaekTheme.colors.gray750,
                shape = RoundedCornerShape(6.dp),
            )
            .border(
                width = 1.dp,
                color = BongBaekTheme.colors.primaryNormal,
                shape = RoundedCornerShape(6.dp),
            )
            .padding(
                start = 16.dp,
                top = 5.dp,
                end = 16.dp,
                bottom = 7.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = event.label,
            style = BongBaekTheme.typography.body1Medium16,
            color = BongBaekTheme.colors.white,
        )
    }
}

@Preview
@Composable
private fun BongBaekChipFilterPreview() {
    BongBaekTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            BongBaekFilterChip(
                event = EventType.WEDDING,
                isSelected = true,
                onClick = { },
            )
            BongBaekFilterChip(
                event = EventType.WEDDING,
                isSelected = false,
                onClick = { },
            )
            BongBaekLabelChip(
                event = EventType.FIRST_BD,
            )
        }
    }
}
