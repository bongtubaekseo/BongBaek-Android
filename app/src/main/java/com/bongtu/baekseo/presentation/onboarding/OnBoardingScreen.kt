package com.bongtu.baekseo.presentation.onboarding

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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.drawable.ic_calendar
import com.bongtu.baekseo.R.drawable.ic_person
import com.bongtu.baekseo.R.string.birth_text_field_label
import com.bongtu.baekseo.R.string.birth_text_field_placeholder
import com.bongtu.baekseo.R.string.button_start_service
import com.bongtu.baekseo.R.string.name_text_field_label
import com.bongtu.baekseo.R.string.name_text_field_placeholder
import com.bongtu.baekseo.R.string.onboarding_button_income_down
import com.bongtu.baekseo.R.string.onboarding_button_income_up
import com.bongtu.baekseo.R.string.onboarding_income
import com.bongtu.baekseo.R.string.onboarding_income_question
import com.bongtu.baekseo.R.string.topbar_profile_setting
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.common.type.DatePickerDialogType
import com.bongtu.baekseo.core.common.type.IncomeType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.compositionlocal.safeDrawingWithBottomNavBar
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.component.dialog.BongBaekDatePickerDialog
import com.bongtu.baekseo.core.designsystem.component.switch.BongBaekSwitch
import com.bongtu.baekseo.core.designsystem.component.textfield.LabelTextField
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.DateFormatter
import com.bongtu.baekseo.core.util.DateTextFieldFormat
import com.bongtu.baekseo.core.util.clearFocus
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.presentation.onboarding.OnBoardingContract.OnBoardingSideEffect.NavigateToHome
import com.bongtu.baekseo.presentation.onboarding.OnBoardingContract.OnBoardingSideEffect.NavigateToLogin
import com.bongtu.baekseo.presentation.onboarding.OnBoardingContract.OnBoardingUiState
import com.bongtu.baekseo.presentation.onboarding.component.OnBoardingButton

@Composable
fun OnBoardingRoute(
    navigateToUp: () -> Unit,
    navigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OnBoardingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToHome -> navigateToHome()
                    is NavigateToLogin -> navigateToUp()
                }
            }
    }

    OnBoardingScreen(
        uiState = uiState,
        onNameChange = viewModel::updateName,
        onBirthChange = viewModel::updateBirth,
        onDialogBirthChange = viewModel::updateDialogBirth,
        onIncomeChange = viewModel::updateIncome,
        onBackClick = viewModel::navigateToLogin,
        onStartClick = viewModel::postSignUp,
        checkButtonEnabled = viewModel::updateButtonState,
        modifier = modifier,
    )
}

@Composable
fun OnBoardingScreen(
    uiState: OnBoardingUiState,
    onNameChange: (String) -> Unit,
    onBirthChange: (String) -> Unit,
    onDialogBirthChange: (String) -> Unit,
    onIncomeChange: (IncomeType) -> Unit,
    onBackClick: () -> Unit,
    onStartClick: () -> Unit,
    checkButtonEnabled: () -> Boolean,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val density = LocalDensity.current

    val isImeVisible = WindowInsets.ime.getBottom(density) > 0

    LaunchedEffect(isImeVisible) {
        if (!isImeVisible) {
            focusManager.clearFocus()
        }
    }

    var isDatePickerDialogVisible by remember { mutableStateOf(false) }
    var switchChecked by remember { mutableStateOf(false) }

    val isIncomeSelectionInvalid = uiState.income == IncomeType.NONE && switchChecked
    val isIncomeUnderDimmed =
        uiState.income != IncomeType.NONE && uiState.income != IncomeType.UNDER_200
    val isIncomeOverDimmed =
        uiState.income != IncomeType.NONE && uiState.income != IncomeType.OVER_200

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.bgDisplayPrimary)
            .windowInsetsPadding(WindowInsets.safeDrawingWithBottomNavBar)
            .clearFocus(focusManager),
    ) {
        BongBaekTopBar(
            title = stringResource(id = topbar_profile_setting),
            topBarType = TopBarType.LEADING_ICON,
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp)
                        .noRippleClickable(onBackClick),
                    tint = BongBaekTheme.colors.iconInteractiveDefault,
                )
            }
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
                    text = uiState.name,
                    placeholder = stringResource(id = name_text_field_placeholder),
                    modifier = Modifier,
                    errorText = uiState.nameError,
                    isRequired = true,
                    onTextChange = onNameChange,
                    isClearButtonEnabled = true,
                )

                LabelTextField(
                    labelImage = ic_calendar,
                    labelName = stringResource(id = birth_text_field_label),
                    text = uiState.birth,
                    placeholder = stringResource(id = birth_text_field_placeholder),
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .noRippleClickable {
                            focusManager.clearFocus()
                            onDialogBirthChange(
                                DateFormatter.formatLocalDateToNumeric(
                                    uiState.birth,
                                )
                            )
                            isDatePickerDialogVisible = true
                        },
                    onTextChange = onBirthChange,
                    isRequired = true,
                    isEditable = false,
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
                            vertical = 18.dp,
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
                        onCheckedChange = {
                            switchChecked = it
                            onIncomeChange(IncomeType.NONE)
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

                        OnBoardingButton(
                            title = stringResource(id = onboarding_button_income_down),
                            isSelected = uiState.income == IncomeType.UNDER_200,
                            isDimmed = isIncomeUnderDimmed,
                            onClick = { onIncomeChange(IncomeType.UNDER_200) },
                            modifier = Modifier.padding(top = 16.dp),
                        )

                        OnBoardingButton(
                            title = stringResource(id = onboarding_button_income_up),
                            isSelected = uiState.income == IncomeType.OVER_200,
                            isDimmed = isIncomeOverDimmed,
                            onClick = { onIncomeChange(IncomeType.OVER_200) },
                            modifier = Modifier.padding(top = 8.dp),
                        )
                    }
                }
            }

            BongBaekButton(
                title = stringResource(id = button_start_service),
                onClick = {
                    if (uiState.loadState !is UiState.Loading)
                        onStartClick()
                },
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 36.dp,
                    ),
                enabled = checkButtonEnabled() && !isIncomeSelectionInvalid,
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
                onConfirmClick = onBirthChange,
            )
        }
    }
}

@Preview
@Composable
private fun OnBoardingSettingScreenPreview() {
    BongBaekTheme {
        OnBoardingScreen(
            uiState = OnBoardingUiState(),
            onNameChange = {},
            onBirthChange = {},
            onDialogBirthChange = {},
            onIncomeChange = {},
            onBackClick = {},
            onStartClick = {},
            checkButtonEnabled = { true },
        )
    }
}
