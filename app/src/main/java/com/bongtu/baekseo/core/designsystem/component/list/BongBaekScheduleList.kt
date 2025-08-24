package com.bongtu.baekseo.core.designsystem.component.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.string.record_card_list_month
import com.bongtu.baekseo.R.string.record_card_list_year
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.OnBottomReached
import com.bongtu.baekseo.data.model.event.ScheduleEvent
import com.bongtu.baekseo.presentation.schedule.model.ScheduleYearMonthEventItem
import com.bongtu.baekseo.presentation.schedule.model.toYearMonthEventItemList
import kotlinx.collections.immutable.ImmutableList

@Composable
fun BongBaekScheduleList(
    scheduleEventList: ImmutableList<ScheduleEvent>,
    card: @Composable (ScheduleEvent, PaddingValues) -> Unit,
    lazyListState: LazyListState,
    updatePage: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(20.dp),
) {
    val yearMonthEventItems = scheduleEventList.toYearMonthEventItemList()

    val hasUserScrolled = remember(lazyListState) {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 0 || lazyListState.isScrollInProgress
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = lazyListState,
        contentPadding = contentPadding,
    ) {
        itemsIndexed(
            items = yearMonthEventItems,
        ) { index, item ->
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
                    card.invoke(item.event, PaddingValues(top = topPadding))
                }
            }
        }
    }

    if (hasUserScrolled.value) {
        lazyListState.OnBottomReached(
            buffer = 3,
            onLoadMore = updatePage,
        )
    }
}
