package com.bongtu.baekseo.presentation.record.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_arrow_down
import com.bongtu.baekseo.R.drawable.ic_arrow_up
import com.bongtu.baekseo.R.drawable.ic_calendar
import com.bongtu.baekseo.R.drawable.ic_check_gray
import com.bongtu.baekseo.R.drawable.ic_coin
import com.bongtu.baekseo.R.drawable.ic_event
import com.bongtu.baekseo.R.drawable.ic_location
import com.bongtu.baekseo.R.drawable.ic_nickname
import com.bongtu.baekseo.R.drawable.ic_person
import com.bongtu.baekseo.R.drawable.ic_relation
import com.bongtu.baekseo.R.string.record_card_cost
import com.bongtu.baekseo.R.string.record_detail_calendar_title
import com.bongtu.baekseo.R.string.record_detail_cost_title
import com.bongtu.baekseo.R.string.record_detail_dropdown_title
import com.bongtu.baekseo.R.string.record_detail_event_title
import com.bongtu.baekseo.R.string.record_detail_is_attend_title
import com.bongtu.baekseo.R.string.record_detail_location_title
import com.bongtu.baekseo.R.string.record_detail_name_title
import com.bongtu.baekseo.R.string.record_detail_nickname_title
import com.bongtu.baekseo.R.string.record_detail_realtion_title
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.core.util.toFormattedShortMonth
import com.bongtu.baekseo.data.model.RecordEvent
import com.bongtu.baekseo.presentation.record.type.RecordDetailDropDownTrailingType
import com.bongtu.baekseo.presentation.record.type.RecordDetailDropDownTrailingType.TrailingChip
import com.bongtu.baekseo.presentation.record.type.RecordDetailDropDownTrailingType.TrailingText
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

@Composable
fun RecordDetailDropDown(
    event: RecordEvent,
    modifier: Modifier = Modifier,
    attendLabel: String,
    location: String? = null,
    address: String? = null,
) {
    var isDetailVisible by remember { mutableStateOf(false) }

    val recordDetailItems = persistentListOf(
        Triple(
            ic_person,
            record_detail_name_title,
            TrailingText(event.hostName)
        ),
        Triple(
            ic_nickname,
            record_detail_nickname_title,
            TrailingText(event.hostNickName)
        ),
        Triple(
            ic_relation,
            record_detail_realtion_title,
            TrailingChip(event.relationship)
        ),
        Triple(
            ic_event,
            record_detail_event_title,
            TrailingChip(event.category)
        ),
        Triple(
            ic_coin,
            record_detail_cost_title,
            TrailingText(stringResource(record_card_cost, event.cost))
        ),
        Triple(
            ic_check_gray,
            record_detail_is_attend_title,
            TrailingChip(attendLabel)
        ),
        Triple(
            ic_calendar,
            record_detail_calendar_title,
            TrailingChip(event.eventDate.toFormattedShortMonth())
        ),
        Triple(
            ic_location,
            record_detail_location_title,
            null
        )
    )

    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(record_detail_dropdown_title),
                style = BongBaekTheme.typography.titleSemiBold18,
                color = BongBaekTheme.colors.white,
                modifier = Modifier.padding(vertical = 20.dp),
            )
            Icon(
                imageVector = ImageVector.vectorResource(
                    if (isDetailVisible) ic_arrow_up else ic_arrow_down
                ),
                contentDescription = null,
                modifier = Modifier
                    .noRippleClickable { isDetailVisible = !isDetailVisible },
                tint = BongBaekTheme.colors.white,
            )
        }

        AnimatedVisibility(
            visible = isDetailVisible,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = BongBaekTheme.colors.gray800,
                        shape = RoundedCornerShape(10.dp),
                    )
                    .padding(
                        horizontal = 20.dp,
                        vertical = 24.dp,
                    ),
            ) {
                recordDetailItems.forEachIndexed { index, (iconRes, labelId, type) ->
                    RecordDetailDropDownItem(
                        iconRes = iconRes,
                        labelRes = labelId,
                        trailingType = type,
                    )
                    Spacer(
                        modifier = Modifier.height(
                            if (index == recordDetailItems.lastIndex) 12.dp else 24.dp
                        ),
                    )
                }

                // TODO: Location Info field 로 변경 예정
                if (location != null && address != null) {
                    RecordDetailDropDownLocationContent(
                        location = location,
                        address = address,
                    )
                }
            }
        }
    }
}

@Composable
private fun RecordDetailDropDownItem(
    @DrawableRes iconRes: Int,
    @StringRes labelRes: Int,
    modifier: Modifier = Modifier,
    trailingType: RecordDetailDropDownTrailingType? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .size(21.dp)
                    .padding(end = 4.dp),
                tint = Color.Unspecified,
            )
            Text(
                text = stringResource(id = labelRes),
                style = BongBaekTheme.typography.body1Medium14,
                color = BongBaekTheme.colors.gray100,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        trailingType?.let {
            when (trailingType) {
                is TrailingChip ->
                    Row(
                        modifier = modifier
                            .background(
                                color = BongBaekTheme.colors.primaryBackground,
                                shape = RoundedCornerShape(4.dp),
                            )
                            .padding(
                                horizontal = 8.dp,
                                vertical = 3.dp,
                            ),
                    ) {
                        Text(
                            text = trailingType.text,
                            style = BongBaekTheme.typography.body2Regular14,
                            color = BongBaekTheme.colors.primaryNormal,
                        )
                    }

                is TrailingText ->
                    Text(
                        text = trailingType.text,
                        style = BongBaekTheme.typography.body1Medium16,
                        color = BongBaekTheme.colors.white,
                        modifier = Modifier
                            .padding(vertical = 6.dp),
                    )
            }
        }
    }
}

private const val MAP_RATIO = 280f / 180f

@Composable
private fun RecordDetailDropDownLocationContent(
    location: String,      // TODO: field 수정 예정 LatLng
    address: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.gray700,
                shape = RoundedCornerShape(10.dp),
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = BongBaekTheme.colors.primaryLight,
                    shape = RoundedCornerShape(10.dp),
                )
                .aspectRatio(MAP_RATIO),
        ) {
            // TODO: 지도로 대체
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp,
                ),
        ) {
            Text(
                text = location,
                style = BongBaekTheme.typography.body1Medium16,
                color = BongBaekTheme.colors.white,
                modifier = Modifier
                    .padding(bottom = 2.dp),
            )
            Text(
                text = address,
                style = BongBaekTheme.typography.body2Regular14,
                color = BongBaekTheme.colors.gray400,
            )
        }
    }
}

@Preview
@Composable
private fun RecordDetailDropDownPreview() {
    BongBaekTheme {
        RecordDetailDropDown(
            event = RecordEvent(
                eventId = "",
                hostName = "김봉백",
                hostNickName = "봉봉",
                relationship = "가족/친척",
                category = "결혼식",
                cost = 100000,
                eventDate = LocalDate.of(2024, 10, 5),
            ),
            attendLabel = "참석",
            location = "서울특별시 강남구 예식장",
            address = "서울특별시 강남구 예식장",
        )
    }
}