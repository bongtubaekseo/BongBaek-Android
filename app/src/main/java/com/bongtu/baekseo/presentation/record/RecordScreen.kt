package com.bongtu.baekseo.presentation.record

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
import com.bongtu.baekseo.presentation.record.RecordContract.RecordState

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
        modifier = modifier,
    )
}

@Composable
private fun RecordScreen(
    uiState: RecordState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
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

            }
        }
    }
}

@Preview
@Composable
private fun RecordScreenPreview() {
    BongBaekTheme {
        RecordRoute()
    }
}