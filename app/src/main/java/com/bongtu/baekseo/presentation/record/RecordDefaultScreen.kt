package com.bongtu.baekseo.presentation.record

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.data.model.RecordEvent
import com.bongtu.baekseo.presentation.record.RecordContract.RecordState
import com.bongtu.baekseo.presentation.record.component.AttendTypeTab
import com.bongtu.baekseo.presentation.record.component.EventCategoryBar
import com.bongtu.baekseo.presentation.record.component.RecordEmptyContent
import com.bongtu.baekseo.presentation.record.component.RecordListContent
import com.bongtu.baekseo.presentation.record.component.RecordTopBar
import com.bongtu.baekseo.presentation.record.type.AttendType
import com.bongtu.baekseo.presentation.record.type.EventCategoryType
import java.time.LocalDate

@Composable
fun RecordDefaultRoute(
    toggleButtonBar: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecordViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchRecordEvent()
    }

    RecordDefaultScreen(
        uiState = uiState,
        onTabClick = viewModel::updateAttendType,
        onCategoryClick = viewModel::updateEventType,
        onDeleteButtonClick = {
            toggleButtonBar()
            viewModel.updateDeleting()
        },
        onDeleteCancelButtonClick = {
            toggleButtonBar()
            viewModel.updateDeletingCancel()
        },
        onDeletingSelectedButtonClick = viewModel::updateSelectedDeleteEventId,
        onDeletingDeleteButtonClick = viewModel::fetchSelectedDeleteEventIds,
        modifier = modifier,
    )
}

@Composable
private fun RecordDefaultScreen(
    uiState: RecordState,
    onTabClick: (AttendType) -> Unit,
    onCategoryClick: (EventCategoryType) -> Unit,
    onDeleteButtonClick: () -> Unit,
    onDeleteCancelButtonClick: () -> Unit,
    onDeletingSelectedButtonClick: (String) -> Unit,
    onDeletingDeleteButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDeletable = uiState.selectedDeleteEventIds.isNotEmpty()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900),
    ) {
        RecordTopBar(
            onDeleteButtonClick = onDeleteButtonClick,
            onBackButtonClick = onDeleteCancelButtonClick,
            onDeletingDeleteButtonClick = onDeletingDeleteButtonClick,
            isDeleting = uiState.isDeleting,
            isDeletable = isDeletable,
        )

        AttendTypeTab(
            selectedTab = uiState.attendType,
            onTabClick = onTabClick,
            isEnabled = !uiState.isDeleting,
        )

        EventCategoryBar(
            selectedCategory = uiState.eventCategoryType,
            onCategoryClick = onCategoryClick,
            isEnabled = !uiState.isDeleting,
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
                    onCardClick = {},
                    selectedDeleteEventIds = uiState.selectedDeleteEventIds,
                    onDeletingSelectedButtonClick = onDeletingSelectedButtonClick,
                    isDeleting = uiState.isDeleting,
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecordDefaultScreenPreview() {
    BongBaekTheme {
        RecordDefaultScreen(
            uiState = RecordState(
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
            onDeleteButtonClick = {},
            onDeleteCancelButtonClick = {},
            onDeletingSelectedButtonClick = {},
            onDeletingDeleteButtonClick = {},
            modifier = Modifier,
        )
    }
}