package com.bongtu.baekseo.presentation.setting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.drawable.ic_calendar
import com.bongtu.baekseo.R.drawable.ic_person
import com.bongtu.baekseo.R.string.birth_text_field_label
import com.bongtu.baekseo.R.string.birth_text_field_placeholder
import com.bongtu.baekseo.R.string.name_text_field_label
import com.bongtu.baekseo.R.string.name_text_field_placeholder
import com.bongtu.baekseo.R.string.onboarding_button_income_down
import com.bongtu.baekseo.R.string.onboarding_button_income_up
import com.bongtu.baekseo.R.string.onboarding_income
import com.bongtu.baekseo.R.string.onboarding_income_question
import com.bongtu.baekseo.R.string.profile_edit_button
import com.bongtu.baekseo.R.string.topbar_profile_edit
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.common.type.DatePickerDialogType
import com.bongtu.baekseo.core.common.type.IncomeType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekIncomeButton
import com.bongtu.baekseo.core.designsystem.component.dialog.BongBaekDatePickerDialog
import com.bongtu.baekseo.core.designsystem.component.switch.BongBaekSwitch
import com.bongtu.baekseo.core.designsystem.component.textfield.LabelTextField
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.DateFormatter
import com.bongtu.baekseo.core.util.DateTextFieldFormat
import com.bongtu.baekseo.core.util.clearFocus
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.presentation.setting.SettingContract.SettingSideEffect.ProfileEditSideEffect
import com.bongtu.baekseo.presentation.setting.SettingContract.SettingSideEffect.ProfileEditSideEffect.NavigateToSetting
import com.bongtu.baekseo.presentation.setting.SettingContract.SettingUiState
import kotlinx.coroutines.flow.filterIsInstance

@Composable
fun ProfileEditRoute(
    navigateToUp: () -> Unit,
    viewModel: SettingViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .filterIsInstance<ProfileEditSideEffect>()
            .collect { sideEffect ->
                when (sideEffect) {
                    NavigateToSetting -> navigateToUp()
                }
            }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearProfileEditState()
        }
    }

    ProfileEditScreen(
        uiState = uiState,
        isEditButtonEnabled = uiState.isEditButtonEnabled,
        navigateToUp = navigateToUp,
        onUserNameChange = viewModel::updateUserName,
        onUserBirthChange = viewModel::updateUserBirth,
        onDialogBirthChange = viewModel::updateDialogBirth,
        onUserIncomeChange = viewModel::updateUserIncome,
        onIncomeButtonChange = viewModel::updateIncomeButtonState,
        onEditClick = viewModel::patchUserProfile,
        modifier = modifier,
    )
}

@Composable
private fun ProfileEditScreen(
    uiState: SettingUiState,
    isEditButtonEnabled: Boolean,
    navigateToUp: () -> Unit,
    onUserNameChange: (String) -> Unit,
    onUserBirthChange: (String) -> Unit,
    onDialogBirthChange: (String) -> Unit,
    onUserIncomeChange: (IncomeType) -> Unit,
    onIncomeButtonChange: (IncomeType) -> Boolean?,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isDatePickerDialogVisible by remember { mutableStateOf(false) }
    var switchChecked by remember { mutableStateOf(uiState.userIncome != IncomeType.NONE) }
    val isIncomeSelectionInvalid = uiState.userIncome == IncomeType.NONE && switchChecked
    val focusManager = LocalFocusManager.current
    val density = LocalDensity.current

    val isImeVisible = WindowInsets.ime.getBottom(density) > 0

    LaunchedEffect(isImeVisible) {
        if (!isImeVisible) {
            focusManager.clearFocus()
        }
    }

    Column(
        modifier = modifier
            .background(color = BongBaekTheme.colors.bgDisplayPrimary)
            .fillMaxSize()
            .statusBarsPadding()
            .clearFocus(focusManager),
    ) {
        BongBaekTopBar(
            title = stringResource(id = topbar_profile_edit),
            topBarType = TopBarType.LEADING_ICON,
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp)
                        .noRippleClickable(onClick = navigateToUp),
                    tint = BongBaekTheme.colors.iconInteractiveDefault,
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
                LabelTextField(
                    labelImage = ic_person,
                    labelName = stringResource(id = name_text_field_label),
                    text = uiState.userName,
                    placeholder = stringResource(id = name_text_field_placeholder),
                    errorText = uiState.nameError,
                    isRequired = true,
                    onTextChange = onUserNameChange,
                    isClearButtonEnabled = true,
                )

                LabelTextField(
                    labelImage = ic_calendar,
                    labelName = stringResource(id = birth_text_field_label),
                    text = uiState.userBirth,
                    placeholder = stringResource(id = birth_text_field_placeholder),
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .noRippleClickable {
                            onDialogBirthChange(
                                DateFormatter.formatLocalDateToNumeric(
                                    uiState.userBirth,
                                )
                            )
                            isDatePickerDialogVisible = true
                            focusManager.clearFocus()
                        },
                    onTextChange = onUserBirthChange,
                    isEditable = false,
                    isRequired = true,
                    isClearButtonEnabled = false,
                    visualTransformation = DateTextFieldFormat(),
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(color = BongBaekTheme.colors.bgDisplayCard)
                        .padding(
                            horizontal = 20.dp,
                            vertical = 16.dp,
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = onboarding_income),
                        style = BongBaekTheme.typography.body1Medium16,
                        color = BongBaekTheme.colors.txtDisplaySecondary,
                    )
                    BongBaekSwitch(
                        checked = switchChecked,
                        onCheckedChange = { isChecked ->
                            switchChecked = isChecked
                            onUserIncomeChange(IncomeType.NONE)
                            focusManager.clearFocus()
                        },
                    )
                }

                AnimatedVisibility(
                    visible = switchChecked,
                    modifier = Modifier.padding(top = 8.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(color = BongBaekTheme.colors.bgDisplayCard)
                            .padding(20.dp),
                    ) {
                        Text(
                            text = stringResource(id = onboarding_income_question),
                            style = BongBaekTheme.typography.titleSemiBold18,
                            color = BongBaekTheme.colors.txtDisplaySecondary,
                        )

                        BongBaekIncomeButton(
                            title = stringResource(id = onboarding_button_income_down),
                            isSelected = onIncomeButtonChange(IncomeType.UNDER_200),
                            onClick = { onUserIncomeChange(IncomeType.UNDER_200) },
                            modifier = Modifier.padding(top = 16.dp),
                        )

                        BongBaekIncomeButton(
                            title = stringResource(id = onboarding_button_income_up),
                            isSelected = onIncomeButtonChange(IncomeType.OVER_200),
                            onClick = { onUserIncomeChange(IncomeType.OVER_200) },
                            modifier = Modifier.padding(top = 8.dp),
                        )
                    }
                }
            }

            BongBaekButton(
                title = stringResource(id = profile_edit_button),
                onClick = {
                    if (uiState.loadState !is UiState.Loading)
                        onEditClick()
                },
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 36.dp,
                    ),
                enabled = isEditButtonEnabled && !isIncomeSelectionInvalid,
            )
        }

        if (isDatePickerDialogVisible) {
            BongBaekDatePickerDialog(
                datePickerDialogType = DatePickerDialogType.BIRTH,
                value = uiState.dialogBirth,
                onValueChange = onDialogBirthChange,
                onDismissRequest = {
                    isDatePickerDialogVisible = false
                    onDialogBirthChange("")
                },
                onConfirmClick = onUserBirthChange,
            )
        }
    }
}

@Preview
@Composable
private fun ProfileEditScreenPreview() {
    BongBaekTheme {
        ProfileEditScreen(
            uiState = SettingUiState(),
            isEditButtonEnabled = false,
            navigateToUp = {},
            onUserNameChange = {},
            onUserBirthChange = {},
            onDialogBirthChange = {},
            onUserIncomeChange = {},
            onIncomeButtonChange = { true },
            onEditClick = {},
        )
    }
}
