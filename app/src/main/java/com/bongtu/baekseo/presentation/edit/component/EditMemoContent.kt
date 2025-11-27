package com.bongtu.baekseo.presentation.edit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.string.edit_memo_text_field_placeholder
import com.bongtu.baekseo.R.string.edit_memo_text_length
import com.bongtu.baekseo.R.string.edit_memo_title
import com.bongtu.baekseo.core.designsystem.component.textfield.BongBaekInnerTextField
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.bringIntoView
import com.bongtu.baekseo.core.util.checkLength

private const val MEMO_RATIO = 280 / 92f
private const val MEMO_INPUT_MAX_LENGTH = 50

@Composable
fun EditMemoContent(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isEditable: Boolean = true,
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    val bongBaekColors = BongBaekTheme.colors
    val (borderColor, backgroundColor) = remember(isFocused) {
        if (isFocused) {
            bongBaekColors.statusFocused to bongBaekColors.bgFieldSecondary
        } else {
            bongBaekColors.transparent to bongBaekColors.bgFieldSecondary
        }
    }

    Column(
        modifier = modifier
            .bringIntoViewRequester(bringIntoViewRequester),
    ) {
        Text(
            text = stringResource(edit_memo_title),
            color = BongBaekTheme.colors.txtDisplayPrimary,
            style = BongBaekTheme.typography.titleSemiBold18,
            modifier = Modifier
                .padding(bottom = 10.dp),
        )

        Column(
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
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp,
                ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(MEMO_RATIO),
                contentAlignment = Alignment.TopStart,
            ) {
                BongBaekInnerTextField(
                    text = text,
                    onTextChange = {
                        if (it.checkLength() <= MEMO_INPUT_MAX_LENGTH) {
                            onTextChange(it)
                        }
                    },
                    textColor = BongBaekTheme.colors.txtFieldValue,
                    textStyle = BongBaekTheme.typography.body2Regular16,
                    placeholder = stringResource(edit_memo_text_field_placeholder),
                    placeholderColor = BongBaekTheme.colors.txtFieldPlaceholder,
                    placeholderStyle = BongBaekTheme.typography.body2Regular16,
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
                        .onFocusEvent { focusState ->
                            isFocused = focusState.isFocused
                            bringIntoViewRequester.bringIntoView(coroutineScope, isFocused)
                        }
                        .matchParentSize(),
                )
            }

            Text(
                text = stringResource(id = edit_memo_text_length, text.checkLength()),
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.End),
                style = BongBaekTheme.typography.captionRegular12,
                color = bongBaekColors.txtDisplayTertiary,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview
@Composable
private fun EditMemoContentPreview() {
    BongBaekTheme {
        EditMemoContent(
            text = "",
            onTextChange = {},
            isEditable = true,
        )
    }
}
