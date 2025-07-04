package com.bongtu.baekseo.core.designsystem.component.dialog

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bongtu.baekseo.R.drawable.ic_caution
import com.bongtu.baekseo.R.string.date_picker_cancel
import com.bongtu.baekseo.R.string.date_picker_description_birth
import com.bongtu.baekseo.R.string.date_picker_description_date
import com.bongtu.baekseo.R.string.date_picker_error
import com.bongtu.baekseo.R.string.date_picker_ok
import com.bongtu.baekseo.R.string.date_picker_placeholder
import com.bongtu.baekseo.R.string.date_picker_title
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.DatePickerFormat
import com.bongtu.baekseo.core.util.isValidDate
import com.bongtu.baekseo.core.util.noRippleClickable

/**
 * Bong baek date picker
 *
 * 날짜를 선택할 때 사용하는 dialog
 *
 * @param descriptionId - dialog 설명
 * @param value - date textfield 입력값
 * @param onValueChange - date textfield 입력값 변경
 * @param onDismissRequest - dialog 닫기
 * @param onConfirmClick - ok 버튼 클릭 이벤트
 */

private const val DATE_INPUT_MAX_LENGTH = 8

@Composable
fun BongBaekDatePickerDialog(
    @StringRes descriptionId: Int,
    value: String,
    onValueChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val hasInput = value.isNotEmpty()
    val isValid = isValidDate(value)

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(
                modifier = modifier
                    .wrapContentSize()
                    .background(color = BongBaekTheme.colors.gray750)
                    .padding(20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(id = descriptionId),
                        style = BongBaekTheme.typography.captionRegular12,
                        color = BongBaekTheme.colors.gray300,
                    )

                    Spacer(modifier = Modifier.size(2.dp))

                    Text(
                        text = stringResource(id = date_picker_title),
                        style = BongBaekTheme.typography.titleSemiBold20,
                        color = BongBaekTheme.colors.white,
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    BongBaekDatePickerTextField(
                        value = value,
                        onValueChange = onValueChange,
                    )
                }

                Spacer(modifier = Modifier.size(10.dp))

                AnimatedVisibility(
                    visible = hasInput && !isValid,
                    enter = EnterTransition.None,
                    exit = ExitTransition.None,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = ic_caution),
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = BongBaekTheme.colors.secondaryRed,
                        )

                        Spacer(modifier = Modifier.size(2.dp))

                        Text(
                            text = stringResource(id = date_picker_error),
                            style = BongBaekTheme.typography.captionRegular12,
                            color = BongBaekTheme.colors.secondaryRed,
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.End)
                        .padding(top = 4.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 7.5.dp, vertical = 8.dp)
                            .noRippleClickable(onDismissRequest),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(id = date_picker_cancel),
                            style = BongBaekTheme.typography.body2Regular16,
                            color = BongBaekTheme.colors.gray300,
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 21.5.dp, vertical = 8.dp)
                            .noRippleClickable {
                                if (isValid) {
                                    onDismissRequest()
                                    // TODO: ok 클릭 시 이벤트 구현
                                    onConfirmClick()
                                }
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(id = date_picker_ok),
                            style = BongBaekTheme.typography.body2Regular16,
                            color = if (isValid) BongBaekTheme.colors.white else BongBaekTheme.colors.gray300,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BongBaekDatePickerTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (it.length <= DATE_INPUT_MAX_LENGTH) {
                onValueChange(it)
            }
        },
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = BongBaekTheme.colors.lineNormal,
                shape = RoundedCornerShape(10.dp),
            ),
        placeholder = {
            Text(
                text = stringResource(id = date_picker_placeholder),
                style = BongBaekTheme.typography.body1Medium16,
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = BongBaekTheme.colors.gray800,
            unfocusedContainerColor = BongBaekTheme.colors.gray800,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = Color.Transparent,
            focusedPlaceholderColor = BongBaekTheme.colors.gray500,
        ),
        textStyle = TextStyle(
            color = BongBaekTheme.colors.white,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = DatePickerFormat(),
    )
}

@Preview
@Composable
private fun BongBaekDatePickerTextFieldPreview() {
    BongBaekDatePickerTextField(
        value = "",
        onValueChange = {},
    )
}

@Preview
@Composable
private fun BongBaekDatePickerDialogBirthPreview() {
    var date by remember { mutableStateOf("") }

    BongBaekTheme {
        BongBaekDatePickerDialog(
            descriptionId = date_picker_description_birth,
            value = date,
            onValueChange = {
                date = it
            },
            onDismissRequest = {},
            onConfirmClick = {},
        )
    }
}

@Preview
@Composable
private fun BongBaekDatePickerDialogDatePreview() {
    var date by remember { mutableStateOf("") }

    BongBaekTheme {
        BongBaekDatePickerDialog(
            descriptionId = date_picker_description_date,
            value = date,
            onValueChange = {
                date = it
            },
            onDismissRequest = {},
            onConfirmClick = {},
        )
    }
}
