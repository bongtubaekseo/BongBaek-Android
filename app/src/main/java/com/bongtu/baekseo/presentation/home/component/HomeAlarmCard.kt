package com.bongtu.baekseo.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_calendar
import com.bongtu.baekseo.R.drawable.img_home_alarm
import com.bongtu.baekseo.R.string.home_contents_card_empty_date
import com.bongtu.baekseo.R.string.home_contents_card_empty_description
import com.bongtu.baekseo.R.string.home_contents_card_title
import com.bongtu.baekseo.R.string.home_page_card_title
import com.bongtu.baekseo.R.string.home_page_card_today_title
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

private const val POSTPOSITION_GA = "가"
private const val POSTPOSITION_E = "이"

@Composable
fun HomeAlarmCard(
    hostname: String,
    eventType: String,
    eventDate: String,
    daysLeft: Int,
    modifier: Modifier = Modifier,
) {
    val postposition = if (eventType == "돌잔치") POSTPOSITION_GA else POSTPOSITION_E
    val cardDescription = if (daysLeft == 0) stringResource(
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

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.bgDisplaySecondary,
                shape = RoundedCornerShape(8.dp),
            )
            .border(
                width = 1.dp,
                color = BongBaekTheme.colors.borderFieldDefault,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                top = 14.dp,
                bottom = 14.dp,
                start = 16.dp,
                end = 14.dp,
            ),
        verticalAlignment = Alignment.Top,
    ) {
        HomeAlarmCardContent(
            description = cardDescription,
            date = eventDate,
            modifier = Modifier.weight(1f),
        )
        Spacer(modifier = Modifier.size(4.dp))

        Image(
            painter = painterResource(img_home_alarm),
            contentDescription = null,
            modifier = Modifier
                .size(86.dp, 74.dp),
        )
    }
}

@Composable
fun HomeAlarmEmptyCard(
    modifier: Modifier = Modifier,
) {
    HomeAlarmCardContent(
        description = stringResource(home_contents_card_empty_description),
        date = stringResource(home_contents_card_empty_date),
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.bgDisplaySecondary,
                shape = RoundedCornerShape(8.dp),
            )
            .border(
                width = 1.dp,
                color = BongBaekTheme.colors.borderFieldDefault,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(
                top = 14.dp,
                bottom = 14.dp,
                start = 16.dp,
                end = 14.dp,
            ),
    )
}

@Composable
fun HomeAlarmCardContent(
    description: String,
    date: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(home_contents_card_title),
            style = BongBaekTheme.typography.captionRegular12,
            color = BongBaekTheme.colors.txtDisplaySubtle,
        )

        Text(
            text = description,
            style = BongBaekTheme.typography.titleSemiBold16,
            color = BongBaekTheme.colors.txtDisplayPrimary,
        )

        Spacer(modifier = Modifier.size(6.dp))

        Row(
            modifier = Modifier
                .background(
                    color = BongBaekTheme.colors.bgDisplayPrimary,
                    shape = RoundedCornerShape(2.dp),
                )
                .padding(
                    top = 4.dp,
                    bottom = 4.dp,
                    start = 6.dp,
                    end = 8.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = ic_calendar),
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = BongBaekTheme.colors.iconDisabledPrimary,
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = date,
                style = BongBaekTheme.typography.captionRegular12,
                color = BongBaekTheme.colors.txtDisplaySecondary,
            )
        }
    }
}

@Preview
@Composable
private fun HomeContentsCardPreview() {
    BongBaekTheme {
        HomeAlarmCard(
            hostname = "일이삼사오육칠팔구",
            eventType = "생일",
            daysLeft = 10,
            eventDate = "2025년 02월 11일",
        )
    }
}

@Preview
@Composable
private fun HomeAlarmEmptyCardPreview() {
    BongBaekTheme {
        HomeAlarmEmptyCard()
    }
}

@Preview
@Composable
private fun HomeAlarmCardContentPreview() {
    BongBaekTheme {
        HomeAlarmCardContent(
            description = "",
            date = "",
        )
    }
}
