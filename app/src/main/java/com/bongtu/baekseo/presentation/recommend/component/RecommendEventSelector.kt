package com.bongtu.baekseo.presentation.recommend.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_recommend_birth
import com.bongtu.baekseo.R.drawable.ic_recommend_funeral
import com.bongtu.baekseo.R.drawable.ic_recommend_gift
import com.bongtu.baekseo.R.drawable.ic_recommend_wedding
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

@Composable
fun RecommendEventSelector(
    selectedEvent: EventType?,
    onEventSelect: (EventType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        EventType.entries.forEach { item ->
            EventSelectorItem(
                event = item,
                isSelected = selectedEvent == item,
                modifier = Modifier
                    .noRippleClickable {
                        onEventSelect(item)
                    },
            )
        }
    }
}

@Composable
private fun EventSelectorItem(
    event: EventType,
    isSelected: Boolean?,
    modifier: Modifier = Modifier,
) {
    val (backgroundColor, textColor) =
        if (isSelected == true) BongBaekTheme.colors.statusFocused to BongBaekTheme.colors.iconInteractiveInverse
        else BongBaekTheme.colors.btnInteractiveTertiary to BongBaekTheme.colors.txtInteractivePrimary
    val iconRes = when (event) {
        EventType.WEDDING -> ic_recommend_wedding
        EventType.FUNERAL -> ic_recommend_funeral
        EventType.FIRST_BD -> ic_recommend_birth
        EventType.BIRTHDAY -> ic_recommend_gift
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(
                horizontal = 14.dp,
                vertical = 12.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null,
            modifier = Modifier
                .background(
                    color = BongBaekTheme.colors.bgDisplayPrimary,
                    shape = CircleShape,
                )
                .padding(10.dp),
            tint = Color.Unspecified,
        )

        Text(
            text = event.label,
            style = BongBaekTheme.typography.titleSemiBold16,
            color = textColor,
        )
    }
}

@Preview
@Composable
private fun EventSelectorPreview() {
    BongBaekTheme {
        var selectedEvent by remember { mutableStateOf<EventType?>(null) }

        RecommendEventSelector(
            selectedEvent = selectedEvent,
            onEventSelect = { selectedEvent = it },
        )
    }
}
