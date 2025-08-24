package com.bongtu.baekseo.core.designsystem.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.bongtu.baekseo.R.string.record_card_weekday
import com.bongtu.baekseo.core.common.type.HomeScheduleCardInfoType
import com.bongtu.baekseo.core.common.type.ScheduleCardType
import com.bongtu.baekseo.core.designsystem.component.badge.BongBaekSmallBadge
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.core.util.toFormattedDateAndDay
import com.bongtu.baekseo.core.util.toFormattedDateWithDay

@Composable
fun BongBaekScheduleCard(
    scheduleCardType: ScheduleCardType,
    hostName: String,
    hostNickname: String,
    eventCategory: String,
    relationship: String,
    cost: Int,
    eventDate: String,
    modifier: Modifier = Modifier,
    location: String = "",
    onCardClick: () -> Unit = {},
) {
    val isHomeCard = scheduleCardType == ScheduleCardType.HOME
    val isScheduleCard = scheduleCardType == ScheduleCardType.SCHEDULE

    val (date, weekDay) = eventDate.toFormattedDateAndDay()
    val homeDate = eventDate.toFormattedDateWithDay()

    val spacerHeight = if (isHomeCard) 10.dp else 8.dp
    val spacerBadgeHeight = if (isHomeCard) 4.dp else 8.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.gray750,
                shape = RoundedCornerShape(10.dp),
            )
            .then(
                if (isHomeCard) {
                    Modifier.border(
                        width = 1.dp,
                        color = BongBaekTheme.colors.lineNormal,
                        shape = RoundedCornerShape(10.dp),
                    )
                } else {
                    Modifier.noRippleClickable(onCardClick)
                }
            )
            .padding(scheduleCardType.padding),
    ) {
        Text(
            text = hostNickname,
            style = BongBaekTheme.typography.captionRegular12,
            color = BongBaekTheme.colors.primaryNormal,
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = hostName,
                style = BongBaekTheme.typography.titleSemiBold18,
                color = BongBaekTheme.colors.white,
            )

            Text(
                text = stringResource(record_card_cost, cost),
                style = BongBaekTheme.typography.titleSemiBold18,
                color = BongBaekTheme.colors.white,
            )
        }

        Spacer(modifier = Modifier.height(spacerHeight))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BongBaekSmallBadge(
                title = eventCategory,
            )

            Spacer(modifier = Modifier.width(spacerBadgeHeight))

            BongBaekSmallBadge(
                title = relationship,
            )

            if (isScheduleCard) {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = date,
                    color = BongBaekTheme.colors.gray400,
                    style = BongBaekTheme.typography.captionRegular12,
                    modifier = Modifier.padding(end = 4.dp),
                )

                Text(
                    text = stringResource(record_card_weekday, weekDay),
                    color = BongBaekTheme.colors.gray400,
                    style = BongBaekTheme.typography.captionRegular12,
                )
            }
        }

        if (isHomeCard) {
            Spacer(modifier = Modifier.height(12.dp))

            HomeScheduleCardInfo(
                infoType = HomeScheduleCardInfoType.LOCATION,
                content = location.ifBlank { "-" },
            )

            Spacer(modifier = Modifier.height(4.dp))

            HomeScheduleCardInfo(
                infoType = HomeScheduleCardInfoType.DATE,
                content = homeDate,
            )
        }
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
private fun BongBaekScheduleCardPreview() {
    BongBaekTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            BongBaekScheduleCard(
                scheduleCardType = ScheduleCardType.HOME,
                hostName = "헤헤",
                hostNickname = "초록승준",
                eventCategory = "생일",
                relationship = "친구",
                cost = 10000,
                eventDate = "2025-02-11",
                location = "강남",
                onCardClick = {},
            )
            BongBaekScheduleCard(
                scheduleCardType = ScheduleCardType.SCHEDULE,
                hostName = "헤헤",
                hostNickname = "초록승준",
                eventCategory = "생일",
                relationship = "친구",
                cost = 10000,
                eventDate = "2025-02-11",
                location = "강남",
                onCardClick = {},
            )
        }
    }
}