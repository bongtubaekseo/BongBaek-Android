package com.bongtu.baekseo.core.designsystem.component.textfield

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_cancel
import com.bongtu.baekseo.R.drawable.ic_caution
import com.bongtu.baekseo.R.drawable.ic_nickname
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.R.string.label_text_field_required_text

/**
 *  Label Text Field
 *
 *  @param labelName 라벨 이름
 *  @param labelImage 라벨 이미지 리소스
 *  @param text 입력값
 *  @param placeholder 힌트
 *  @param isRequired 필수 값 * 표시 여부
 *  @param isEditable write / read
 *  @param onTextChange 입력값 변경
 *  @param onInputDone 입력 완료
 *  @param validateResult TextFieldValidateResult.Default / TextFieldValidateResult.Error
 *  @param isClearButtonEnabled 텍스트 clear 버튼 활성화 여부
 */
@Composable
fun LabelTextField(
    labelName: String,
    @DrawableRes labelImage: Int,
    text: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false,
    isEditable: Boolean = true,
    onTextChange: (String) -> Unit = {},
    onInputDone: (() -> Unit)? = null,
    validateResult: TextFieldValidateResult = TextFieldValidateResult.Default,
    isClearButtonEnabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current

    val isFilled = text.isNotEmpty()
    val isError = validateResult is TextFieldValidateResult.Error

    val bongBaekColors = BongBaekTheme.colors
    val dividerColor = remember(isError, isFocused, isFilled) {
        when {
            isError -> bongBaekColors.secondaryRed
            isFocused -> bongBaekColors.primaryNormal
            isFilled -> bongBaekColors.white
            else -> bongBaekColors.gray500
        }
    }

    val textColor = remember(isError) {
        if (isError) bongBaekColors.secondaryRed else bongBaekColors.white
    }

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
                tint = BongBaekTheme.colors.gray400,
                modifier = Modifier
                    .height(16.dp)
                    .padding(end = 6.dp),
            )
            Text(
                text = labelName,
                style = BongBaekTheme.typography.body1Medium14,
                color = BongBaekTheme.colors.gray100,
            )

            if (isRequired) {
                Text(
                    text = stringResource(label_text_field_required_text),
                    style = BongBaekTheme.typography.body1Medium14,
                    color = BongBaekTheme.colors.primaryNormal,
                    modifier = Modifier
                        .padding(start = 2.dp),
                )
            }
        }

        BongBaekInnerTextField(
            text = text,
            onTextChange = onTextChange,
            textColor = textColor,
            placeholder = placeholder,
            placeholderColor = BongBaekTheme.colors.gray400,
            isReadOnly = !isEditable,
            isEnabled = isEditable,
            textStyle = BongBaekTheme.typography.body2Regular16,
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(
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
                        tint = BongBaekTheme.colors.gray500,
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
                    tint = BongBaekTheme.colors.secondaryRed,
                    modifier = Modifier
                        .size(14.dp),
                )
                Text(
                    text = validateResult.message.orEmpty(),
                    modifier = Modifier
                        .padding(start = 4.dp),
                    style = BongBaekTheme.typography.captionRegular12,
                    color = BongBaekTheme.colors.secondaryRed,
                )
            }
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
            var validateResult: TextFieldValidateResult by remember {
                mutableStateOf(
                    TextFieldValidateResult.Default
                )
            }

            LabelTextField(
                labelImage = ic_nickname,
                labelName = "별명",
                text = text,
                placeholder = "placeholder",
                validateResult = validateResult,
                onTextChange = {
                    text = it
                    validateResult = TextFieldValidateResult.Default
                },
                onInputDone = {
                    validateResult = if (text.length < 2)
                        TextFieldValidateResult.Error("2자 이상 입력해주세요.")
                    else
                        TextFieldValidateResult.Default
                }
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
            var validateResult: TextFieldValidateResult by remember {
                mutableStateOf(
                    TextFieldValidateResult.Default
                )
            }

            LabelTextField(
                labelImage = ic_nickname,
                labelName = "이름",
                text = text,
                placeholder = "이름을 입력해주세요",
                validateResult = validateResult,
                onTextChange = {
                    text = it
                    validateResult = TextFieldValidateResult.Default
                },
                onInputDone = {
                    validateResult = if (text.length < 2)
                        TextFieldValidateResult.Error("2자 이상 입력해주세요.")
                    else
                        TextFieldValidateResult.Default
                },
                isClearButtonEnabled = false,
            )
        }
    }
}