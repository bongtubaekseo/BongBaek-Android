package com.bongtu.baekseo.presentation.edit.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_cancel
import com.bongtu.baekseo.R.drawable.ic_caution
import com.bongtu.baekseo.R.drawable.ic_coin
import com.bongtu.baekseo.R.string.edit_cost_text_field_placeholder
import com.bongtu.baekseo.R.string.edit_cost_title
import com.bongtu.baekseo.R.string.kr_won
import com.bongtu.baekseo.R.string.label_text_field_required_text
import com.bongtu.baekseo.core.designsystem.component.textfield.BongBaekInnerTextField
import com.bongtu.baekseo.core.designsystem.component.textfield.TextFieldValidateResult
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.CostTextFieldFormat
import com.bongtu.baekseo.core.util.noRippleClickable

@Composable
fun EditCostLabelTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onInputDone: (() -> Unit)? = null,
    validateResult: TextFieldValidateResult = TextFieldValidateResult.Default,
    visualTransformation: VisualTransformation = CostTextFieldFormat(),
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
                imageVector = ImageVector.vectorResource(id = ic_coin),
                contentDescription = null,
                tint = BongBaekTheme.colors.gray400,
                modifier = Modifier
                    .height(16.dp)
                    .padding(end = 6.dp),
            )

            Text(
                text = stringResource(id = edit_cost_title),
                style = BongBaekTheme.typography.body1Medium14,
                color = BongBaekTheme.colors.gray100,
            )

            Text(
                text = stringResource(label_text_field_required_text),
                style = BongBaekTheme.typography.body1Medium14,
                color = BongBaekTheme.colors.primaryNormal,
                modifier = Modifier
                    .padding(start = 2.dp),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                BongBaekInnerTextField(
                    text = text,
                    onTextChange = { cost ->
                        val digitsOnly = cost.filter { it.isDigit() }
                        val number = digitsOnly.toLongOrNull()?.coerceIn(0L, 99_999_999L)
                        onTextChange(number.toString())
                    },
                    textColor = textColor,
                    placeholder = stringResource(edit_cost_text_field_placeholder),
                    placeholderColor = BongBaekTheme.colors.gray400,
                    textStyle = BongBaekTheme.typography.body2Regular16,
                    interactionSource = interactionSource,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Done,
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            onInputDone?.invoke()
                        },
                    ),
                    suffix = {
                        if (isFilled && isFocused) {
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
                        .padding(top = 12.dp, bottom = 6.dp),
                    thickness = 1.dp,
                    color = dividerColor,
                )
            }

            Text(
                text = stringResource(kr_won),
                style = BongBaekTheme.typography.body2Regular16,
                color = BongBaekTheme.colors.white,
                modifier = Modifier
                    .padding(start = 16.dp),
            )
        }

        AnimatedVisibility(
            visible = isError,
            enter = EnterTransition.None,
            exit = ExitTransition.None,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = ic_caution),
                    contentDescription = null,
                    modifier = Modifier
                        .size(14.dp),
                    tint = BongBaekTheme.colors.secondaryRed,
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