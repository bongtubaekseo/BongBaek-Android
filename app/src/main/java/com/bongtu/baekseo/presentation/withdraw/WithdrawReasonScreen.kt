package com.bongtu.baekseo.presentation.withdraw

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.string.withdraw_button_title
import com.bongtu.baekseo.R.string.withdraw_information
import com.bongtu.baekseo.R.string.withdraw_title
import com.bongtu.baekseo.R.string.withdraw_topbar
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.common.type.DialogType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.common.type.WithdrawType
import com.bongtu.baekseo.core.compositionlocal.safeDrawingWithBottomNavBar
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.component.dialog.BongBaekDialog
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.clearFocus
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.presentation.withdraw.WithdrawContract.WithdrawUiState
import com.bongtu.baekseo.presentation.withdraw.component.WithdrawReasonSelector

@Composable
fun WithdrawRoute(
    navigateToUp: () -> Unit,
    onRestartApp: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WithdrawViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val buttonEnabled = remember(uiState.reasonType, uiState.etcReason) {
        viewModel.updateButtonState()
    }
    val pageIndex = remember { mutableStateOf(0) }

    when (pageIndex.value) {
        0 -> WithdrawReasonScreen(
            uiState = uiState,
            buttonEnabled = buttonEnabled,
            navigateToUp = navigateToUp,
            onReasonSelect = viewModel::updateReasonType,
            modifier = modifier,
            onEtcValueChange = viewModel::updateEtcReason,
            onConfirmClick = {
                pageIndex.value = 1
                viewModel.withdraw()
            },
        )

        1 -> WithdrawQuitScreen(
            onRestartApp = onRestartApp,
            modifier = modifier,
        )
    }
}

@Composable
private fun WithdrawReasonScreen(
    uiState: WithdrawUiState,
    buttonEnabled: Boolean,
    navigateToUp: () -> Unit,
    onReasonSelect: (WithdrawType) -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
    onEtcValueChange: (String) -> Unit = {},
) {
    var isWithdrawDialogVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var etcFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900)
            .windowInsetsPadding(WindowInsets.safeDrawingWithBottomNavBar)
            .clearFocus(focusManager),
    ) {
        BongBaekTopBar(
            title = stringResource(withdraw_topbar),
            topBarType = TopBarType.LEADING_ICON,
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp)
                        .noRippleClickable(navigateToUp),
                    tint = BongBaekTheme.colors.white,
                )
            },
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                AnimatedVisibility(
                    visible = !(uiState.reasonType == WithdrawType.OTHER && etcFocused),
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = stringResource(id = withdraw_title),
                            style = BongBaekTheme.typography.headBold24,
                            color = BongBaekTheme.colors.gray100,
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = stringResource(id = withdraw_information),
                            style = BongBaekTheme.typography.body2Regular14,
                            color = BongBaekTheme.colors.gray400,
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                WithdrawReasonSelector(
                    value = uiState.etcReason,
                    onValueChange = onEtcValueChange,
                    selectedReason = uiState.reasonType,
                    onReasonSelect = onReasonSelect,
                    etcFocused = etcFocused,
                    onEtcFocusChange = { etcFocused = it },
                )
            }

            BongBaekButton(
                title = stringResource(id = withdraw_button_title),
                onClick = {
                    isWithdrawDialogVisible = true
                },
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 36.dp,
                    ),
                enabled = buttonEnabled,
            )
        }
        if (isWithdrawDialogVisible) {
            BongBaekDialog(
                dialogType = DialogType.WITHDRAW,
                onDismissRequest = {
                    isWithdrawDialogVisible = false
                },
                onConfirmClick = onConfirmClick,
            )
        }
    }
}

@Preview
@Composable
private fun WithdrawReasonScreenPreview() {
    BongBaekTheme {
        WithdrawReasonScreen(
            uiState = WithdrawUiState(
                reasonType = WithdrawType.INCONVENIENT,
            ),
            buttonEnabled = true,
            navigateToUp = {},
            onReasonSelect = {},
            onConfirmClick = {},
        )
    }
}