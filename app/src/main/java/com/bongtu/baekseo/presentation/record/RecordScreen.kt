package com.bongtu.baekseo.presentation.record

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_delete
import com.bongtu.baekseo.R.drawable.ic_plus
import com.bongtu.baekseo.R.string.record_top_bar_title
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.presentation.record.RecordContract.RecordState
import com.bongtu.baekseo.presentation.record.component.AttendTypeTab
import com.bongtu.baekseo.presentation.record.component.EventCategoryBar
import com.bongtu.baekseo.presentation.record.component.RecordListContent
import com.bongtu.baekseo.presentation.record.type.AttendType
import com.bongtu.baekseo.presentation.record.type.EventCategoryType

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
        modifier = modifier,
    )
}

@Composable
private fun RecordScreen(
    uiState: RecordState,
    onTabClick: (AttendType) -> Unit,
    onCategoryClick: (EventCategoryType) -> Unit,
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
                        modifier = Modifier
                            .padding(14.dp)
                            .size(20.dp),
                        tint = BongBaekTheme.colors.gray400,
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
                // 빈 상태 화면
            }

            is UiState.Failure -> {
                // 에러 상태 화면
            }

            is UiState.Loading -> {
                //로딩 상태 화면
            }

            is UiState.Success -> {
                RecordListContent(
                    recordEventList = uiState.recordLoadState.data,
                    onCardClick = {},
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
            uiState = RecordState(),
            onTabClick = {},
            onCategoryClick = {},
        )
    }
}