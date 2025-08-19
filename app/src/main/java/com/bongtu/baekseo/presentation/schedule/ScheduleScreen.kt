package com.bongtu.baekseo.presentation.schedule

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.string.schedule_title
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.data.model.event.PageScheduleEvent
import com.bongtu.baekseo.data.model.event.ScheduleEvent
import com.bongtu.baekseo.presentation.record.component.EventCategoryBar
import com.bongtu.baekseo.presentation.record.type.EventCategoryType
import com.bongtu.baekseo.presentation.schedule.ScheduleContract.ScheduleState
import com.bongtu.baekseo.presentation.schedule.ScheduleContract.ScheduleSideEffect.NavigateToHome
import com.bongtu.baekseo.presentation.schedule.ScheduleContract.ScheduleSideEffect.NavigateToDetail
import com.bongtu.baekseo.presentation.schedule.ScheduleContract.ScheduleSideEffect.NavigateToEdit
import com.bongtu.baekseo.presentation.schedule.component.ScheduleEmptyContent
import com.bongtu.baekseo.presentation.schedule.component.ScheduleListContent
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ScheduleRoute(
    setBottomBarVisible: (Boolean) -> Unit,
    navigateToUp: () -> Unit,
    navigateToDetail: (String) -> Unit,
    navigateToEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ScheduleViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lazyListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.clearPage()
        viewModel.fetchScheduleEvent()
        viewModel.getUsername()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToDetail -> navigateToDetail(sideEffect.eventId)
                    NavigateToEdit -> navigateToEdit()
                    NavigateToHome -> navigateToUp()
                }
            }
    }

    LaunchedEffect(uiState.eventCategoryType) {
        lazyListState.scrollToItem(0)
    }

    DisposableEffect(Unit) {
        setBottomBarVisible(false)
        onDispose {
            setBottomBarVisible(true)
        }
    }

    ScheduleScreen(
        uiState = uiState,
        onCategoryClick = viewModel::updateEventType,
        onBackClick = viewModel::navigateToHome,
        onCardClick = viewModel::navigateToDetail,
        navigateToEdit = viewModel::navigateToEdit,
        lazyListState = lazyListState,
        viewModel = viewModel,
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
    viewModel: ScheduleViewModel,
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

        EventCategoryBar(
            selectedCategory = uiState.eventCategoryType,
            onCategoryClick = onCategoryClick,
            isEnabled = true,
        )

        Crossfade(
            targetState = uiState.scheduleLoadState to uiState.eventCategoryType,
        ) { (loadState, category) ->
            when (loadState) {
                is UiState.Empty -> {
                    ScheduleEmptyContent(
                        eventType = category.label,
                        onButtonClick = navigateToEdit,
                        modifier = Modifier
                            .padding(top = 58.dp)
                            .padding(horizontal = 20.dp),
                    )
                }

                is UiState.Failure -> {
                    // TODO: 에러 상태
                }

                is UiState.Loading -> {
                    // TODO: 로딩 상태
                }

                is UiState.Success -> {
                    ScheduleListContent(
                        scheduleEventList = loadState.data.events,
                        onCardClick = onCardClick,
                        lazyListState = lazyListState,
                        updatePage = viewModel::updatePage,
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
    )

    BongBaekTheme {
        ScheduleScreen(
            uiState = ScheduleState(
                scheduleLoadState = UiState.Success(
                    data = PageScheduleEvent(
                        events = events,
                        currentPage = 0,
                        isLast = false,
                    )
                )
            ),
            onCategoryClick = {},
            onBackClick = {},
            onCardClick = {},
            navigateToEdit = {},
            lazyListState = rememberLazyListState(),
            viewModel = hiltViewModel(),
            modifier = Modifier,
        )
    }
}