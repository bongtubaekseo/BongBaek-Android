package com.bongtu.baekseo.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_calendar
import com.bongtu.baekseo.R.drawable.ic_location
import com.bongtu.baekseo.R.string.record_card_cost
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.core.designsystem.component.badge.BongBaekSmallBadge
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.toFormattedDateWithDay
import com.bongtu.baekseo.presentation.home.model.HomeEvent
import com.bongtu.baekseo.presentation.home.model.HomeEventInfo
import com.bongtu.baekseo.presentation.home.model.HomeHostInfo
import com.bongtu.baekseo.presentation.home.model.HomeLocationInfo
import com.bongtu.baekseo.presentation.home.type.HomeScheduleCardInfoType
import java.time.LocalDate

@Composable
fun HomeScheduleCard(
    event: HomeEvent,
    modifier: Modifier = Modifier,
) {
    val (date, weekDay) = event.eventInfo.eventDate.toFormattedDateWithDay()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.gray750,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(
                horizontal = 20.dp,
                vertical = 18.dp,
            ),
    ) {
        Text(
            text = event.hostInfo.hostNickname,
            style = BongBaekTheme.typography.captionRegular12,
            color = BongBaekTheme.colors.primaryNormal,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = event.hostInfo.hostName,
                style = BongBaekTheme.typography.titleSemiBold18,
                color = BongBaekTheme.colors.white,
            )

            Text(
                text = stringResource(record_card_cost, event.eventInfo.cost),
                style = BongBaekTheme.typography.titleSemiBold18,
                color = BongBaekTheme.colors.white,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            BongBaekSmallBadge(
                title = event.eventInfo.eventCategory.label,
            )

            BongBaekSmallBadge(
                title = event.eventInfo.relationship.label,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        HomeScheduleCardInfo(
            infoType = HomeScheduleCardInfoType.LOCATION,
            content = event.locationInfo.location,
        )

        Spacer(modifier = Modifier.height(4.dp))

        HomeScheduleCardInfo(
            infoType = HomeScheduleCardInfoType.DATE,
            content = "$date (${weekDay})",
        )
    }
}

@Composable
private fun HomeScheduleCardInfo(
    infoType: HomeScheduleCardInfoType,
    content: String,
    modifier: Modifier = Modifier,
) {
    val iconImage = if (infoType == HomeScheduleCardInfoType.LOCATION) ic_location else ic_calendar

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.gray800,
                shape = RoundedCornerShape(4.dp),
            )
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconImage),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = BongBaekTheme.colors.primaryNormal,
        )

        Text(
            text = content,
            style = BongBaekTheme.typography.captionRegular12,
            color = BongBaekTheme.colors.gray200,
        )
    }
}

@Preview
@Composable
private fun HomeScheduleCardInfoPreview() {
    BongBaekTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            HomeScheduleCardInfo(
                infoType = HomeScheduleCardInfoType.LOCATION,
                content = "강남구 테헤란로 강남 웨딩홀",
            )
            HomeScheduleCardInfo(
                infoType = HomeScheduleCardInfoType.DATE,
                content = "2024. 02. 11 (목)",
            )
        }
    }
}

@Preview
@Composable
private fun HomeScheduleCardPreview() {
    BongBaekTheme {
        HomeScheduleCard(
            event = HomeEvent(
                eventId = "eventId",
                hostInfo = HomeHostInfo(
                    hostName = "헤헤",
                    hostNickname = "헤헤헤",
                ),
                eventInfo = HomeEventInfo(
                    eventCategory = EventType.BIRTHDAY,
                    relationship = RelationType.FRIEND,
                    cost = 10000,
                    eventDate = LocalDate.of(2025, 2, 11),
                ),
                locationInfo = HomeLocationInfo(
                    location = "강남구 테헤란로 강남 웨딩홀"
                )
            )
        )
    }
}