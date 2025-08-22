package com.bongtu.baekseo.presentation.record.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_check_secondary_red
import com.bongtu.baekseo.R.drawable.ic_record_radio_circle
import com.bongtu.baekseo.R.string.record_card_cost
import com.bongtu.baekseo.R.string.record_card_list_month
import com.bongtu.baekseo.R.string.record_card_list_year
import com.bongtu.baekseo.R.string.record_card_weekday
import com.bongtu.baekseo.core.compositionlocal.safeDrawingWithBottomNavBar
import com.bongtu.baekseo.core.designsystem.component.badge.BongBaekSmallBadge
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.OnBottomReached
import com.bongtu.baekseo.core.util.excludeTop
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.core.util.plus
import com.bongtu.baekseo.core.util.toFormattedDateAndDay
import com.bongtu.baekseo.data.model.event.ScheduleEvent
import com.bongtu.baekseo.presentation.home.schedule.model.ScheduleYearMonthEventItem
import com.bongtu.baekseo.presentation.home.schedule.model.toYearMonthEventItemList
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun RecordListContent(
    recordEventList: ImmutableList<ScheduleEvent>,
    isDeleteMode: Boolean,
    selectedDeleteEventIds: Set<String>,
    onCardClick: (String) -> Unit,
    onDeleteSelectedButtonClick: (String) -> Unit,
    lazyListState: LazyListState,
    updatePage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val yearMonthEventItems = recordEventList.toYearMonthEventItemList()

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 20.dp)
                + WindowInsets.safeDrawingWithBottomNavBar.excludeTop().asPaddingValues(),
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
                        val isDeleteToggleCheck = selectedDeleteEventIds.contains(eventId)
                        RecordCard(
                            nickname = hostNickname,
                            username = hostName,
                            cost = cost,
                            category = eventCategory,
                            relationship = relationship,
                            eventDate = eventDate,
                            onCardClick = {
                                if (!isDeleteMode) onCardClick(eventId)
                                else onDeleteSelectedButtonClick(eventId)
                            },
                            isDeleteMode = isDeleteMode,
                            isDeleteToggleCheck = isDeleteToggleCheck,
                            onDeleteToggleClick = { onDeleteSelectedButtonClick(eventId) },
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
        onLoadMore = updatePage,
    )
}

@Composable
private fun RecordCard(
    nickname: String,
    username: String,
    cost: Int,
    category: String,
    relationship: String,
    eventDate: String,
    onCardClick: () -> Unit,
    isDeleteMode: Boolean,
    isDeleteToggleCheck: Boolean,
    onDeleteToggleClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (date, weekDay) = eventDate.toFormattedDateAndDay()

    val deletePadding by animateDpAsState(
        targetValue = if (isDeleteMode) 16.dp else 0.dp,
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isDeleteMode) {
            RecordDeleteToggleButton(
                isDeleteToggleCheck = isDeleteToggleCheck,
                onDeleteToggleClick = onDeleteToggleClick,
                modifier = Modifier.padding(end = deletePadding),
            )
        }

        Column(
            modifier = Modifier
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
                text = nickname,
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
                    text = username,
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
                    title = category,
                    modifier = Modifier.padding(end = 8.dp),
                )

                BongBaekSmallBadge(title = relationship)

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
}

@Composable
private fun RecordDeleteToggleButton(
    isDeleteToggleCheck: Boolean,
    onDeleteToggleClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (isDeleteToggleCheck) {
        Icon(
            imageVector = ImageVector.vectorResource(id = ic_check_secondary_red),
            contentDescription = null,
            modifier = modifier
                .size(18.dp)
                .noRippleClickable(onClick = onDeleteToggleClick),
            tint = Color.Unspecified,
        )
    } else {
        Icon(
            imageVector = ImageVector.vectorResource(id = ic_record_radio_circle),
            contentDescription = null,
            modifier = modifier
                .size(18.dp)
                .noRippleClickable(onClick = onDeleteToggleClick),
            tint = Color.Unspecified,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecordContentPreview() {
    BongBaekTheme {
        RecordListContent(
            modifier = Modifier.background(color = BongBaekTheme.colors.gray900),
            recordEventList = persistentListOf(
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
            onDeleteSelectedButtonClick = {},
            isDeleteMode = true,
            lazyListState = rememberLazyListState(),
            updatePage = {},
            selectedDeleteEventIds = setOf("eventId"),
        )
    }
}