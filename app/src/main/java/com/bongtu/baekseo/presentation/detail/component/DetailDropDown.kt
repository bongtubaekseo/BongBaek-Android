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
import com.bongtu.baekseo.R.drawable.ic_record_calendar_off
import com.bongtu.baekseo.R.drawable.ic_record_check_off
import com.bongtu.baekseo.R.drawable.ic_record_event_off
import com.bongtu.baekseo.R.drawable.ic_record_location_off
import com.bongtu.baekseo.R.drawable.ic_record_money_off
import com.bongtu.baekseo.R.drawable.ic_record_nickname_off
import com.bongtu.baekseo.R.drawable.ic_record_person_off
import com.bongtu.baekseo.R.drawable.ic_record_relation_off
import com.bongtu.baekseo.R.string.kr_won
import com.bongtu.baekseo.R.string.location_empty_text
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
import com.bongtu.baekseo.core.util.DateFormatter
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.data.model.event.DetailEvent
import com.bongtu.baekseo.presentation.detail.type.DetailDropDownItemType
import com.bongtu.baekseo.presentation.detail.type.DetailDropDownTrailingType
import com.bongtu.baekseo.presentation.detail.type.DetailDropDownTrailingType.TrailingChip
import com.bongtu.baekseo.presentation.detail.type.DetailDropDownTrailingType.TrailingCost
import com.bongtu.baekseo.presentation.detail.type.DetailDropDownTrailingType.TrailingText
import kotlinx.collections.immutable.persistentListOf

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
    val eventDate = remember(event.eventDate) { DateFormatter.formatToKorean(event.eventDate) }

    val locationInfo = event.locationInfo?.takeUnless { info ->
        info.location.isBlank() && info.address.isBlank() &&
                info.latitude == 0.0 && info.longitude == 0.0
    }

    val recordDetailItems = persistentListOf(
        DetailDropDownItemType(
            iconRes = ic_record_person_off,
            labelRes = record_detail_name_title,
            trailingType = TrailingText(event.hostName),
        ),
        DetailDropDownItemType(
            iconRes = ic_record_nickname_off,
            labelRes = record_detail_nickname_title,
            trailingType = TrailingText(event.hostNickname),
        ),
        DetailDropDownItemType(
            iconRes = ic_record_relation_off,
            labelRes = record_detail_relation_title,
            trailingType = TrailingChip(event.relationship),
        ),
        DetailDropDownItemType(
            iconRes = ic_record_event_off,
            labelRes = record_detail_event_title,
            trailingType = TrailingChip(event.eventCategory),
        ),
        DetailDropDownItemType(
            iconRes = ic_record_money_off,
            labelRes = record_detail_cost_title,
            trailingType = DetailDropDownTrailingType.TrailingCost(
                stringResource(
                    record_card_cost,
                    event.cost
                ),
            ),
        ),
        DetailDropDownItemType(
            iconRes = ic_record_check_off,
            labelRes = record_detail_is_attend_title,
            trailingType = TrailingChip(attendType.label),
        ),
        DetailDropDownItemType(
            iconRes = ic_record_calendar_off,
            labelRes = record_detail_calendar_title,
            trailingType = TrailingChip(eventDate),
        ),
        DetailDropDownItemType(
            iconRes = ic_record_location_off,
            labelRes = record_detail_location_title,
            trailingType = if (locationInfo == null)
                TrailingText(stringResource(location_empty_text)) else null,
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
                color = BongBaekTheme.colors.txtDisplayPrimary,
                modifier = Modifier.padding(vertical = 20.dp),
            )

            Icon(
                imageVector = ImageVector.vectorResource(
                    if (isDetailVisible) ic_arrow_up else ic_arrow_down
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .noRippleClickable { isDetailVisible = !isDetailVisible },
                tint = BongBaekTheme.colors.iconInteractiveDefault,
            )
        }

        AnimatedVisibility(
            visible = isDetailVisible,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut(),
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = BongBaekTheme.colors.bgDisplaySecondary,
                        shape = RoundedCornerShape(10.dp),
                    )
                    .padding(20.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    recordDetailItems.forEachIndexed { index, item ->
                        DetailDropDownItem(
                            iconRes = item.iconRes,
                            labelRes = item.labelRes,
                            trailingType = item.trailingType,
                        )
                    }
                }

                locationInfo?.let { data ->
                    DetailLocationContent(
                        location = data.location,
                        address = data.address,
                        latitude = data.latitude,
                        longitude = data.longitude,
                        modifier = Modifier
                            .padding(
                                top = 12.dp,
                            ),
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
                    .padding(vertical = 8.dp)
                    .size(20.dp)
                    .padding(end = 4.dp),
                tint = Color.Unspecified,
            )

            Text(
                text = stringResource(id = labelRes),
                style = BongBaekTheme.typography.body1Medium14,
                color = BongBaekTheme.colors.txtDisplaySecondary,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        trailingType?.let {
            when (trailingType) {
                is TrailingChip ->
                    Row(
                        modifier = modifier
                            .background(
                                color = BongBaekTheme.colors.bgDisplayChips,
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
                            color = BongBaekTheme.colors.statusFocused,
                        )
                    }

                is TrailingText ->
                    Text(
                        text = trailingType.text,
                        style = BongBaekTheme.typography.body1Medium16,
                        color = BongBaekTheme.colors.txtDisplayPrimary,
                    )

                is TrailingCost ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        Text(
                            text = trailingType.text,
                            style = BongBaekTheme.typography.body1Medium16,
                            color = BongBaekTheme.colors.txtDisplayPrimary,
                        )

                        Text(
                            text = stringResource(kr_won),
                            style = BongBaekTheme.typography.body2Regular16,
                            color = BongBaekTheme.colors.txtDisplayPrimary,
                        )
                    }
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
                eventDate = "2024-06-10",
                note = null,
                locationInfo = null,
            )
        )
    }
}
