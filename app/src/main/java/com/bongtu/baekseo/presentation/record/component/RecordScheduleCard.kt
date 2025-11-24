package com.bongtu.baekseo.presentation.record.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.string.record_card_cost
import com.bongtu.baekseo.core.designsystem.component.badge.BongBaekSmallBadge
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

@Composable
fun RecordScheduleCard(
    hostName: String,
    hostNickname: String,
    eventCategory: String,
    relationship: String,
    cost: Int,
    eventDate: String,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.bgDisplayCard,
                shape = RoundedCornerShape(10.dp),
            )
            .noRippleClickable(onCardClick)
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp,
            ),
    ) {
        Text(
            text = hostNickname,
            style = BongBaekTheme.typography.captionRegular12,
            color = BongBaekTheme.colors.statusFocused,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = hostName,
                style = BongBaekTheme.typography.titleSemiBold18,
                color = BongBaekTheme.colors.txtDisplayPrimary,
            )

            Text(
                text = stringResource(record_card_cost, cost),
                style = BongBaekTheme.typography.titleSemiBold18,
                color = BongBaekTheme.colors.txtDisplayPrimary,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            BongBaekSmallBadge(
                title = eventCategory,
            )

            Spacer(modifier = Modifier.width(4.dp))

            BongBaekSmallBadge(
                title = relationship,
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = eventDate,
                color = BongBaekTheme.colors.txtDisplayTertiary,
                style = BongBaekTheme.typography.captionRegular12,
            )
        }
    }
}

@Preview
@Composable
private fun BongBaekScheduleCardPreview() {
    BongBaekTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            RecordScheduleCard(
                hostName = "헤헤",
                hostNickname = "초록승준",
                eventCategory = "생일",
                relationship = "친구",
                cost = 10000,
                eventDate = "2025년 02월 11일",
                onCardClick = {},
            )

            RecordScheduleCard(
                hostName = "헤헤",
                hostNickname = "초록승준",
                eventCategory = "생일",
                relationship = "친구",
                cost = 10000,
                eventDate = "2025년 02월 11일",
                onCardClick = {},
            )
        }
    }
}
