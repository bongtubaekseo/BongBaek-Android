package com.bongtu.baekseo.presentation.detail.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import com.bongtu.baekseo.R.string.record_detail_relation_title
import com.bongtu.baekseo.core.common.type.AttendType
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.core.util.toFormattedShortEnglishDate
import com.bongtu.baekseo.data.model.event.DetailEvent
import com.bongtu.baekseo.presentation.detail.type.DetailDropDownTrailingType
import com.bongtu.baekseo.presentation.detail.type.DetailDropDownTrailingType.TrailingChip
import com.bongtu.baekseo.presentation.detail.type.DetailDropDownTrailingType.TrailingText
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

@Composable
fun DetailDropDown(
    event: DetailEvent,
    modifier: Modifier = Modifier,
) {
    var isDetailVisible by remember { mutableStateOf(false) }
    val attendType = remember(event.isEventParticipated) {
        if (event.isEventParticipated)
            AttendType.ATTEND
        else
            AttendType.ABSENT
    }
    val eventDate = remember(event.eventDate) { event.eventDate.toFormattedShortEnglishDate() }

    val isEmptyLocationInfo = remember(event.locationInfo) {
        with(event) {
            locationInfo?.let {
                it.location.isBlank() &&
                        it.address.isBlank() &&
                        it.latitude == 0.0 &&
                        it.longitude == 0.0
            }
        }
    }

    val locationInfo = if (isEmptyLocationInfo == true) null else event.locationInfo

    val recordDetailItems = persistentListOf(
        Triple(
            ic_person,
            record_detail_name_title,
            TrailingText(event.hostName)
        ),
        Triple(
            ic_nickname,
            record_detail_nickname_title,
            TrailingText(event.hostNickname)
        ),
        Triple(
            ic_relation,
            record_detail_relation_title,
            TrailingChip(event.relationship)
        ),
        Triple(
            ic_event,
            record_detail_event_title,
            TrailingChip(event.eventCategory)
        ),
        Triple(
            ic_coin,
            record_detail_cost_title,
            TrailingText(stringResource(record_card_cost, event.cost))
        ),
        Triple(
            ic_check_gray,
            record_detail_is_attend_title,
            TrailingChip(attendType.label)
        ),
        Triple(
            ic_calendar,
            record_detail_calendar_title,
            TrailingChip(eventDate)
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
                    DetailDropDownItem(
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

                locationInfo?.let { data ->
                    DetailLocationContent(
                        location = data.location,
                        address = data.address,
                        latitude = data.latitude,
                        longitude = data.longitude,
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailDropDownItem(
    @DrawableRes iconRes: Int,
    @StringRes labelRes: Int,
    modifier: Modifier = Modifier,
    trailingType: DetailDropDownTrailingType? = null,
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

@Preview
@Composable
private fun DetailDropDownPreview() {
    BongBaekTheme {
        DetailDropDown(
            event = DetailEvent(
                eventId = "eventId",
                hostName = "김봉백",
                hostNickname = "봉봉",
                eventCategory = "생일",
                relationship = "친구",
                cost = 50000,
                isEventParticipated = true,
                eventDate = LocalDate.of(2024, 6, 10).toString(),
                note = null,
                locationInfo = null,
            )
        )
    }
}
