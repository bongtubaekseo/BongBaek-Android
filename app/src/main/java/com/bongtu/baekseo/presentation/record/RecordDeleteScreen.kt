package com.bongtu.baekseo.presentation.record

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.string.record_top_bar_delete_title
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.presentation.record.RecordContract.RecordState
import com.bongtu.baekseo.presentation.record.component.AttendTypeTab
import com.bongtu.baekseo.presentation.record.component.EventCategoryBar
import com.bongtu.baekseo.presentation.record.component.RecordListContent

@Composable
fun RecordDeleteRoute(
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    viewModel: RecordViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RecordDeleteScreen(
        uiState = uiState,
        onBackButtonClick = onBackClick,
        onDeleteButtonClick = onDeleteClick,
        modifier = modifier,
    )
}

@Composable
private fun RecordDeleteScreen(
    uiState: RecordState,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900),
    ) {
        BongBaekTopBar(
            title = stringResource(record_top_bar_delete_title),
            topBarType = TopBarType.LEADING_ICON,
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp)
                        .noRippleClickable(onClick = onBackButtonClick),
                    tint = BongBaekTheme.colors.white,
                )
            }
        )

        AttendTypeTab(
            selectedTab = uiState.attendType,
            isEnabled = false,
        )

        EventCategoryBar(
            selectedCategory = uiState.eventCategoryType,
            isEnabled = false,
        )


        RecordListContent(
            recordEventList = (uiState.recordLoadState as UiState.Success).data,
            isDeleting = true,
        )
    }
}