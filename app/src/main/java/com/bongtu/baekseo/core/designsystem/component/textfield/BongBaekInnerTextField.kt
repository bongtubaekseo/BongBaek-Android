package com.bongtu.baekseo.core.designsystem.component.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

/**
 * Inner Text Field
 * 텍스트 입력을 위한 기본 텍스트 필드 컴포넌트입니다.
 * 다른 TextField UI 컴포넌트의 입력 영역으로 공통 사용됩니다.
 *
 * @param text 입력 텍스트 값
 * @param onTextChange 텍스트 값 변경 시 호출되는 콜백
 * @param textColor 입력 텍스트 색상
 * @param textStyle 입력 텍스트 스타일
 * @param placeholder 입력 텍스트가 비어 있을 때 표시되는 힌트 텍스트
 * @param placeholderColor 힌트 텍스트 색상
 * @param isSingleLine single / multi line
 * @param isReadOnly 읽기 전용 여부
 * @param isEnabled 활성화 여부
 * @param suffix
 */
@Composable
fun BongBaekInnerTextField(
    text: String,
    onTextChange: (String) -> Unit,
    textColor: Color,
    textStyle: TextStyle,
    placeholder: String,
    placeholderColor: Color,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isReadOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    isSingleLine: Boolean = true,
    cursorColor: Color = BongBaekTheme.colors.white,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    suffix: (@Composable (() -> Unit))? = null,
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier,
        enabled = isEnabled,
        readOnly = isReadOnly,
        textStyle = textStyle.copy(color = textColor),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = isSingleLine,
        cursorBrush = SolidColor(cursorColor),
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (text.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = textStyle,
                            color = placeholderColor,
                        )
                    }
                    innerTextField()
                }

                suffix?.invoke()
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun BongBaekInnerTextFieldPreview() {
    BongBaekTheme {
        BongBaekInnerTextField(
            text = "",
            onTextChange = {},
            textColor = BongBaekTheme.colors.white,
            textStyle = BongBaekTheme.typography.body1Medium16,
            placeholder = "placeholder",
            placeholderColor = BongBaekTheme.colors.gray400,
        )
    }
}