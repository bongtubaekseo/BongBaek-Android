package com.bongtu.baekseo.core.designsystem.component.textfield

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_cancel
import com.bongtu.baekseo.R.drawable.ic_caution
import com.bongtu.baekseo.R.drawable.ic_nickname
import com.bongtu.baekseo.R.string.label_text_field_required_text
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.TextFieldValidator.validateName
import com.bongtu.baekseo.core.util.bringIntoViewOnFocus
import com.bongtu.baekseo.core.util.noRippleClickable

/**
 *  Label Text Field
 *
 *  @param labelName 라벨 이름
 *  @param labelImage 라벨 이미지 리소스
 *  @param text 입력값
 *  @param placeholder 힌트
 *  @param errorText error 문구
 *  @param isRequired 필수 값 * 표시 여부
 *  @param isEditable write / read
 *  @param isDimmed Dimmed 라벨, 이름 Dimmed 색상 적용 여부
 *  @param onTextChange 입력값 변경
 *  @param onInputDone 입력 완료
 *  @param keyboardType KeyboardType
 *  @param isClearButtonEnabled 텍스트 clear 버튼 활성화 여부
 *  @param extraBottomPadding 필드 하단에 추가로 확보할 여백 높이(dp)
 *  @param suffix 텍스트 필드 오른쪽에 추가로 표시할 내용
 */
@Composable
fun LabelTextField(
    labelName: String,
    @DrawableRes labelImage: Int,
    text: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    errorText: String = "",
    isRequired: Boolean = false,
    isEditable: Boolean = true,
    isDimmed: Boolean = false,
    onTextChange: (String) -> Unit = {},
    onInputDone: (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    isClearButtonEnabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    extraBottomPadding: Dp? = null,
    suffix: @Composable (() -> Unit)? = null,
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val isFilled = text.isNotEmpty()
    val isError = errorText.isNotEmpty()

    val bongBaekColors = BongBaekTheme.colors

    val dividerColor = when {
        isError -> bongBaekColors.statusError
        isFocused -> bongBaekColors.statusFocused
        isFilled -> bongBaekColors.borderFieldFilled
        else -> bongBaekColors.borderFieldDefault
    }

    val textColor = when {
        isError -> bongBaekColors.statusError
        isDimmed -> bongBaekColors.txtDisplayTertiary
        else -> bongBaekColors.txtFieldValue
    }

    val textStyle =
        if (isFocused) BongBaekTheme.typography.body2Regular16
        else BongBaekTheme.typography.body1Medium16

    val labelTextColor =
        if (isDimmed) bongBaekColors.txtDisplayTertiary else bongBaekColors.txtDisplaySecondary

    val requiredTextColor =
        if (isDimmed) bongBaekColors.txtDisplayTertiary else bongBaekColors.statusFocused

    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.padding(bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = labelImage),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(20.dp),
            )

            Text(
                text = labelName,
                style = BongBaekTheme.typography.body1Medium14,
                color = labelTextColor,
            )

            if (isRequired) {
                Text(
                    text = stringResource(label_text_field_required_text),
                    style = BongBaekTheme.typography.body1Medium14,
                    color = requiredTextColor,
                    modifier = Modifier
                        .padding(start = 2.dp),
                )
            }
        }

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .then(
                        extraBottomPadding?.let { padding ->
                            Modifier.bringIntoViewOnFocus(
                                isFocused = isFocused,
                                extraBottom = if (isError) 0.dp else padding
                            )
                        } ?: Modifier
                    )
            ) {
                BongBaekInnerTextField(
                    text = text,
                    onTextChange = onTextChange,
                    textColor = textColor,
                    textStyle = textStyle,
                    placeholder = placeholder,
                    placeholderColor = BongBaekTheme.colors.txtDisplayTertiary,
                    placeholderStyle = textStyle,
                    modifier = Modifier
                        .onFocusEvent { focusState ->
                            isFocused = focusState.isFocused
                        },
                    isReadOnly = !isEditable,
                    isEnabled = isEditable,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            onInputDone?.invoke()
                        },
                    ),
                    suffix = {
                        if (isFilled && isFocused && isClearButtonEnabled) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = ic_cancel),
                                contentDescription = null,
                                tint = BongBaekTheme.colors.iconDisabledPrimary,
                                modifier = Modifier.noRippleClickable { onTextChange("") },
                            )
                        }
                    },
                    visualTransformation = visualTransformation,
                )

                HorizontalDivider(
                    modifier = Modifier
                        .padding(top = 12.dp),
                    thickness = 1.dp,
                    color = dividerColor,
                )

                AnimatedVisibility(
                    visible = isError,
                ) {
                    Row(
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = ic_caution),
                            contentDescription = null,
                            tint = BongBaekTheme.colors.statusError,
                            modifier = Modifier
                                .size(14.dp),
                        )

                        Text(
                            text = errorText,
                            modifier = Modifier
                                .padding(start = 4.dp),
                            style = BongBaekTheme.typography.captionRegular12,
                            color = BongBaekTheme.colors.statusError,
                        )
                    }
                }
            }

            suffix?.invoke()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LabelTextFieldPreview() {
    BongBaekTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BongBaekTheme.colors.black),
            verticalArrangement = Arrangement.Center,
        ) {
            var text by remember { mutableStateOf("봉봉") }

            LabelTextField(
                labelImage = ic_nickname,
                labelName = "별명",
                text = text,
                errorText = validateName(text),
                placeholder = "placeholder",
                onTextChange = { text = it },
                onInputDone = {},
            )
            LabelTextField(
                labelImage = ic_nickname,
                labelName = "이름",
                text = text,
                errorText = validateName(text),
                placeholder = "이름을 입력해주세요",
                onTextChange = { text = it },
                onInputDone = {},
                isClearButtonEnabled = false,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoClearButtonLabelTextFieldPreview() {
    BongBaekTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = BongBaekTheme.colors.black),
            verticalArrangement = Arrangement.Center,
        ) {
            var text by remember { mutableStateOf("봉봉") }

            LabelTextField(
                labelImage = ic_nickname,
                labelName = "이름",
                text = text,
                errorText = validateName(text),
                placeholder = "이름을 입력해주세요",
                onTextChange = { text = it },
                onInputDone = {},
                isClearButtonEnabled = false,
            )
        }
    }
}
