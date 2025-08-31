package com.bongtu.baekseo.presentation.mypage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.bongtu.baekseo.R.drawable.ic_arrow_back
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
import com.bongtu.baekseo.core.compositionlocal.safeDrawingWithBottomNavBar
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

    LaunchedEffect(Unit) {
        viewModel.initEditProfileState()
    }

    ProfileEditScreen(
        uiState = uiState,
        isEditButtonEnabled = uiState.isEditButtonEnabled,
        navigateUp = navigateUp,
        onUserNameChange = viewModel::updateUserName,
        onUserBirthChange = viewModel::updateUserBirth,
        onDialogBirthChange = viewModel::updateDialogBirth,
        onUserIncomeChange = viewModel::updateUserIncome,
        modifier = modifier,
    )
}

@Composable
private fun ProfileEditScreen(
    uiState: MyPageUiState,
    isEditButtonEnabled: Boolean,
    navigateUp: () -> Unit,
    onUserNameChange: (String) -> Unit,
    onUserBirthChange: (String) -> Unit,
    onDialogBirthChange: (String) -> Unit,
    onUserIncomeChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isDatePickerDialogVisible by remember { mutableStateOf(false) }
    val switchChecked = uiState.userIncome != IncomeType.NONE.label
    val incomeSelected = uiState.userIncome != IncomeType.OVER_200.label

    var lastOnIncome by rememberSaveable { mutableStateOf(IncomeType.UNDER_200.label) }
    LaunchedEffect(uiState.userIncome) {
        if (uiState.userIncome != IncomeType.NONE.label) {
            lastOnIncome = uiState.userIncome
        }
    }

    Column(
        modifier = modifier
            .background(color = BongBaekTheme.colors.gray900)
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawingWithBottomNavBar),
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
                        .noRippleClickable(onClick = navigateUp),
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
                LabelTextField(
                    labelImage = ic_person,
                    labelName = stringResource(id = name_text_field_label),
                    text = uiState.userName,
                    placeholder = stringResource(id = name_text_field_placeholder),
                    errorText = uiState.nameError,
                    isRequired = true,
                    onTextChange = onUserNameChange,
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
                    onTextChange = onUserBirthChange,
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
                        onCheckedChange = { isChecked ->
                            onUserIncomeChange(
                                if (isChecked) lastOnIncome else IncomeType.NONE.label,
                            )
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
                            onClick = { onUserIncomeChange(IncomeType.UNDER_200.label) },
                            modifier = Modifier.padding(top = 16.dp),
                        )

                        ProfileEditButton(
                            title = stringResource(id = onboarding_button_income_up),
                            selected = !incomeSelected,
                            onClick = { onUserIncomeChange(IncomeType.OVER_200.label) },
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
                onValueChange = onDialogBirthChange,
                onDismissRequest = {
                    isDatePickerDialogVisible = false
                    onDialogBirthChange("")
                },
                onConfirmClick = {
                    isDatePickerDialogVisible = false
                    onUserBirthChange(uiState.dialogBirth)
                    onDialogBirthChange("")
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
            onUserNameChange = {},
            onUserBirthChange = {},
            onDialogBirthChange = {},
            onUserIncomeChange = {},
        )
    }
}