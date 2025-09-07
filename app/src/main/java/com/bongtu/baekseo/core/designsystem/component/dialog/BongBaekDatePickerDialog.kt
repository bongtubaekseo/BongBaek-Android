package com.bongtu.baekseo.core.designsystem.component.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bongtu.baekseo.R.drawable.ic_caution
import com.bongtu.baekseo.R.string.date_picker_cancel
import com.bongtu.baekseo.R.string.date_picker_ok
import com.bongtu.baekseo.R.string.date_picker_placeholder
import com.bongtu.baekseo.R.string.date_picker_title
import com.bongtu.baekseo.core.common.type.DatePickerDialogType
import com.bongtu.baekseo.core.designsystem.component.textfield.BongBaekInnerTextField
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.DateFormatter
import com.bongtu.baekseo.core.util.DatePickerFormat
import com.bongtu.baekseo.core.util.DateValidator.validateDate
import com.bongtu.baekseo.core.util.noRippleClickable

private const val DATE_INPUT_MAX_LENGTH = 8

/**
 * Bong baek date picker
 *
 * 날짜를 선택할 때 사용하는 dialog
 *
 * @param datePickerDialogType - date picker 타입 (DatePickerDialogType.BIRTH, DatePickerDialogType.DATE)
 * @param value - date textfield 입력값
 * @param onValueChange - date textfield 입력값 변경
 * @param onDismissRequest - dialog 닫기
 * @param onConfirmClick - ok 버튼 클릭 이벤트 (호출시 dissmiss 수행, "yyyy-MM-dd" 형식 문자열 반환)
 */
@Composable
fun BongBaekDatePickerDialog(
    datePickerDialogType: DatePickerDialogType,
    value: String,
    onValueChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dateError = validateDate(
        input = value,
        type = datePickerDialogType,
    )
    val isValid = dateError.isEmpty()
    val bongBaekColors = BongBaekTheme.colors
    val confirmTextColor = remember(isValid) {
        if (isValid) bongBaekColors.white else bongBaekColors.gray300
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
        ),
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = BongBaekTheme.colors.gray750)
                    .padding(
                        start = 20.dp,
                        top = 20.dp,
                        end = 20.dp,
                        bottom = 12.dp,
                    ),
            ) {
                Text(
                    text = stringResource(id = datePickerDialogType.descriptionId),
                    style = BongBaekTheme.typography.captionRegular12,
                    color = BongBaekTheme.colors.gray300,
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = stringResource(id = date_picker_title),
                    style = BongBaekTheme.typography.titleSemiBold20,
                    color = BongBaekTheme.colors.white,
                )

                Spacer(modifier = Modifier.height(10.dp))

                BongBaekDatePickerTextField(
                    value = value,
                    onValueChange = onValueChange,
                )

                AnimatedVisibility(
                    visible = value.isNotEmpty() && !isValid,
                    modifier = Modifier.padding(top = 6.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = ic_caution),
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = BongBaekTheme.colors.secondaryRed,
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = dateError,
                            style = BongBaekTheme.typography.captionRegular12,
                            color = BongBaekTheme.colors.secondaryRed,
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = date_picker_cancel),
                        modifier = Modifier
                            .padding(8.dp)
                            .noRippleClickable(onDismissRequest),
                        style = BongBaekTheme.typography.body2Regular16,
                        color = BongBaekTheme.colors.gray300,
                    )

                    Text(
                        text = stringResource(id = date_picker_ok),
                        modifier = Modifier
                            .padding(
                                horizontal = 22.dp,
                                vertical = 8.dp,
                            )
                            .noRippleClickable {
                                if (isValid) {
                                    onConfirmClick(DateFormatter.formatNumericToLocalDate(value))
                                    onDismissRequest()
                                }
                            },
                        style = BongBaekTheme.typography.body2Regular16,
                        color = confirmTextColor,
                    )
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
    BongBaekInnerTextField(
        text = value,
        onTextChange = {
            if (it.length <= DATE_INPUT_MAX_LENGTH) {
                onValueChange(it)
            }
        },
        textColor = BongBaekTheme.colors.white,
        textStyle = BongBaekTheme.typography.body1Medium16,
        placeholder = stringResource(id = date_picker_placeholder),
        placeholderColor = BongBaekTheme.colors.gray500,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = DatePickerFormat(),
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = BongBaekTheme.colors.lineNormal,
                shape = RoundedCornerShape(10.dp),
            )
            .background(
                color = BongBaekTheme.colors.gray800,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(
                horizontal = 14.dp,
                vertical = 10.dp,
            ),
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
            datePickerDialogType = DatePickerDialogType.BIRTH,
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
            datePickerDialogType = DatePickerDialogType.DATE,
            value = date,
            onValueChange = {
                date = it
            },
            onDismissRequest = {},
            onConfirmClick = {},
        )
    }
}
