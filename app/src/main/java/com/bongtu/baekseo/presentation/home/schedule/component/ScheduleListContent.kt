package com.bongtu.baekseo.presentation.home.schedule.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.string.record_card_cost
import com.bongtu.baekseo.R.string.record_card_list_month
import com.bongtu.baekseo.R.string.record_card_list_year
import com.bongtu.baekseo.R.string.record_card_weekday
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.core.designsystem.component.badge.BongBaekSmallBadge
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.core.util.toFormattedDateWithDay
import com.bongtu.baekseo.presentation.home.schedule.model.ScheduleEvent
import com.bongtu.baekseo.presentation.home.schedule.model.ScheduleEventInfo
import com.bongtu.baekseo.presentation.home.schedule.model.ScheduleHostInfo
import com.bongtu.baekseo.presentation.home.schedule.model.ScheduleYearMonthEventItem
import com.bongtu.baekseo.presentation.home.schedule.model.toYearMonthEventItemList
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

// TODO: 기록하기와 공통 컴포넌트화 할 것
@Composable
fun ScheduleListContent(
    scheduleEventList: ImmutableList<ScheduleEvent>,
    onCardClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val yearMonthEventItems = scheduleEventList.toYearMonthEventItemList()

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp),
    ) {
        itemsIndexed(yearMonthEventItems) { index, item ->
            val previousItem = yearMonthEventItems.getOrNull(index - 1)

            val topPadding = remember(item, previousItem) {
                when (item) {
                    is ScheduleYearMonthEventItem.YearHeader -> if (previousItem == null) 0.dp else 40.dp
                    is ScheduleYearMonthEventItem.MonthHeader -> 30.dp
                    is ScheduleYearMonthEventItem.Event -> {
                        if (previousItem is ScheduleYearMonthEventItem.Event) 10.dp else 20.dp
                    }
                }
            }

            if (index == 0) {
                Spacer(modifier = Modifier.height(20.dp))
            }

            when (item) {
                is ScheduleYearMonthEventItem.YearHeader -> {
                    Text(
                        text = stringResource(record_card_list_year, item.year),
                        color = BongBaekTheme.colors.white,
                        style = BongBaekTheme.typography.headBold24,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = topPadding),
                    )
                }

                is ScheduleYearMonthEventItem.MonthHeader -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                            .padding(top = topPadding),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(record_card_list_month, item.month),
                            color = BongBaekTheme.colors.white,
                            style = BongBaekTheme.typography.titleSemiBold16,
                            modifier = Modifier.padding(end = 12.dp),
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = BongBaekTheme.colors.gray750,
                        )
                    }
                }

                is ScheduleYearMonthEventItem.Event -> {
                    with(item.event) {
                        ScheduleCard(
                            hostInfo = hostInfo,
                            eventInfo = eventInfo,
                            onCardClick = { onCardClick(eventId) },
                            modifier = Modifier.padding(top = topPadding),
                        )
                    }
                }
            }

            if (index == yearMonthEventItems.lastIndex) {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun ScheduleCard(
    hostInfo: ScheduleHostInfo,
    eventInfo: ScheduleEventInfo,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (date, weekDay) = eventInfo.eventDate.toFormattedDateWithDay()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .background(color = BongBaekTheme.colors.gray750)
            .padding(
                top = 16.dp,
                bottom = 18.dp,
                start = 20.dp,
                end = 20.dp,
            )
            .noRippleClickable(onClick = onCardClick),
    ) {
        Text(
            text = hostInfo.hostNickname,
            color = BongBaekTheme.colors.primaryNormal,
            style = BongBaekTheme.typography.captionRegular12,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = hostInfo.hostName,
                color = BongBaekTheme.colors.white,
                style = BongBaekTheme.typography.titleSemiBold18,
            )
            Text(
                text = stringResource(record_card_cost, eventInfo.cost),
                color = BongBaekTheme.colors.white,
                style = BongBaekTheme.typography.titleSemiBold18,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BongBaekSmallBadge(
                title = eventInfo.eventCategory.label,
                modifier = Modifier.padding(end = 8.dp),
            )

            BongBaekSmallBadge(
                title = eventInfo.relationship.label,
            )

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
}

@Preview
@Composable
private fun ScheduleListContentPreview() {
    BongBaekTheme {
        ScheduleListContent(
            modifier = Modifier.background(color = BongBaekTheme.colors.gray900),
            scheduleEventList = persistentListOf(
                ScheduleEvent(
                    eventId = "1",
                    hostInfo = ScheduleHostInfo(
                        hostName = "공승준",
                        hostNickname = "초록승준",
                    ),
                    eventInfo = ScheduleEventInfo(
                        eventCategory = EventType.WEDDING,
                        relationship = RelationType.FRIEND,
                        cost = 10000,
                        eventDate = LocalDate.of(2025, 3, 11),
                    ),
                ),
                ScheduleEvent(
                    eventId = "2",
                    hostInfo = ScheduleHostInfo(
                        hostName = "김종명",
                        hostNickname = "봉준호",
                    ),
                    eventInfo = ScheduleEventInfo(
                        eventCategory = EventType.FIRST_BD,
                        relationship = RelationType.NEIGHBOR,
                        cost = 10000,
                        eventDate = LocalDate.of(2025, 2, 11),
                    ),
                ),
                ScheduleEvent(
                    eventId = "3",
                    hostInfo = ScheduleHostInfo(
                        hostName = "김헤정",
                        hostNickname = "메정",
                    ),
                    eventInfo = ScheduleEventInfo(
                        eventCategory = EventType.BIRTHDAY,
                        relationship = RelationType.ALUMNI,
                        cost = 10000,
                        eventDate = LocalDate.of(2025, 1, 11),
                    ),
                ),
                ScheduleEvent(
                    eventId = "4",
                    hostInfo = ScheduleHostInfo(
                        hostName = "공승준",
                        hostNickname = "초록승준",
                    ),
                    eventInfo = ScheduleEventInfo(
                        eventCategory = EventType.WEDDING,
                        relationship = RelationType.FRIEND,
                        cost = 10000,
                        eventDate = LocalDate.of(2025, 6, 11),
                    ),
                ),
                ScheduleEvent(
                    eventId = "5",
                    hostInfo = ScheduleHostInfo(
                        hostName = "김종명",
                        hostNickname = "봉준호",
                    ),
                    eventInfo = ScheduleEventInfo(
                        eventCategory = EventType.FIRST_BD,
                        relationship = RelationType.NEIGHBOR,
                        cost = 10000,
                        eventDate = LocalDate.of(2025, 8, 11),
                    ),
                ),
                ScheduleEvent(
                    eventId = "6",
                    hostInfo = ScheduleHostInfo(
                        hostName = "김헤정",
                        hostNickname = "메정",
                    ),
                    eventInfo = ScheduleEventInfo(
                        eventCategory = EventType.BIRTHDAY,
                        relationship = RelationType.ALUMNI,
                        cost = 10000,
                        eventDate = LocalDate.of(2025, 8, 11),
                    ),
                ),
            ),
            onCardClick = {},
        )
    }
}