package com.bongtu.baekseo.presentation.record.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_check_secondary_red
import com.bongtu.baekseo.R.drawable.ic_record_radio_circle
import com.bongtu.baekseo.core.common.type.ScheduleCardType
import com.bongtu.baekseo.core.compositionlocal.LocalBottomNavigationBarsPadding
import com.bongtu.baekseo.core.designsystem.component.card.BongBaekScheduleCard
import com.bongtu.baekseo.core.designsystem.component.list.BongBaekScheduleList
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.data.model.event.ScheduleEvent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun RecordListContent(
    scheduleEventList: ImmutableList<ScheduleEvent>,
    lazyListState: LazyListState,
    updatePage: () -> Unit,
    onCardClick: (String) -> Unit,
    isDeleteMode: Boolean,
    selectedDeleteEventIds: Set<String>,
    onDeleteSelectedButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val animBottom by animateDpAsState(
        targetValue = if (isDeleteMode) WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
        else LocalBottomNavigationBarsPadding.current.calculateBottomPadding(),
    )

    val contentPadding = PaddingValues(
        start = 20.dp,
        end = 20.dp,
        top = 20.dp,
        bottom = 60.dp + animBottom,
    )

    BongBaekScheduleList(
        items = scheduleEventList,
        getKey = { event -> event.eventId },
        getDate = { event -> event.eventDate },
        card = { event, padding ->
            RecordCard(
                event = event,
                onCardClick = {
                    if (!isDeleteMode) onCardClick(event.eventId)
                    else onDeleteSelectedButtonClick(event.eventId)
                },
                isDeleteMode = isDeleteMode,
                isDeleteToggleCheck = selectedDeleteEventIds.contains(event.eventId),
                onDeleteToggleClick = { onDeleteSelectedButtonClick(event.eventId) },
                modifier = Modifier.padding(padding),
            )
        },
        lazyListState = lazyListState,
        updatePage = updatePage,
        modifier = modifier,
        contentPadding = contentPadding,
    )
}

@Composable
private fun RecordCard(
    event: ScheduleEvent,
    onCardClick: () -> Unit,
    isDeleteMode: Boolean,
    isDeleteToggleCheck: Boolean,
    onDeleteToggleClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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

        BongBaekScheduleCard(
            scheduleCardType = ScheduleCardType.SCHEDULE,
            hostName = event.hostName,
            hostNickname = event.hostNickname,
            eventCategory = event.eventCategory,
            relationship = event.relationship,
            cost = event.cost,
            eventDate = event.eventDate,
            onCardClick = onCardClick,
        )
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
            scheduleEventList = persistentListOf(
                ScheduleEvent(
                    eventId = "1",
                    hostName = "공승준",
                    hostNickname = "초록승준",
                    eventCategory = "결혼식",
                    relationship = "친구",
                    cost = 10000,
                    eventDate = "2025-02-11",
                ),
                ScheduleEvent(
                    eventId = "2",
                    hostName = "공승준",
                    hostNickname = "초록승준",
                    eventCategory = "결혼식",
                    relationship = "친구",
                    cost = 10000,
                    eventDate = "2025-06-11",
                ),
                ScheduleEvent(
                    eventId = "3",
                    hostName = "공승준",
                    hostNickname = "초록승준",
                    eventCategory = "결혼식",
                    relationship = "친구",
                    cost = 10000,
                    eventDate = "2025-08-11",
                ),
                ScheduleEvent(
                    eventId = "4",
                    hostName = "공승준",
                    hostNickname = "초록승준",
                    eventCategory = "결혼식",
                    relationship = "친구",
                    cost = 10000,
                    eventDate = "2025-07-11",
                ),
                ScheduleEvent(
                    eventId = "5",
                    hostName = "공승준",
                    hostNickname = "초록승준",
                    eventCategory = "결혼식",
                    relationship = "친구",
                    cost = 10000,
                    eventDate = "2025-06-11",
                ),
                ScheduleEvent(
                    eventId = "6",
                    hostName = "공승준",
                    hostNickname = "초록승준",
                    eventCategory = "결혼식",
                    relationship = "친구",
                    cost = 10000,
                    eventDate = "2025-05-11",
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
