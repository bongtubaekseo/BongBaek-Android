package com.bongtu.baekseo.core.designsystem.component.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_cancel
import com.bongtu.baekseo.R.drawable.ic_caution
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.TextFieldValidator.validateName
import com.bongtu.baekseo.core.util.noRippleClickable

/**
 *  Rounded Text Field
 *
 *  @param text 입력값
 *  @param placeholder 힌트
 *  @param errorText error 문구
 *  @param isEditable write / read
 *  @param onClick TextField 영역 전체 클릭
 *  @param onTextChange 입력값 변경
 *  @param onInputDone 입력 완료
 */
@Composable
fun RoundedBoxTextField(
    text: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    errorText: String = "",
    isEditable: Boolean = true,
    onFocusChange: (Boolean) -> Unit = {},
    onClick: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
    onInputDone: (() -> Unit)? = null,
    roundedCornerShape: RoundedCornerShape = RoundedCornerShape(size = 10.dp),
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current

    val isFilled = text.isNotEmpty()
    val isError = errorText.isNotEmpty()

    val bongBaekColors = BongBaekTheme.colors
    val borderColor = remember(isError, isFocused) {
        when {
            isError -> bongBaekColors.statusError
            isFocused -> bongBaekColors.statusFocused
            else -> bongBaekColors.borderFieldDefault
        }
    }

    val textColor = remember(isError) {
        if (isError) bongBaekColors.statusError else bongBaekColors.txtFieldValue
    }

    LaunchedEffect(isFocused) {
        onFocusChange(isFocused)
    }

    Column(
        modifier = modifier
            .noRippleClickable(onClick = onClick),
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = BongBaekTheme.colors.bgFieldPrimary,
                    shape = roundedCornerShape,
                )
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = roundedCornerShape,
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
            BongBaekInnerTextField(
                text = text,
                onTextChange = onTextChange,
                textColor = textColor,
                textStyle = BongBaekTheme.typography.body1Medium16,
                placeholder = placeholder,
                placeholderColor = BongBaekTheme.colors.txtFieldPlaceholder,
                placeholderStyle = BongBaekTheme.typography.body2Regular16,
                isReadOnly = !isEditable,
                isEnabled = isEditable,
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
                visualTransformation = visualTransformation,
                suffix = {
                    if (isFilled && isFocused) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = ic_cancel),
                            contentDescription = null,
                            tint = BongBaekTheme.colors.iconDisabledPrimary,
                            modifier = Modifier.noRippleClickable { onTextChange("") },
                        )
                    }
                },
            )
        }

        AnimatedVisibility(
            visible = isError,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
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
}

@Preview(showBackground = true)
@Composable
private fun RoundedBoxTextFieldPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.black),
        verticalArrangement = Arrangement.Center,
    ) {
        var text by remember { mutableStateOf("봉봉") }

        RoundedBoxTextField(
            text = text,
            placeholder = "placeholder",
            onTextChange = { text = it },
            errorText = validateName(text),
            onInputDone = {},
            isEditable = false,
        )
    }
}
