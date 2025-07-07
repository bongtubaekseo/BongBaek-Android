package com.bongtu.baekseo.presentation.record

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_delete
import com.bongtu.baekseo.R.drawable.ic_plus
import com.bongtu.baekseo.R.drawable.ic_record_empty
import com.bongtu.baekseo.R.string.record_empty_button
import com.bongtu.baekseo.R.string.record_empty_description
import com.bongtu.baekseo.R.string.record_empty_title
import com.bongtu.baekseo.R.string.record_top_bar_title
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.data.model.RecordEvent
import com.bongtu.baekseo.presentation.record.RecordContract.RecordState
import com.bongtu.baekseo.presentation.record.component.AttendTypeTab
import com.bongtu.baekseo.presentation.record.component.EventCategoryBar
import com.bongtu.baekseo.presentation.record.component.RecordListContent
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
        onDeleteButtonClick = viewModel::updateIsDeleting,
        modifier = modifier,
    )
}

@Composable
private fun RecordScreen(
    uiState: RecordState,
    onTabClick: (AttendType) -> Unit,
    onCategoryClick: (EventCategoryType) -> Unit,
    onDeleteButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900),
    ) {
        BongBaekTopBar(
            title = stringResource(record_top_bar_title),
            topBarType = TopBarType.TRAILING_ICON,
            trailingIcon = {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(ic_plus),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(14.dp)
                            .size(20.dp),
                        tint = BongBaekTheme.colors.gray400,
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(ic_delete),
                        contentDescription = null,
                        tint = BongBaekTheme.colors.gray400,
                        modifier = Modifier
                            .padding(14.dp)
                            .size(20.dp)
                            .noRippleClickable {
                                if (uiState.recordLoadState is UiState.Success) {
                                    onDeleteButtonClick()
                                }
                            },
                    )
                }
            },
            modifier = Modifier
                .padding(vertical = 8.dp),
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
                RecordEmptyContent(modifier = Modifier.padding(top = 5.dp))
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

@Composable
private fun RecordEmptyContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 60.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(record_empty_title),
            color = BongBaekTheme.colors.white,
            style = BongBaekTheme.typography.headBold24,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )
        Text(
            text = stringResource(record_empty_description),
            color = BongBaekTheme.colors.gray400,
            style = BongBaekTheme.typography.body2Regular14,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
        )

        Image(
            painter = painterResource(ic_record_empty),
            contentDescription = null,
        )

        Button(
            modifier = Modifier.padding(top = 32.dp),
            onClick = { },
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = BongBaekTheme.colors.primaryNormal,
                contentColor = BongBaekTheme.colors.white,
            ),
            contentPadding = PaddingValues(
                horizontal = 30.dp,
                vertical = 8.dp,
            ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(record_empty_button),
                    style = BongBaekTheme.typography.titleSemiBold16,
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
                isDeleting = true,
            ),
            onTabClick = {},
            onCategoryClick = {},
            onDeleteButtonClick = {},
            modifier = Modifier,
        )
    }
}