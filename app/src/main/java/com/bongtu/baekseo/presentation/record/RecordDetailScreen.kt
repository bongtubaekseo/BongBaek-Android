package com.bongtu.baekseo.presentation.record

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.drawable.ic_edit
import com.bongtu.baekseo.R.string.record_card_cost
import com.bongtu.baekseo.R.string.record_card_weekday
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.core.util.toFormattedDateWithDay
import com.bongtu.baekseo.data.model.RecordEvent
import com.bongtu.baekseo.presentation.record.type.AttendType
import java.time.LocalDate

@Composable
private fun RecordDetailScreen(
    onBackButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    /* TODO: state 수정 예정 임시 더미 데이터 사용 */
    val event = RecordEvent(
        eventId = "eventId",
        hostName = "김봉백",
        hostNickName = "봉봉",
        category = EventType.WEDDING.label,
        relationship = RelationType.FAMILY.label,
        cost = 50000,
        eventDate = LocalDate.of(2024, 6, 10),
    )
    val (location, address) = "강남 알베르" to "강남구 테헤란로 123-4 567호"
    val (isAttend, note) = AttendType.ATTEND.label to "메모 입니다아아아아"

    Column(
        modifier = modifier
            .background(color = BongBaekTheme.colors.gray900)
            .systemBarsPadding(),
    ) {
        BongBaekTopBar(
            title = "경조사 상세 조회",
            modifier = modifier,
            topBarType = TopBarType.BOTH_ICONS,
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_back),
                    contentDescription = null,
                    modifier = modifier
                        .padding(12.dp)
                        .noRippleClickable(onClick = onBackButtonClick),
                    tint = BongBaekTheme.colors.white,
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_edit),
                    contentDescription = null,
                    modifier = modifier
                        .padding(14.dp)
                        .size(20.dp)
                        .noRippleClickable(onClick = onEditButtonClick),
                    tint = BongBaekTheme.colors.white,
                )
            },
        )

        RecordDetailTitleCard(
            title = "김승우의 결혼식",
            eventDate = LocalDate.of(2024, 8, 10),
            modifier = Modifier.padding(20.dp),
        )

        RecordDetailCostCard(
            cost = event.cost,
            modifier = Modifier.padding(20.dp),
        )
    }
}

@Composable
fun RecordDetailTitleCard(
    title: String,
    eventDate: LocalDate,
    modifier: Modifier = Modifier,
) {
    val (date, weekDay) = eventDate.toFormattedDateWithDay()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .background(color = BongBaekTheme.colors.gray750)
            .padding(20.dp)
    ) {
        Text(
            text = title,
            color = BongBaekTheme.colors.white,
            style = BongBaekTheme.typography.titleSemiBold18,
        )
        Row(
            modifier = Modifier
                .padding(top = 2.dp)
        ) {
            Text(
                text = date,
                color = BongBaekTheme.colors.gray400,
                style = BongBaekTheme.typography.body2Regular14,
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                text = stringResource(record_card_weekday, weekDay),
                color = BongBaekTheme.colors.gray400,
                style = BongBaekTheme.typography.body2Regular14,
            )
        }
    }
}

@Composable
private fun RecordDetailCostCard(
    cost: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        BongBaekTheme.colors.primaryNormal,
                        Color(0xFF6F53FF),       // TODO: 컬러 세팅 필요
                    ),
                ),
                shape = RoundedCornerShape(10.dp),
            )
            .border(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFBFB8FF),      // TODO: 컬러 세팅 필요
                        BongBaekTheme.colors.primaryNormal,
                    ),
                ),
                width = 1.dp,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "경조사비",
                color = BongBaekTheme.colors.white,
                style = BongBaekTheme.typography.titleSemiBold16,
            )
            Text(
                text = stringResource(record_card_cost, cost),
                color = BongBaekTheme.colors.white,
                style = BongBaekTheme.typography.titleSemiBold18,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecordDetailScreenPreview() {
    BongBaekTheme {
        RecordDetailScreen(
            onBackButtonClick = {},
            onEditButtonClick = {},
        )
    }
}