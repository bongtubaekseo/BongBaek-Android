package com.bongtu.baekseo.core.designsystem.component.chip

import androidx.compose.foundation.background
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
 * @param eventLabel - 경조사 종류 label
 * @param isSelected - 선택 여부
 * @param onClick - click event
 */
@Composable
fun BongBaekFilterChip(
    eventLabel: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bongBaekColors = BongBaekTheme.colors
    val (backgroundColor, textColor) = remember(isSelected) {
        if (isSelected) bongBaekColors.btnInteractiveDisabled to bongBaekColors.statusFocused
        else bongBaekColors.btnInteractiveSecondary to bongBaekColors.txtStatusDisabled
    }

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(6.dp),
            )
            .noRippleClickable(onClick)
            .padding(
                horizontal = 16.dp,
                vertical = 6.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = eventLabel,
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
                color = BongBaekTheme.colors.bgDisplayCard,
                shape = RoundedCornerShape(6.dp),
            )
            .padding(
                horizontal = 16.dp,
                vertical = 6.dp,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = event.label,
            style = BongBaekTheme.typography.body1Medium16,
            color = BongBaekTheme.colors.statusFocused,
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
                eventLabel = EventType.WEDDING.label,
                isSelected = true,
                onClick = { },
            )
            BongBaekFilterChip(
                eventLabel = EventType.WEDDING.label,
                isSelected = false,
                onClick = { },
            )
            BongBaekLabelChip(
                event = EventType.FIRST_BD,
            )
        }
    }
}
