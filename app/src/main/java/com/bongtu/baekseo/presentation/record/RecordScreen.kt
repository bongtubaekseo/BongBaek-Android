package com.bongtu.baekseo.presentation.record

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.AttendType
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.data.model.RecordEvent
import com.bongtu.baekseo.presentation.record.RecordContract.RecordSideEffect.NavigateToAdd
import com.bongtu.baekseo.presentation.record.RecordContract.RecordSideEffect.NavigateToDetail
import com.bongtu.baekseo.presentation.record.RecordContract.RecordUiState
import com.bongtu.baekseo.presentation.record.component.AttendTypeTab
import com.bongtu.baekseo.presentation.record.component.EventCategoryBar
import com.bongtu.baekseo.presentation.record.component.RecordEmptyContent
import com.bongtu.baekseo.presentation.record.component.RecordListContent
import com.bongtu.baekseo.presentation.record.component.RecordTopBar
import com.bongtu.baekseo.presentation.record.type.EventCategoryType
import java.time.LocalDate

@Composable
fun RecordRoute(
    setBottomBarVisible: (Boolean) -> Unit,
    navigateToDetail: (String) -> Unit,
    navigateToAdd: () -> Unit,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    viewModel: RecordViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val bottomPadding = innerPadding.calculateBottomPadding()

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

    LaunchedEffect(Unit) { viewModel.fetchRecordEvent() }

    RecordScreen(
        uiState = uiState,
        innerPadding = innerPadding,
        navigateToDetail = viewModel::navigateToDetail,
        navigateToAdd = viewModel::navigateToAdd,
        onTabClick = viewModel::selectAttendType,
        onCategoryClick = viewModel::selectEventCategory,
        onEnterDeleteModeClick = viewModel::updateDeleteMode,
        onExitDeleteModeClick = viewModel::updateDeleteModeCancel,
        onDeleteSelectedButtonClick = viewModel::updateSelectedDeleteEventId,
        onDeleteClick = viewModel::fetchSelectedDeleteEventIds,
        modifier = modifier
            .then(
                if (uiState.isDeleteMode) {
                    Modifier
                } else {
                    Modifier.padding(bottom = bottomPadding)
                }
            ),
    )
}

@Composable
private fun RecordScreen(
    uiState: RecordUiState,
    innerPadding: PaddingValues,
    navigateToDetail: (String) -> Unit,
    navigateToAdd: () -> Unit,
    onTabClick: (AttendType) -> Unit,
    onCategoryClick: (EventCategoryType) -> Unit,
    onEnterDeleteModeClick: () -> Unit,
    onExitDeleteModeClick: () -> Unit,
    onDeleteSelectedButtonClick: (String) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDeleteButtonEnabled = remember(uiState.selectedDeleteEventIds) {
        uiState.selectedDeleteEventIds.isNotEmpty()
    }

    Column(
        modifier = modifier
            .background(color = BongBaekTheme.colors.gray900)
            .then(
                if (uiState.isDeleteMode) {
                    Modifier.padding(innerPadding)
                } else {
                    Modifier.statusBarsPadding()
                }
            )
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

        when (uiState.recordLoadState) {
            is UiState.Empty -> {
                RecordEmptyContent()
            }

            is UiState.Failure -> {
                // TODO: 에러 상태 화면
            }

            is UiState.Loading -> {
                // TODO: 로딩 상태 화면
            }

            is UiState.Success -> {
                RecordListContent(
                    recordEventList = uiState.recordLoadState.data,
                    isDeleteMode = uiState.isDeleteMode,
                    selectedDeleteEventIds = uiState.selectedDeleteEventIds,
                    onCardClick = navigateToDetail,
                    onDeleteSelectedButtonClick = onDeleteSelectedButtonClick,
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecordDefaultScreenPreview() {
    BongBaekTheme {
        RecordScreen(
            uiState = RecordUiState(
                recordLoadState = UiState.Success(
                    listOf(
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
                ),
            ),
            onTabClick = {},
            onCategoryClick = {},
            onEnterDeleteModeClick = {},
            onExitDeleteModeClick = {},
            onDeleteSelectedButtonClick = {},
            onDeleteClick = {},
            navigateToDetail = {},
            navigateToAdd = {},
            innerPadding = PaddingValues(),
            modifier = Modifier,
        )
    }
}