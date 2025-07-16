package com.bongtu.baekseo.presentation.edit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.string.edit_memo_text_field_placeholder
import com.bongtu.baekseo.R.string.edit_memo_title
import com.bongtu.baekseo.core.designsystem.component.textfield.BongBaekInnerTextField
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

private const val MEMO_RATIO = 320 / 152f
private const val MEMO_INPUT_MAX_LENGTH = 50

@Composable
fun EditMemoContent(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isEditable: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusManager = LocalFocusManager.current

    val bongBaekColors = BongBaekTheme.colors
    val (borderColor, backgroundColor) = remember(isFocused) {
        if (isFocused) {
            bongBaekColors.primaryNormal to bongBaekColors.gray750
        } else {
            bongBaekColors.transparent to bongBaekColors.gray800
        }
    }

    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(edit_memo_title),
            color = BongBaekTheme.colors.white,
            style = BongBaekTheme.typography.titleSemiBold18,
            modifier = Modifier
                .padding(bottom = 10.dp),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(10.dp),
                )
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(10.dp),
                )
                .aspectRatio(MEMO_RATIO),
            contentAlignment = Alignment.TopStart,
        ) {
            BongBaekInnerTextField(
                text = text,
                onTextChange = {
                    if (it.length <= MEMO_INPUT_MAX_LENGTH) {
                        onTextChange(it)
                    }
                },
                textColor = BongBaekTheme.colors.white,
                placeholder = stringResource(edit_memo_text_field_placeholder),
                placeholderColor = BongBaekTheme.colors.gray500,
                textStyle = BongBaekTheme.typography.body2Regular16,
                interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    },
                ),
                isReadOnly = !isEditable,
                isEnabled = isEditable,
                isSingleLine = false,
                modifier = Modifier
                    .padding(
                        vertical = 16.dp,
                        horizontal = 20.dp
                    ),
            )
        }
    }
}