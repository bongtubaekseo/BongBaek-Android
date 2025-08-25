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
import com.bongtu.baekseo.core.util.toFormattedYearWithMonthPair
import kotlinx.collections.immutable.ImmutableList

@Composable
fun <T> BongBaekScheduleList(
    items: ImmutableList<T>,
    getKey: (T) -> String,
    getDate: (T) -> String,
    card: @Composable (T, PaddingValues) -> Unit,
    lazyListState: LazyListState,
    updatePage: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(20.dp),
) {
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
            items = items,
            key = { _, item -> getKey(item) }
        ) { index, item ->
            val prev = items.getOrNull(index - 1)

            val (curYear, curMonth) = getDate(item).toFormattedYearWithMonthPair()
            val (prevYear, prevMonth) = prev?.let { getDate(it).toFormattedYearWithMonthPair() }
                ?: (null to null)

            val isNewYear = prevYear == null || prevYear != curYear
            val isNewMonth = prevMonth == null || prevMonth != curMonth || isNewYear

            if (isNewYear) {
                Text(
                    text = stringResource(record_card_list_year, curYear),
                    color = BongBaekTheme.colors.white,
                    style = BongBaekTheme.typography.headBold24,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = if (prev == null) 0.dp else 40.dp),
                )
            }

            if (isNewMonth) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .padding(top = 30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(record_card_list_month, curMonth),
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

            val cardTopPadding = if (prev != null && !isNewMonth) 10.dp else 20.dp
            card(item, PaddingValues(top = cardTopPadding))
        }
    }

    if (hasUserScrolled.value) {
        lazyListState.OnBottomReached(
            buffer = 3,
            onLoadMore = updatePage,
        )
    }
}
