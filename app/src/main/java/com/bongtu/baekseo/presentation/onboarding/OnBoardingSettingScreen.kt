package com.bongtu.baekseo.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.common.type.DatePickerDialogType
import com.bongtu.baekseo.core.common.type.IncomeType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.component.dialog.BongBaekDatePickerDialog
import com.bongtu.baekseo.core.designsystem.component.switch.BongBaekSwitch
import com.bongtu.baekseo.core.designsystem.component.textfield.LabelTextField
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.DateTextFieldFormat
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.presentation.onboarding.OnBoardingContract.OnBoardingSideEffect.NavigateToHome
import com.bongtu.baekseo.presentation.onboarding.component.OnBoardingButton

@Composable
fun OnBoardingSettingScreen(
    navigateToHome: () -> Unit,
    navigateToUp: () -> Unit,
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
                }
            }
    }

    var isDatePickerDialogVisible by remember { mutableStateOf(false) }
    var switchChecked by remember { mutableStateOf(false) }
    val buttonEnabled = remember(uiState.name, uiState.birth) {
        viewModel.updateButtonState()
    }
    var incomeSelected by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900)
            .systemBarsPadding(),
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
                        .noRippleClickable(onClick = navigateToUp),
                    tint = BongBaekTheme.colors.white,
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
                    onTextChange = viewModel::updateName,
                    isClearButtonEnabled = false,
                )

                LabelTextField(
                    labelImage = ic_calendar,
                    labelName = stringResource(id = birth_text_field_label),
                    text = uiState.birth,
                    placeholder = stringResource(id = birth_text_field_placeholder),
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .noRippleClickable {
                            viewModel.updateDialogBirth(uiState.birth)
                            isDatePickerDialogVisible = true
                        },
                    onTextChange = viewModel::updateBirth,
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
                        .background(color = BongBaekTheme.colors.gray750)
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
                        color = BongBaekTheme.colors.white,
                    )
                    BongBaekSwitch(
                        checked = switchChecked,
                        onCheckedChange = {
                            switchChecked = it
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
                            .background(color = BongBaekTheme.colors.gray750)
                            .padding(20.dp),
                    ) {
                        Text(
                            text = stringResource(id = onboarding_income_question),
                            style = BongBaekTheme.typography.body1Medium16,
                            color = BongBaekTheme.colors.gray100,
                        )

                        OnBoardingButton(
                            title = stringResource(id = onboarding_button_income_down),
                            selected = incomeSelected,
                            onClick = {
                                if (!incomeSelected) incomeSelected = true
                                viewModel.updateIncome(IncomeType.UNDER_200.label)
                            },
                            modifier = Modifier.padding(top = 16.dp),
                        )

                        OnBoardingButton(
                            title = stringResource(id = onboarding_button_income_up),
                            selected = !incomeSelected,
                            onClick = {
                                if (incomeSelected) incomeSelected = false
                                viewModel.updateIncome(IncomeType.OVER_200.label)
                            },
                            modifier = Modifier.padding(top = 8.dp),
                        )
                    }
                }
            }

            BongBaekButton(
                title = stringResource(id = button_start_service),
                onClick = viewModel::postSignUp,
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 38.dp,
                    ),
                enabled = buttonEnabled,
            )
        }

        if (isDatePickerDialogVisible) {
            BongBaekDatePickerDialog(
                datePickerDialogType = DatePickerDialogType.BIRTH,
                value = uiState.dialogBirth,
                onValueChange = {
                    viewModel.updateDialogBirth(newDialogBirth = it)
                },
                onDismissRequest = {
                    isDatePickerDialogVisible = false
                    viewModel.updateDialogBirth("")
                },
                onConfirmClick = viewModel::updateBirth,
            )
        }
    }
}

@Preview
@Composable
private fun OnBoardingSettingScreenPreview() {
    BongBaekTheme {
        OnBoardingSettingScreen(
            viewModel = hiltViewModel(),
            navigateToHome = {},
            navigateToUp = {},
        )
    }
}