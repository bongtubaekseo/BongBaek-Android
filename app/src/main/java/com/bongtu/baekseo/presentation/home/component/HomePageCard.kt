package com.bongtu.baekseo.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_calendar
import com.bongtu.baekseo.R.drawable.img_home_black_alarm
import com.bongtu.baekseo.R.drawable.img_home_clock
import com.bongtu.baekseo.R.string.badge_schedule_empty
import com.bongtu.baekseo.R.string.home_page_card_description
import com.bongtu.baekseo.R.string.home_page_card_empty
import com.bongtu.baekseo.R.string.home_page_card_title
import com.bongtu.baekseo.R.string.home_page_card_today_title
import com.bongtu.baekseo.core.designsystem.component.badge.BongBaekMediumBadge
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.toFormattedDateWithDay

private const val POSTPOSITION_GA = "가"
private const val POSTPOSITION_E = "이"

@Composable
fun HomePageCard(
    hostname: String,
    eventType: String,
    eventDate: String,
    daysLeft: Int,
    modifier: Modifier = Modifier,
) {
    val date = eventDate.toFormattedDateWithDay()
    val postposition = if (eventType == "돌잔치") POSTPOSITION_GA else POSTPOSITION_E
    val pageTitle = if (daysLeft == 0) stringResource(
        home_page_card_today_title,
        hostname,
        eventType,
    ) else stringResource(
        home_page_card_title,
        hostname,
        eventType,
        postposition,
        daysLeft,
    )
    val colors = BongBaekTheme.colors
    val colorStops = remember {
        arrayOf(
            0f to colors.gradientCardStop1,
            0.37f to colors.gradientCardStop2,
            0.61f to colors.gradientCardStop3,
            0.82f to colors.gradientCardStop4,
            1f to colors.gradientCardStop5,
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .drawWithCache {
                val brush = Brush.linearGradient(
                    colorStops = colorStops,
                    start = Offset(size.width * 0.33f, 0f),
                    end = Offset(size.width * 0.66f, size.height),
                )
                onDrawBehind {
                    drawRoundRect(
                        brush = brush,
                        cornerRadius = CornerRadius(10.dp.toPx()),
                    )
                }
            },
    ) {
        Column(
            modifier = Modifier
                .padding(top = 30.dp)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = pageTitle,
                style = BongBaekTheme.typography.headBold24,
                color = colors.white,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(home_page_card_description),
                style = BongBaekTheme.typography.captionRegular12,
                color = colors.gray100,
            )

            Spacer(modifier = Modifier.weight(1f))

            HomePageCardDate(
                date = date,
                modifier = Modifier
                    .padding(
                        bottom = 30.dp,
                    ),
            )
        }

        Image(
            painter = painterResource(img_home_clock),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 14.dp,
                    bottom = 24.dp,
                )
                .size(130.dp),
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
                painter = painterResource(id = img_home_black_alarm),
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
        HomePageCard(
            hostname = "헤헤",
            eventType = "생일",
            daysLeft = 10,
            eventDate = "2025.02.11 (목)",
        )
    }
}

@Preview
@Composable
private fun HomePageCardEmptyPreview() {
    BongBaekTheme {
        HomePageEmptyCard(
            onBadgeClick = {},
        )
    }
}
