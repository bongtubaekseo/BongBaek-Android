package com.bongtu.baekseo.presentation.record.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.core.designsystem.component.badge.BongBaekSmallBadge
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.core.util.toFormattedCost
import com.bongtu.baekseo.core.util.toFormattedDateWithDay
import com.bongtu.baekseo.data.model.RecordEvent
import com.bongtu.baekseo.presentation.record.model.YearMonthEventItem
import com.bongtu.baekseo.presentation.record.model.toYearMonthEventItemList
import java.time.LocalDate

@Composable
fun RecordListContent(
    recordEventList: List<RecordEvent>,
    onCardClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val yearMonthEventItems = recordEventList.toYearMonthEventItemList()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        itemsIndexed(yearMonthEventItems) { index, item ->
            val previousItem = yearMonthEventItems.getOrNull(index - 1)

            val topPadding = remember(item, previousItem) {
                when (item) {
                    is YearMonthEventItem.YearHeader -> if (previousItem == null) 0.dp else 40.dp
                    is YearMonthEventItem.MonthHeader -> 30.dp
                    is YearMonthEventItem.Event -> {
                        if (previousItem is YearMonthEventItem.Event) 10.dp else 20.dp
                    }
                }
            }

            if (index == 0) {
                Spacer(
                    modifier = Modifier.height(20.dp),
                )
            }

            when (item) {
                is YearMonthEventItem.YearHeader -> {
                    Text(
                        text = "${item.year}년",
                        color = BongBaekTheme.colors.white,
                        style = BongBaekTheme.typography.headBold24,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = topPadding),
                    )
                }

                is YearMonthEventItem.MonthHeader -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                            .padding(top = topPadding),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "${item.month}월",
                            color = BongBaekTheme.colors.white,
                            style = BongBaekTheme.typography.titleSemiBold16,
                            modifier = Modifier
                                .padding(end = 12.dp),
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = BongBaekTheme.colors.gray750,
                        )
                    }
                }

                is YearMonthEventItem.Event -> {
                    with(item.event) {
                        RecordCard(
                            nickname = hostNickName,
                            username = hostName,
                            cost = cost,
                            category = category,
                            relationship = relationship,
                            eventDate = eventDate,
                            onCardClick = { onCardClick(eventId) },
                            modifier = Modifier
                                .padding(top = topPadding),
                        )
                    }
                }
            }

            if (index == yearMonthEventItems.lastIndex) {
                Spacer(
                    modifier = Modifier.height(20.dp),
                )
            }
        }
    }
}

@Composable
private fun RecordCard(
    nickname: String,
    username: String,
    cost: Int,
    category: String,
    relationship: String,
    eventDate: LocalDate,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (date, weekDay) = eventDate.toFormattedDateWithDay()

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
                text = "${cost.toFormattedCost()}원",
                color = BongBaekTheme.colors.white,
                style = BongBaekTheme.typography.titleSemiBold18,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BongBaekSmallBadge(
                title = category,
                modifier = Modifier
                    .padding(end = 8.dp)
            )

            BongBaekSmallBadge(
                title = relationship,
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = date,
                color = BongBaekTheme.colors.gray400,
                style = BongBaekTheme.typography.captionRegular12,
            )

            Text(
                text = "(${weekDay})",
                color = BongBaekTheme.colors.gray400,
                style = BongBaekTheme.typography.captionRegular12,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecordContentPreview() {
    BongBaekTheme {
        RecordListContent(
            modifier = Modifier
                .background(color = BongBaekTheme.colors.gray900),
            recordEventList = listOf(
                RecordEvent(
                    eventId = "eventId",
                    hostName = "username",
                    hostNickName = "nickname",
                    category = "경조사 유형",
                    relationship = "관계",
                    cost = 10000,
                    eventDate = LocalDate.of(2025, 5, 4),
                ),
                RecordEvent(
                    eventId = "eventId",
                    hostName = "username",
                    hostNickName = "nickname",
                    category = "경조사 유형",
                    relationship = "관계",
                    cost = 10000,
                    eventDate = LocalDate.of(2025, 5, 2),
                ),
                RecordEvent(
                    eventId = "eventId",
                    hostName = "username",
                    hostNickName = "nickname",
                    category = "경조사 유형",
                    relationship = "관계",
                    cost = 10000,
                    eventDate = LocalDate.of(2025, 4, 10),
                ),
                RecordEvent(
                    eventId = "eventId",
                    hostName = "username",
                    hostNickName = "nickname",
                    category = "경조사 유형",
                    relationship = "관계",
                    cost = 10000,
                    eventDate = LocalDate.of(2025, 2, 23),
                ),
                RecordEvent(
                    eventId = "eventId",
                    hostName = "username",
                    hostNickName = "nickname",
                    category = "경조사 유형",
                    relationship = "관계",
                    cost = 10000,
                    eventDate = LocalDate.of(2024, 6, 8),
                ),
                RecordEvent(
                    eventId = "eventId",
                    hostName = "username",
                    hostNickName = "nickname",
                    category = "경조사 유형",
                    relationship = "관계",
                    cost = 10000,
                    eventDate = LocalDate.of(2024, 5, 24),
                ),
                RecordEvent(
                    eventId = "eventId",
                    hostName = "username",
                    hostNickName = "nickname",
                    category = "경조사 유형",
                    relationship = "관계",
                    cost = 10000,
                    eventDate = LocalDate.of(2023, 2, 4),
                ),
            ),
            onCardClick = {},
        )
    }
}