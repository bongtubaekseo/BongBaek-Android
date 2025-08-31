package com.bongtu.baekseo.presentation.mypage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_calendar
import com.bongtu.baekseo.R.drawable.ic_person
import com.bongtu.baekseo.R.drawable.ic_select
import com.bongtu.baekseo.R.string.birth_text_field_label
import com.bongtu.baekseo.R.string.birth_text_field_placeholder
import com.bongtu.baekseo.R.string.name_text_field_label
import com.bongtu.baekseo.R.string.name_text_field_placeholder
import com.bongtu.baekseo.R.string.onboarding_button_income_down
import com.bongtu.baekseo.R.string.onboarding_button_income_up
import com.bongtu.baekseo.R.string.onboarding_income
import com.bongtu.baekseo.R.string.onboarding_income_question
import com.bongtu.baekseo.R.string.profile_edit_button
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
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.presentation.mypage.MyPageContract.MyPageUiState

@Composable
fun ProfileEditRoute(
    navigateUp: () -> Unit,
    viewModel: MyPageViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isEditButtonEnabled = remember(uiState.userName, uiState.userBirth) {
        viewModel.updateButtonState()
    }

    ProfileEditScreen(
        uiState = uiState,
        isEditButtonEnabled = isEditButtonEnabled,
        navigateUp = navigateUp,
        onUserNameTextChange = viewModel::updateUserName,
        onUserBirthTextChange = viewModel::updateUserBirth,
        onDialogBirthTextChange = viewModel::updateDialogBirth,
        onIncomeButtonClick = viewModel::updateUserIncome,
        modifier = modifier,
    )
}

@Composable
private fun ProfileEditScreen(
    uiState: MyPageUiState,
    isEditButtonEnabled: Boolean,
    navigateUp: () -> Unit,
    onUserNameTextChange: (String) -> Unit,
    onUserBirthTextChange: (String) -> Unit,
    onDialogBirthTextChange: (String) -> Unit,
    onIncomeButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isDatePickerDialogVisible by remember { mutableStateOf(false) }
    var switchChecked by remember { mutableStateOf(false) }
    var incomeSelected by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900)
            .systemBarsPadding(),
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
                    text = uiState.userName,
                    placeholder = stringResource(id = name_text_field_placeholder),
                    errorText = uiState.nameError,
                    isRequired = true,
                    onTextChange = onUserNameTextChange,
                    isClearButtonEnabled = false,
                )

                LabelTextField(
                    labelImage = ic_calendar,
                    labelName = stringResource(id = birth_text_field_label),
                    text = uiState.userBirth,
                    placeholder = stringResource(id = birth_text_field_placeholder),
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .noRippleClickable {
                            isDatePickerDialogVisible = true
                        },
                    onTextChange = onUserBirthTextChange,
                    isEditable = false,
                    isRequired = true,
                    isClearButtonEnabled = false,
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
                    modifier = Modifier.padding(top = 30.dp),
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

                        ProfileEditButton(
                            title = stringResource(id = onboarding_button_income_down),
                            selected = incomeSelected,
                            onClick = {
                                if (!incomeSelected) incomeSelected = true
                                onIncomeButtonClick(IncomeType.UNDER_200.label)
                            },
                            modifier = Modifier.padding(top = 16.dp),
                        )

                        ProfileEditButton(
                            title = stringResource(id = onboarding_button_income_up),
                            selected = !incomeSelected,
                            onClick = {
                                if (incomeSelected) incomeSelected = false
                                onIncomeButtonClick(IncomeType.OVER_200.label)
                            },
                            modifier = Modifier.padding(top = 8.dp),
                        )
                    }
                }
            }

            BongBaekButton(
                title = stringResource(id = profile_edit_button),
                onClick = { navigateUp() /* TODO: 프로필 수정 */ },
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 36.dp,
                    ),
                enabled = isEditButtonEnabled,
            )
        }

        if (isDatePickerDialogVisible) {
            BongBaekDatePickerDialog(
                datePickerDialogType = DatePickerDialogType.BIRTH,
                value = uiState.dialogBirth,
                onValueChange = onDialogBirthTextChange,
                onDismissRequest = {
                    isDatePickerDialogVisible = false
                    onDialogBirthTextChange("")
                },
                onConfirmClick = {
                    isDatePickerDialogVisible = false
                    onUserBirthTextChange(uiState.dialogBirth)
                    onDialogBirthTextChange("")
                },
            )
        }
    }
}

@Composable
private fun ProfileEditButton(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        if (selected) BongBaekTheme.colors.primaryBackground else BongBaekTheme.colors.gray750
    val borderColor =
        if (selected) BongBaekTheme.colors.primaryNormal else BongBaekTheme.colors.gray100

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = backgroundColor)
            .noRippleClickable(onClick)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(
                start = 18.dp,
                end = 12.dp,
            )
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = BongBaekTheme.typography.body2Regular14,
            color = BongBaekTheme.colors.gray100,
        )

        Spacer(modifier = Modifier.weight(1f))

        if (selected) {
            Icon(
                imageVector = ImageVector.vectorResource(id = ic_select),
                contentDescription = null,
                tint = BongBaekTheme.colors.primaryNormal,
            )
        }
    }
}

@Preview
@Composable
private fun ProfileEditScreenPreview() {
    BongBaekTheme {
        ProfileEditScreen(
            uiState = MyPageUiState(),
            isEditButtonEnabled = false,
            navigateUp = {},
            onUserNameTextChange = {},
            onUserBirthTextChange = {},
            onDialogBirthTextChange = {},
            onIncomeButtonClick = {},
        )
    }
}