package com.bongtu.baekseo.presentation.schedule

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.string.schedule_title
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.EventCategoryType
import com.bongtu.baekseo.core.common.type.ScheduleCardType
import com.bongtu.baekseo.core.common.type.ScheduleType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.BongBaekScheduleEmptyContent
import com.bongtu.baekseo.core.designsystem.component.card.BongBaekScheduleCard
import com.bongtu.baekseo.core.designsystem.component.list.BongBaekScheduleList
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekCategoryBar
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.DateFormatter
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.data.model.event.ScheduleEvent
import com.bongtu.baekseo.presentation.schedule.ScheduleContract.ScheduleState
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ScheduleRoute(
    navigateToUp: () -> Unit,
    navigateToEdit: () -> Unit,
    navigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ScheduleViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.clearPage()
        viewModel.fetchScheduleEvent()
    }

    LaunchedEffect(uiState.eventCategoryType) {
        lazyListState.scrollToItem(0)
    }

    ScheduleScreen(
        uiState = uiState,
        onCategoryClick = viewModel::selectEventType,
        onBackClick = navigateToUp,
        onCardClick = navigateToDetail,
        navigateToEdit = navigateToEdit,
        lazyListState = lazyListState,
        updatePage = viewModel::updateNextPage,
        modifier = modifier,
    )
}

@Composable
private fun ScheduleScreen(
    uiState: ScheduleState,
    onCategoryClick: (EventCategoryType) -> Unit,
    onBackClick: () -> Unit,
    onCardClick: (String) -> Unit,
    navigateToEdit: () -> Unit,
    lazyListState: LazyListState,
    updatePage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900)
            .statusBarsPadding(),
    ) {
        BongBaekTopBar(
            title = stringResource(id = schedule_title, uiState.name),
            topBarType = TopBarType.LEADING_ICON,
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp)
                        .size(24.dp)
                        .noRippleClickable(onBackClick),
                    tint = BongBaekTheme.colors.white,
                )
            },
        )

        BongBaekCategoryBar(
            selectedCategory = uiState.eventCategoryType,
            onCategoryClick = onCategoryClick,
            isEnabled = true,
            modifier = Modifier.padding(vertical = 16.dp),
        )

        Crossfade(
            targetState = uiState.scheduleLoadState to uiState.eventCategoryType,
        ) { (loadState, category) ->
            when (loadState) {
                is UiState.Empty -> {
                    BongBaekScheduleEmptyContent(
                        eventType = category.label,
                        scheduleType = ScheduleType.SCHEDULE,
                        onButtonClick = navigateToEdit,
                        modifier = Modifier
                            .padding(
                                top = 100.dp,
                            ),
                    )
                }

                is UiState.Failure -> {
                    // TODO: 에러 상태
                }

                is UiState.Loading -> {
                    // TODO: 로딩 상태
                }

                is UiState.Success -> {
                    val contentPadding = PaddingValues(
                        start = 20.dp,
                        end = 20.dp,
                        top = 20.dp,
                        bottom = 20.dp + WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding(),
                    )
                    BongBaekScheduleList(
                        items = uiState.scheduleList,
                        getKey = { event -> event.eventId },
                        getDate = { event -> event.eventDate },
                        card = { event, padding ->
                            BongBaekScheduleCard(
                                scheduleCardType = ScheduleCardType.SCHEDULE,
                                hostName = event.hostName,
                                hostNickname = event.hostNickname,
                                eventCategory = event.eventCategory,
                                relationship = event.relationship,
                                cost = event.cost,
                                eventDate = DateFormatter.formatToKorean(event.eventDate),
                                onCardClick = { onCardClick(event.eventId) },
                                modifier = Modifier.padding(padding),
                            )
                        },
                        lazyListState = lazyListState,
                        updatePage = updatePage,
                        modifier = modifier,
                        contentPadding = contentPadding,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ScheduleScreenPreview() {
    val events = persistentListOf(
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
    )

    BongBaekTheme {
        ScheduleScreen(
            uiState = ScheduleState(
                scheduleList = events,
                scheduleLoadState = UiState.Success(Unit),
            ),
            onCategoryClick = {},
            onBackClick = {},
            onCardClick = {},
            navigateToEdit = {},
            lazyListState = rememberLazyListState(),
            updatePage = {},
            modifier = Modifier,
        )
    }
}