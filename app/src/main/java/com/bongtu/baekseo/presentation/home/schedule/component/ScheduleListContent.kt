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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.bongtu.baekseo.R.string.record_card_cost
import com.bongtu.baekseo.R.string.record_card_list_month
import com.bongtu.baekseo.R.string.record_card_list_year
import com.bongtu.baekseo.R.string.record_card_weekday
import com.bongtu.baekseo.core.designsystem.component.badge.BongBaekSmallBadge
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.OnBottomReached
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.core.util.toFormattedDateAndDay
import com.bongtu.baekseo.data.model.event.ScheduleEvent
import com.bongtu.baekseo.presentation.home.schedule.ScheduleViewModel
import com.bongtu.baekseo.presentation.home.schedule.model.ScheduleYearMonthEventItem
import com.bongtu.baekseo.presentation.home.schedule.model.toYearMonthEventItemList
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

// TODO: 기록하기와 공통 컴포넌트화 할 것
@Composable
fun ScheduleListContent(
    scheduleEventList: ImmutableList<ScheduleEvent>,
    onCardClick: (String) -> Unit,
    lazyListState: LazyListState,
    viewModel: ScheduleViewModel,
    modifier: Modifier = Modifier,
) {
    val yearMonthEventItems = scheduleEventList.toYearMonthEventItemList()

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = lazyListState,
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
                            hostName = hostName,
                            hostNickname = hostNickname,
                            eventCategory = eventCategory,
                            relationship = relationship,
                            cost = cost,
                            eventDate = eventDate,
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

    lazyListState.OnBottomReached(
        buffer = 3,
        onLoadMore = {
            viewModel.updatePage()
        }
    )
}

@Composable
private fun ScheduleCard(
    hostName: String,
    hostNickname: String,
    eventCategory: String,
    relationship: String,
    cost: Int,
    eventDate: String,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (date, weekDay) = eventDate.toFormattedDateAndDay()

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
            text = hostNickname,
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
                text = hostName,
                color = BongBaekTheme.colors.white,
                style = BongBaekTheme.typography.titleSemiBold18,
            )
            Text(
                text = stringResource(record_card_cost, cost),
                color = BongBaekTheme.colors.white,
                style = BongBaekTheme.typography.titleSemiBold18,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BongBaekSmallBadge(
                title = eventCategory,
                modifier = Modifier.padding(end = 8.dp),
            )

            BongBaekSmallBadge(
                title = relationship,
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
                    hostName = "공승준",
                    hostNickname = "초록승준",
                    eventCategory = "결혼식",
                    relationship = "친구",
                    cost = 10000,
                    eventDate = "2025.02.11 (목)",
                ),
                ScheduleEvent(
                    eventId = "1",
                    hostName = "공승준",
                    hostNickname = "초록승준",
                    eventCategory = "결혼식",
                    relationship = "친구",
                    cost = 10000,
                    eventDate = "2025.09.11 (목)",
                ),
                ScheduleEvent(
                    eventId = "1",
                    hostName = "공승준",
                    hostNickname = "초록승준",
                    eventCategory = "결혼식",
                    relationship = "친구",
                    cost = 10000,
                    eventDate = "2025.08.11 (목)",
                ),
                ScheduleEvent(
                    eventId = "1",
                    hostName = "공승준",
                    hostNickname = "초록승준",
                    eventCategory = "결혼식",
                    relationship = "친구",
                    cost = 10000,
                    eventDate = "2025.07.11 (목)",
                ),
                ScheduleEvent(
                    eventId = "1",
                    hostName = "공승준",
                    hostNickname = "초록승준",
                    eventCategory = "결혼식",
                    relationship = "친구",
                    cost = 10000,
                    eventDate = "2025.06.11 (목)",
                ),
                ScheduleEvent(
                    eventId = "1",
                    hostName = "공승준",
                    hostNickname = "초록승준",
                    eventCategory = "결혼식",
                    relationship = "친구",
                    cost = 10000,
                    eventDate = "2025.05.11 (목)",
                ),
            ),
            onCardClick = {},
            lazyListState = rememberLazyListState(),
            viewModel = hiltViewModel(),
        )
    }
}