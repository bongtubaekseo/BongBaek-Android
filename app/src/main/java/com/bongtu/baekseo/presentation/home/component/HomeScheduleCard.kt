package com.bongtu.baekseo.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_record_calendar_off
import com.bongtu.baekseo.R.drawable.ic_record_location_off
import com.bongtu.baekseo.R.drawable.ic_record_nickname_off
import com.bongtu.baekseo.R.drawable.ic_record_relation_off
import com.bongtu.baekseo.R.string.home_schedule_card_cost
import com.bongtu.baekseo.R.string.home_schedule_card_title
import com.bongtu.baekseo.R.string.kr_won
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.DateFormatter
import com.bongtu.baekseo.presentation.home.type.HomeScheduleInfoType

@Composable
fun HomeScheduleCard(
    hostName: String,
    hostNickname: String,
    eventCategory: String,
    relationship: String,
    cost: Int,
    eventDate: String,
    location: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(
                color = BongBaekTheme.colors.btnInteractiveSecondary,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(16.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = DateFormatter.getDayOfMonth(eventDate),
                style = BongBaekTheme.typography.titleSemiBold18,
                color = BongBaekTheme.colors.txtDisplayPrimary,
            )

            Text(
                text = DateFormatter.getDayOfWeekKorean(eventDate),
                style = BongBaekTheme.typography.captionRegular12,
                color = BongBaekTheme.colors.txtDisplaySecondary,
            )
        }

        VerticalDivider(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxHeight(),
            thickness = 1.dp,
            color = BongBaekTheme.colors.borderDividerPrimary,
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(home_schedule_card_title, hostName),
                    style = BongBaekTheme.typography.body1Medium16,
                    color = BongBaekTheme.colors.txtDisplayPrimary,
                )
                Text(
                    text = eventCategory,
                    style = BongBaekTheme.typography.body1Medium16,
                    color = BongBaekTheme.colors.txtDisplaySubtle,
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(home_schedule_card_cost, cost),
                    style = BongBaekTheme.typography.titleSemiBold18,
                    color = BongBaekTheme.colors.txtDisplayPrimary,
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = stringResource(kr_won),
                    style = BongBaekTheme.typography.titleSemiBold18,
                    color = BongBaekTheme.colors.txtDisplayPrimary,
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
            ) {
                HomeScheduleCardInfo(
                    infoType = HomeScheduleInfoType.DATE,
                    content = DateFormatter.formatToKorean(eventDate),
                )

                VerticalDivider(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .fillMaxHeight(),
                    thickness = 1.dp,
                    color = BongBaekTheme.colors.borderDividerPrimary,
                )

                HomeScheduleCardInfo(
                    infoType = HomeScheduleInfoType.LOCATION,
                    content = location,
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
            ) {
                HomeScheduleCardInfo(
                    infoType = HomeScheduleInfoType.RELATIONSHIP,
                    content = relationship,
                )

                VerticalDivider(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .fillMaxHeight(),
                    thickness = 1.dp,
                    color = BongBaekTheme.colors.borderDividerPrimary,
                )

                HomeScheduleCardInfo(
                    infoType = HomeScheduleInfoType.NICKNAME,
                    content = hostNickname,
                )
            }
        }
    }
}

@Composable
fun HomeScheduleCardInfo(
    infoType: HomeScheduleInfoType,
    content: String,
    modifier: Modifier = Modifier,
) {
    val iconImage = when (infoType) {
        HomeScheduleInfoType.DATE -> ic_record_calendar_off
        HomeScheduleInfoType.LOCATION -> ic_record_location_off
        HomeScheduleInfoType.RELATIONSHIP -> ic_record_relation_off
        HomeScheduleInfoType.NICKNAME -> ic_record_nickname_off
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconImage),
            contentDescription = null,
            modifier = Modifier.size(12.dp),
            tint = Color.Unspecified,
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = content,
            style = BongBaekTheme.typography.captionRegular12,
            color = BongBaekTheme.colors.txtDisplayTertiary,
        )
    }
}

@Preview
@Composable
private fun HomeScheduleCardPreview() {
    BongBaekTheme {
        HomeScheduleCard(
            hostName = "일이삼사오육칠팔구",
            hostNickname = "히히",
            eventCategory = "생일",
            relationship = "친구",
            cost = 10000,
            location = "강남구 어쩌구 저쩌구",
            eventDate = "2025-02-11",
        )
    }
}
