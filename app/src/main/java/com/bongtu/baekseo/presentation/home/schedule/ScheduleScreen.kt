package com.bongtu.baekseo.presentation.home.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.string.schedule_title
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.presentation.home.schedule.ScheduleContract.ScheduleState
import com.bongtu.baekseo.presentation.home.schedule.component.ScheduleListContent
import com.bongtu.baekseo.presentation.home.schedule.model.ScheduleEvent
import com.bongtu.baekseo.presentation.home.schedule.model.ScheduleEventInfo
import com.bongtu.baekseo.presentation.home.schedule.model.ScheduleHostInfo
import com.bongtu.baekseo.presentation.record.component.EventCategoryBar
import com.bongtu.baekseo.presentation.record.type.EventCategoryType
import java.time.LocalDate

@Composable
fun ScheduleRoute(
    setBottomBarVisible: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ScheduleViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchScheduleEvent()
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
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@Composable
private fun ScheduleScreen(
    uiState: ScheduleState,
    onCategoryClick: (EventCategoryType) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(color = BongBaekTheme.colors.gray900)
    ) {
        BongBaekTopBar(
            title = stringResource(id = schedule_title, "봉백"),
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
            }
        )

        EventCategoryBar(
            selectedCategory = uiState.eventCategoryType,
            onCategoryClick = onCategoryClick,
            isEnabled = true,
        )

        when (uiState.scheduleLoadState) {
            is UiState.Empty -> {
                // TODO: 빈 상태
            }

            is UiState.Failure -> {
                // TODO: 에러 상태
            }

            is UiState.Loading -> {
                // TODO: 로딩 상태
            }

            is UiState.Success -> {
                ScheduleListContent(
                    scheduleEventList = uiState.scheduleLoadState.data,
                    onCardClick = {
                        // TODO: Card 클릭 이벤트
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun ScheduleScreenPreview() {
    BongBaekTheme {
        ScheduleScreen(
            uiState = ScheduleState(
                scheduleLoadState = UiState.Success(
                    listOf(
                        ScheduleEvent(
                            eventId = "1",
                            hostInfo = ScheduleHostInfo(
                                hostName = "공승준",
                                hostNickname = "초록승준",
                            ),
                            eventInfo = ScheduleEventInfo(
                                eventCategory = EventType.WEDDING,
                                relationship = RelationType.FRIEND,
                                cost = 10000,
                                eventDate = LocalDate.of(2025, 3, 11),
                            ),
                        ),
                        ScheduleEvent(
                            eventId = "2",
                            hostInfo = ScheduleHostInfo(
                                hostName = "김종명",
                                hostNickname = "봉준호",
                            ),
                            eventInfo = ScheduleEventInfo(
                                eventCategory = EventType.FIRST_BD,
                                relationship = RelationType.NEIGHBOR,
                                cost = 10000,
                                eventDate = LocalDate.of(2025, 2, 11),
                            ),
                        ),
                        ScheduleEvent(
                            eventId = "3",
                            hostInfo = ScheduleHostInfo(
                                hostName = "김헤정",
                                hostNickname = "메정",
                            ),
                            eventInfo = ScheduleEventInfo(
                                eventCategory = EventType.BIRTHDAY,
                                relationship = RelationType.ALUMNI,
                                cost = 10000,
                                eventDate = LocalDate.of(2025, 1, 11),
                            ),
                        ),
                        ScheduleEvent(
                            eventId = "4",
                            hostInfo = ScheduleHostInfo(
                                hostName = "공승준",
                                hostNickname = "초록승준",
                            ),
                            eventInfo = ScheduleEventInfo(
                                eventCategory = EventType.WEDDING,
                                relationship = RelationType.FRIEND,
                                cost = 10000,
                                eventDate = LocalDate.of(2025, 6, 11),
                            ),
                        ),
                        ScheduleEvent(
                            eventId = "5",
                            hostInfo = ScheduleHostInfo(
                                hostName = "김종명",
                                hostNickname = "봉준호",
                            ),
                            eventInfo = ScheduleEventInfo(
                                eventCategory = EventType.FIRST_BD,
                                relationship = RelationType.NEIGHBOR,
                                cost = 10000,
                                eventDate = LocalDate.of(2025, 8, 11),
                            ),
                        ),
                        ScheduleEvent(
                            eventId = "6",
                            hostInfo = ScheduleHostInfo(
                                hostName = "김헤정",
                                hostNickname = "메정",
                            ),
                            eventInfo = ScheduleEventInfo(
                                eventCategory = EventType.BIRTHDAY,
                                relationship = RelationType.ALUMNI,
                                cost = 10000,
                                eventDate = LocalDate.of(2025, 8, 11),
                            ),
                        ),
                    ),
                ),
            ),
            onCategoryClick = {},
            onBackClick = {},
            modifier = Modifier,
        )
    }
}