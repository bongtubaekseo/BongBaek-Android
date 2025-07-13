package com.bongtu.baekseo.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_calendar
import com.bongtu.baekseo.R.drawable.ic_person
import com.bongtu.baekseo.R.string.birth_text_field_label
import com.bongtu.baekseo.R.string.birth_text_field_placeholder
import com.bongtu.baekseo.R.string.button_start_service
import com.bongtu.baekseo.R.string.name_text_field_error_length
import com.bongtu.baekseo.R.string.name_text_field_error_special_character
import com.bongtu.baekseo.R.string.name_text_field_label
import com.bongtu.baekseo.R.string.name_text_field_placeholder
import com.bongtu.baekseo.R.string.onboarding_button_income_down
import com.bongtu.baekseo.R.string.onboarding_button_income_up
import com.bongtu.baekseo.R.string.onboarding_income
import com.bongtu.baekseo.R.string.onboarding_income_question
import com.bongtu.baekseo.R.string.topbar_profile_setting
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.common.type.DatePickerDialogType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.component.dialog.BongBaekDatePickerDialog
import com.bongtu.baekseo.core.designsystem.component.textfield.LabelTextField
import com.bongtu.baekseo.core.designsystem.component.textfield.TextFieldValidateResult
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.DateTextFieldFormat
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.presentation.onboarding.OnBoardingContract.OnBoardingSideEffect.NavigateToHome
import com.bongtu.baekseo.presentation.onboarding.component.OnBoardingButton
import com.bongtu.baekseo.presentation.onboarding.component.OnBoardingSwitch

@Composable
fun OnBoardingSettingScreen(
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
                }
            }
    }

    var name by remember { mutableStateOf("") }
    var birth by remember { mutableStateOf("") }
    var dialogBirth by remember { mutableStateOf("") }
    var validateResult: TextFieldValidateResult by remember {
        mutableStateOf(
            TextFieldValidateResult.Default
        )
    }
    var isDatePickerDialogVisible by remember { mutableStateOf(false) }
    var switchChecked by remember { mutableStateOf(false) }
    val buttonEnabled = remember(name, validateResult, birth) {
        validateResult == TextFieldValidateResult.Default && birth.isNotEmpty() && name.isNotEmpty()
    }
    var incomeSelected by remember { mutableStateOf(true) }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900),
    ) {
        BongBaekTopBar(
            title = stringResource(id = topbar_profile_setting),
            topBarType = TopBarType.TEXT_ONLY_START,
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
                    text = name,
                    placeholder = stringResource(id = name_text_field_placeholder),
                    modifier = Modifier,
                    validateResult = validateResult,
                    onTextChange = {
                        name = it
                        validateResult = TextFieldValidateResult.Default
                        viewModel.updateName(newName = it)
                    },
                    onInputDone = {
                        validateResult = if (name.length < 2 || name.length >= 10)
                            TextFieldValidateResult.Error(
                                errorMessage = context.getString(
                                    name_text_field_error_length
                                )
                            )
                        else if (name.contains(Regex("[^a-zA-Z0-9가-힣]"))) // TODO: Regex 객체 관리
                            TextFieldValidateResult.Error(
                                errorMessage = context.getString(
                                    name_text_field_error_special_character
                                )
                            )
                        else
                            TextFieldValidateResult.Default
                    },
                    isClearButtonEnabled = false,
                )

                LabelTextField(
                    labelImage = ic_calendar,
                    labelName = stringResource(id = birth_text_field_label),
                    text = birth,
                    placeholder = stringResource(id = birth_text_field_placeholder),
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .noRippleClickable {
                            dialogBirth = birth
                            isDatePickerDialogVisible = true
                        },
                    onTextChange = {
                        viewModel.updateBirth(newBirth = it)
                    },
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
                    OnBoardingSwitch(
                        checked = switchChecked,
                        onCheckedChange = {
                            switchChecked = it
                        },
                    )
                }

                AnimatedVisibility(
                    visible = switchChecked,
                    modifier = Modifier.padding(top = 30.dp),
                    enter = EnterTransition.None,
                    exit = ExitTransition.None,
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
                            color = BongBaekTheme.colors.white,
                        )

                        OnBoardingButton(
                            title = stringResource(id = onboarding_button_income_down),
                            selected = incomeSelected,
                            onClick = {
                                if (!incomeSelected) incomeSelected = true
                                viewModel.updateIncome("200만원 미만")
                            },
                            modifier = Modifier.padding(top = 16.dp),
                        )

                        OnBoardingButton(
                            title = stringResource(id = onboarding_button_income_up),
                            selected = !incomeSelected,
                            onClick = {
                                if (incomeSelected) incomeSelected = false
                                viewModel.updateIncome("200만원 이상")
                            },
                            modifier = Modifier.padding(top = 8.dp),
                        )
                    }
                }
            }

            BongBaekButton(
                title = stringResource(id = button_start_service),
                onClick = {
                    viewModel.setUsername(name)
                },
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
                value = dialogBirth,
                onValueChange = {
                    dialogBirth = it
                },
                onDismissRequest = {
                    isDatePickerDialogVisible = false
                },
                onConfirmClick = {
                    isDatePickerDialogVisible = false
                    birth = dialogBirth
                },
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
        )
    }
}