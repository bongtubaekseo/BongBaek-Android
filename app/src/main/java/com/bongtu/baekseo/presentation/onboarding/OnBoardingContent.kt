package com.bongtu.baekseo.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_calendar
import com.bongtu.baekseo.R.drawable.ic_person
import com.bongtu.baekseo.R.string.onboarding_income
import com.bongtu.baekseo.R.string.onboarding_income_question
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
import com.bongtu.baekseo.presentation.onboarding.component.OnBoardingButton
import com.bongtu.baekseo.presentation.onboarding.component.OnBoardingSwitch

@Composable
fun OnBoardingContent(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var birth by remember { mutableStateOf("") }
    var dialogBirth by remember { mutableStateOf("") }
    var validateResult: TextFieldValidateResult by remember {
        mutableStateOf(
            TextFieldValidateResult.Default
        )
    }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var switchChecked by remember { mutableStateOf(false) }
    val buttonEnabled by remember { mutableStateOf(false) }
    var incomeSelected by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900),
    ) {
        BongBaekTopBar(
            title = "프로필 설정",
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
                    labelName = "이름",
                    text = name,
                    placeholder = "이름을 입력해주세요",
                    modifier = modifier,
                    validateResult = validateResult,
                    onTextChange = {
                        name = it
                        validateResult = TextFieldValidateResult.Default
                    },
                    onInputDone = {
                        validateResult = if (name.length < 2 || name.length >= 10)
                            TextFieldValidateResult.Error("2자 이상 10자 이내로 입력해주세요")
                        else if (name.contains(Regex("[^a-zA-Z0-9가-힣]")))
                            TextFieldValidateResult.Error("특수문자는 기입할 수 없어요")
                        else
                            TextFieldValidateResult.Default
                    },
                    isClearButtonEnabled = false,
                    paddingValues = PaddingValues(0.dp),
                )

                LabelTextField(
                    labelImage = ic_calendar,
                    labelName = "생년월일",
                    text = birth,
                    placeholder = "생년월일을 입력해주세요",
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .noRippleClickable {
                            dialogBirth = birth
                            showDatePickerDialog = true
                        },
                    isEditable = false,
                    isClearButtonEnabled = false,
                    paddingValues = PaddingValues(0.dp),
                    visualTransformation = DateTextFieldFormat(),
                )

                if (showDatePickerDialog) {
                    BongBaekDatePickerDialog(
                        datePickerDialogType = DatePickerDialogType.BIRTH,
                        value = dialogBirth,
                        onValueChange = {
                            dialogBirth = it
                        },
                        onDismissRequest = {
                            showDatePickerDialog = false
                        },
                        onConfirmClick = {
                            showDatePickerDialog = false
                            birth = dialogBirth
                        }
                    )
                }

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
                        }
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
                            .padding(20.dp)
                    ) {
                        Text(
                            text = stringResource(id = onboarding_income_question),
                            style = BongBaekTheme.typography.body1Medium16,
                            color = BongBaekTheme.colors.white,
                        )

                        OnBoardingButton(
                            title = "월 200만원 미만",
                            selected = incomeSelected,
                            onClick = {
                                if (!incomeSelected) incomeSelected = true
                            },
                            modifier = Modifier.padding(top = 16.dp),
                        )

                        OnBoardingButton(
                            title = "월 200만원 이상",
                            selected = !incomeSelected,
                            onClick = {
                                if (incomeSelected) incomeSelected = false
                            },
                            modifier = Modifier.padding(top = 8.dp),
                        )
                    }
                }
            }

            BongBaekButton(
                title = "봉투백서 시작하기",
                onClick = { },
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 38.dp,
                    ),
                enabled = buttonEnabled,
            )
        }
    }
}

@Preview
@Composable
private fun OnBoardingContentPreview() {
    BongBaekTheme {
        OnBoardingContent()
    }
}