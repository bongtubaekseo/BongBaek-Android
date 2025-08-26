package com.bongtu.baekseo.presentation.record

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.AttendType
import com.bongtu.baekseo.core.common.type.EventCategoryType
import com.bongtu.baekseo.core.designsystem.component.BongBaekScheduleEmptyContent
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.data.model.event.PageScheduleEvent
import com.bongtu.baekseo.data.model.event.ScheduleEvent
import com.bongtu.baekseo.presentation.record.RecordContract.RecordSideEffect.NavigateToAdd
import com.bongtu.baekseo.presentation.record.RecordContract.RecordSideEffect.NavigateToDetail
import com.bongtu.baekseo.presentation.record.RecordContract.RecordUiState
import com.bongtu.baekseo.presentation.record.component.AttendTypeTab
import com.bongtu.baekseo.presentation.record.component.EventCategoryBar
import com.bongtu.baekseo.presentation.record.component.RecordListContent
import com.bongtu.baekseo.presentation.record.component.RecordTopBar
import kotlinx.collections.immutable.persistentListOf

@Composable
fun RecordRoute(
    setBottomBarVisible: (Boolean) -> Unit,
    navigateToDetail: (String) -> Unit,
    navigateToAdd: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecordViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lazyListState = rememberLazyListState()

    if (uiState.isDeleteMode) {
        BackHandler { viewModel.updateDeleteModeCancel() }
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToDetail -> navigateToDetail(sideEffect.eventId)
                    is NavigateToAdd -> navigateToAdd()
                }
            }
    }

    LaunchedEffect(uiState.isDeleteMode) {
        setBottomBarVisible(!uiState.isDeleteMode)
    }

    LaunchedEffect(uiState.eventCategoryType) {
        lazyListState.scrollToItem(0)
    }

    LaunchedEffect(Unit) {
        viewModel.clearPage()
        viewModel.fetchRecordEvent()
    }

    RecordScreen(
        uiState = uiState,
        navigateToDetail = viewModel::navigateToDetail,
        navigateToAdd = viewModel::navigateToAdd,
        onTabClick = viewModel::selectAttendType,
        onCategoryClick = viewModel::updateEventType,
        onEnterDeleteModeClick = viewModel::updateDeleteMode,
        onExitDeleteModeClick = viewModel::updateDeleteModeCancel,
        onDeleteSelectedButtonClick = viewModel::updateSelectedDeleteEventId,
        onDeleteClick = viewModel::fetchSelectedDeleteEventIds,
        lazyListState = lazyListState,
        updatePage = viewModel::updateNextPage,
        modifier = modifier,
    )
}

@Composable
private fun RecordScreen(
    uiState: RecordUiState,
    navigateToDetail: (String) -> Unit,
    navigateToAdd: () -> Unit,
    onTabClick: (AttendType) -> Unit,
    onCategoryClick: (EventCategoryType) -> Unit,
    onEnterDeleteModeClick: () -> Unit,
    onExitDeleteModeClick: () -> Unit,
    onDeleteSelectedButtonClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
    lazyListState: LazyListState,
    updatePage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDeleteButtonEnabled = remember(uiState.selectedDeleteEventIds) {
        uiState.selectedDeleteEventIds.isNotEmpty()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900),
    ) {
        RecordTopBar(
            isDeleteMode = uiState.isDeleteMode,
            isDeleteButtonEnabled = isDeleteButtonEnabled,
            navigateToAdd = navigateToAdd,
            onEnterDeleteModeClick = onEnterDeleteModeClick,
            onExitDeleteModeClick = onExitDeleteModeClick,
            onDeleteClick = onDeleteClick,
        )

        AttendTypeTab(
            selectedTab = uiState.attendType,
            onTabClick = onTabClick,
            isEnabled = !uiState.isDeleteMode,
        )

        EventCategoryBar(
            selectedCategory = uiState.eventCategoryType,
            onCategoryClick = onCategoryClick,
            isEnabled = !uiState.isDeleteMode,
        )

        Crossfade(
            targetState = uiState.recordLoadState to uiState.eventCategoryType,
        ) { (loadState, category) ->
            when (loadState) {
                is UiState.Loading -> {
                    // TODO: 로딩 상태 화면
                    // 임시로 흰 화면이 깜빡거려 같은 배경색으로 대체
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = BongBaekTheme.colors.gray900),
                    )
                }

                is UiState.Success -> {
                    RecordListContent(
                        scheduleEventList = loadState.data.events,
                        isDeleteMode = uiState.isDeleteMode,
                        selectedDeleteEventIds = uiState.selectedDeleteEventIds,
                        onCardClick = navigateToDetail,
                        onDeleteSelectedButtonClick = onDeleteSelectedButtonClick,
                        lazyListState = lazyListState,
                        updatePage = updatePage,
                    )
                }

                else -> {
                    BongBaekScheduleEmptyContent(
                        eventType = category.label,
                        onButtonClick = navigateToAdd,
                        modifier = Modifier
                            .padding(top = 58.dp)
                            .padding(horizontal = 20.dp),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RecordDefaultScreenPreview() {
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
        RecordScreen(
            uiState = RecordUiState(
                recordLoadState = UiState.Success(
                    data = PageScheduleEvent(
                        events = events,
                        currentPage = 0,
                        isLast = false
                    )
                )
            ),
            onTabClick = {},
            onCategoryClick = {},
            onEnterDeleteModeClick = {},
            onExitDeleteModeClick = {},
            onDeleteSelectedButtonClick = {},
            onDeleteClick = {},
            navigateToDetail = {},
            navigateToAdd = {},
            updatePage = {},
            lazyListState = rememberLazyListState(),
            modifier = Modifier,
        )
    }
}