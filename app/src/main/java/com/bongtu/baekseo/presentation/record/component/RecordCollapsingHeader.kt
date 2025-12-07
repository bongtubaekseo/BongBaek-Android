package com.bongtu.baekseo.presentation.record.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_arrow_left
import com.bongtu.baekseo.R.drawable.ic_arrow_right
import com.bongtu.baekseo.core.common.type.EventCategoryType
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekCategoryBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

@Composable
fun RecordCollapsingHeader(
    date: String,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    selectedEvent: EventCategoryType,
    onCategoryClick: (EventCategoryType) -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(BongBaekTheme.colors.bgDisplayPrimary)
            .padding(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        DateSection(
            date = date,
            onLeftClick = onLeftClick,
            onRightClick = onRightClick,
        )

        BongBaekCategoryBar(
            selectedCategory = selectedEvent,
            onCategoryClick = onCategoryClick,
            isEnabled = isEnabled,
        )
    }
}

@Composable
private fun DateSection(
    date: String,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = date,
            color = BongBaekTheme.colors.txtDisplayPrimary,
            style = BongBaekTheme.typography.headBold24,
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = ImageVector.vectorResource(ic_arrow_left),
            contentDescription = null,
            modifier = Modifier
                .padding(12.dp)
                .noRippleClickable(onLeftClick),
        )

        Icon(
            imageVector = ImageVector.vectorResource(ic_arrow_right),
            contentDescription = null,
            modifier = Modifier
                .padding(12.dp)
                .noRippleClickable(onRightClick),
        )
    }
}

@Preview
@Composable
private fun RecordCollapsingHeaderPreview() {
    BongBaekTheme {
        RecordCollapsingHeader(
            date = "2023년 12월",
            onLeftClick = {},
            onRightClick = {},
            selectedEvent = EventCategoryType.ALL,
            onCategoryClick = {},
            isEnabled = true,
        )
    }
}
