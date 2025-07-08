package com.bongtu.baekseo.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_calendar
import com.bongtu.baekseo.R.drawable.img_black_alarm
import com.bongtu.baekseo.R.drawable.img_home_card_multiple
import com.bongtu.baekseo.R.drawable.img_home_card_single
import com.bongtu.baekseo.R.string.badge_schedule_empty
import com.bongtu.baekseo.R.string.home_page_card_description
import com.bongtu.baekseo.R.string.home_page_card_empty
import com.bongtu.baekseo.R.string.home_page_card_title
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.designsystem.component.badge.BongBaekMediumBadge
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.toFormattedDateWithDay
import java.time.LocalDate

@Composable
fun HomePageSingleCard(
    hostname: String,
    eventType: EventType,
    eventDate: LocalDate,
    daysLeft: Int,
    modifier: Modifier = Modifier,
) {
    val (date, weekDay) = eventDate.toFormattedDateWithDay()
    val postposition = remember(eventType) {
        when (eventType) {
            EventType.FIRST_BD -> "가"
            else -> "이"
        }
    }

    Box(
        modifier = modifier.wrapContentSize(),
    ) {
        Image(
            painter = painterResource(id = img_home_card_single),
            contentDescription = null,
            modifier = Modifier
                .wrapContentSize()
                .aspectRatio(32 / 25f),
        )

        Column(
            modifier = Modifier
                .padding(top = 30.dp)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(
                    home_page_card_title,
                    hostname,
                    eventType.label,
                    postposition,
                    daysLeft
                ),
                style = BongBaekTheme.typography.headBold24,
                color = BongBaekTheme.colors.white,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(home_page_card_description),
                style = BongBaekTheme.typography.captionRegular12,
                color = BongBaekTheme.colors.gray100,
            )
        }

        HomePageCardDate(
            date = "$date (${weekDay})",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 20.dp,
                    bottom = 30.dp,
                ),
        )
    }
}

@Composable
fun HomePageMultipleCard(
    hostname: String,
    eventType: EventType,
    eventDate: LocalDate,
    daysLeft: Int,
    modifier: Modifier = Modifier,
) {
    val (date, weekDay) = eventDate.toFormattedDateWithDay()
    val postposition = remember(eventType) {
        when (eventType) {
            EventType.FIRST_BD -> "가"
            else -> "이"
        }
    }

    Box(
        modifier = modifier.wrapContentSize(),
    ) {
        Image(
            painter = painterResource(id = img_home_card_multiple),
            contentDescription = null,
            modifier = Modifier
                .wrapContentSize()
                .aspectRatio(154 / 125f),
        )

        Column(
            modifier = Modifier
                .padding(top = 30.dp)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(
                    home_page_card_title,
                    hostname,
                    eventType.label,
                    postposition,
                    daysLeft
                ),
                style = BongBaekTheme.typography.headBold24,
                color = BongBaekTheme.colors.white,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(home_page_card_description),
                style = BongBaekTheme.typography.captionRegular12,
                color = BongBaekTheme.colors.gray100,
            )
        }

        HomePageCardDate(
            date = "$date (${weekDay})",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start = 20.dp,
                    bottom = 30.dp,
                ),
        )
    }
}

@Composable
fun HomePageEmptyCard(
    onBadgeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.gray800,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(horizontal = 20.dp, vertical = 30.dp),
    ) {
        Text(
            text = stringResource(home_page_card_empty),
            style = BongBaekTheme.typography.headBold24,
            color = BongBaekTheme.colors.white,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
        ) {
            BongBaekMediumBadge(
                title = stringResource(id = badge_schedule_empty),
                onClick = onBadgeClick,
            )

            Image(
                painter = painterResource(id = img_black_alarm),
                contentDescription = null,
                modifier = Modifier
                    .size(126.dp),
            )
        }
    }
}

@Composable
private fun HomePageCardDate(
    date: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .wrapContentSize()
            .background(
                color = BongBaekTheme.colors.gray750,
                shape = RoundedCornerShape(4.dp),
            )
            .padding(vertical = 6.dp)
            .padding(start = 8.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = ic_calendar),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = BongBaekTheme.colors.primaryNormal,
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = date,
            style = BongBaekTheme.typography.captionRegular12,
            color = BongBaekTheme.colors.gray100,
        )
    }
}

@Preview
@Composable
private fun HomePageCardBadgePreview() {
    BongBaekTheme {
        HomePageCardDate(
            date = "2024. 02. 11 (목)",
        )
    }
}

@Preview
@Composable
private fun HomePageCardPreview() {
    BongBaekTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            HomePageSingleCard(
                hostname = "헤헤",
                eventType = EventType.WEDDING,
                daysLeft = 10,
                eventDate = LocalDate.of(2025, 2, 11),
            )

            HomePageMultipleCard(
                hostname = "헤헤",
                eventType = EventType.FIRST_BD,
                daysLeft = 10,
                eventDate = LocalDate.of(2025, 2, 11),
            )

            HomePageEmptyCard(
                onBadgeClick = {},
            )
        }
    }
}