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
fun RecordRoute(
    modifier: Modifier = Modifier,
    viewModel: RecordViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchRecordEvent()
    }

    RecordScreen(
        uiState = uiState,
        onTabClick = viewModel::updateAttendType,
        onCategoryClick = viewModel::updateEventType,
        onDeleteButtonClick = viewModel::updateToDeletingMode,
        onBackButtonClick = viewModel::updateToDefaultMode,
        modifier = modifier,
    )
}

@Composable
private fun RecordScreen(
    uiState: RecordState,
    onTabClick: (AttendType) -> Unit,
    onCategoryClick: (EventCategoryType) -> Unit,
    onDeleteButtonClick: () -> Unit,
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900),
    ) {
        RecordTopBar(
            isDeleting = uiState.isDeleting,
            onDeleteButtonClick = onDeleteButtonClick,
            onBackButtonClick = onBackButtonClick,
        )

        AttendTypeTab(
            selectedTab = uiState.attendType,
            onTabClick = onTabClick,
        )

        EventCategoryBar(
            selectedCategory = uiState.eventCategoryType,
            onCategoryClick = onCategoryClick,
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
                    isDeleting = uiState.isDeleting,
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecordScreenPreview() {
    BongBaekTheme {
        RecordScreen(
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
                isDeleting = false,
            ),
            onTabClick = {},
            onCategoryClick = {},
            onDeleteButtonClick = {},
            onBackButtonClick = {},
            modifier = Modifier,
        )
    }
}