package com.bongtu.baekseo.presentation.withdraw.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_check
import com.bongtu.baekseo.R.drawable.ic_withdraw_check
import com.bongtu.baekseo.R.drawable.ic_withdraw_check_gray
import com.bongtu.baekseo.R.string.input_text_field_placeholder
import com.bongtu.baekseo.R.string.withdraw_reason_account
import com.bongtu.baekseo.R.string.withdraw_reason_error
import com.bongtu.baekseo.R.string.withdraw_reason_etc
import com.bongtu.baekseo.R.string.withdraw_reason_etc_length
import com.bongtu.baekseo.R.string.withdraw_reason_privacy
import com.bongtu.baekseo.R.string.withdraw_reason_uncomfortable
import com.bongtu.baekseo.R.string.withdraw_reason_use
import com.bongtu.baekseo.core.common.type.WithdrawType
import com.bongtu.baekseo.core.designsystem.component.textfield.BongBaekInnerTextField
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.checkLength
import com.bongtu.baekseo.core.util.noRippleClickable

private const val ETC_INPUT_MAX_LENGTH = 50

@Composable
fun WithdrawReasonSelector(
    value: String,
    onValueChange: (String) -> Unit,
    selectedReason: WithdrawType?,
    onReasonSelect: (WithdrawType) -> Unit,
    modifier: Modifier = Modifier,
    etcFocused: Boolean = false,
    onEtcFocusChange: (Boolean) -> Unit = {},
) {
    val showOtherReasons = selectedReason != WithdrawType.OTHER || !etcFocused
    val withdrawReasons = remember {
        WithdrawType.entries.filter { it != WithdrawType.OTHER }
    }

    Column(
        modifier = modifier
            .background(
                color = BongBaekTheme.colors.bgDisplaySecondary,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(20.dp),
    ) {
        AnimatedVisibility(
            visible = showOtherReasons,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                withdrawReasons.forEach { item ->
                    WithdrawSelectorItem(
                        reason = item,
                        isSelected = if (selectedReason == null) null else selectedReason == item,
                        modifier = Modifier
                            .noRippleClickable { onReasonSelect(item) },
                    )
                }
            }
        }

        WithdrawSelectorItem(
            reason = WithdrawType.OTHER,
            isSelected = if (selectedReason == null) null else selectedReason == WithdrawType.OTHER,
            modifier = Modifier
                .then(
                    if (!etcFocused) Modifier.padding(top = 8.dp) else Modifier
                )
                .noRippleClickable { onReasonSelect(WithdrawType.OTHER) },
            etcFocused = etcFocused,
            value = value,
            onValueChange = onValueChange,
            onFocusChange = onEtcFocusChange,
        )
    }
}

@Composable
private fun WithdrawSelectorItem(
    reason: WithdrawType,
    isSelected: Boolean?,
    modifier: Modifier = Modifier,
    etcFocused: Boolean = false,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    onFocusChange: (Boolean) -> Unit = {},
) {
    val bongBaekColors = BongBaekTheme.colors
    val (iconRes, titleColor) = remember(isSelected) {
        when (isSelected) {
            true -> ic_check to bongBaekColors.txtInteractivePrimary
            false -> ic_withdraw_check_gray to bongBaekColors.txtInteractivePrimary
            else -> ic_withdraw_check to bongBaekColors.txtDisplayTertiary
        }
    }
    val titleRes = remember(reason) {
        when (reason) {
            WithdrawType.INCONVENIENT -> withdraw_reason_uncomfortable
            WithdrawType.PRIVACY_CONCERN -> withdraw_reason_privacy
            WithdrawType.RARELY_USED -> withdraw_reason_use
            WithdrawType.BUG_OR_ERROR -> withdraw_reason_error
            WithdrawType.NEW_ACCOUNT -> withdraw_reason_account
            WithdrawType.OTHER -> withdraw_reason_etc
        }
    }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (isSelected == true)
                        Modifier.border(
                            width = 1.dp,
                            color = bongBaekColors.borderStatusFocused,
                            shape = RoundedCornerShape(10.dp),
                        )
                    else Modifier
                )
                .background(
                    color = bongBaekColors.btnInteractiveInput,
                    shape = RoundedCornerShape(10.dp),
                )
                .padding(12.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Top),
                    tint = Color.Unspecified,
                )

                if (reason == WithdrawType.OTHER && isSelected == true) {
                    BongBaekInnerTextField(
                        text = value,
                        onTextChange = {
                            if (it.checkLength() <= ETC_INPUT_MAX_LENGTH)
                                onValueChange(it)
                        },
                        textColor = BongBaekTheme.colors.txtInteractivePrimary,
                        textStyle = BongBaekTheme.typography.body1Medium16,
                        placeholder = stringResource(id = input_text_field_placeholder),
                        placeholderColor = bongBaekColors.txtStatusDisabled,
                        modifier = Modifier
                            .weight(1f)
                            .onFocusChanged { onFocusChange(it.isFocused) },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() },
                        ),
                        isSingleLine = false,
                    )
                } else {
                    Text(
                        text = stringResource(id = titleRes),
                        style = BongBaekTheme.typography.body2Regular16,
                        color = titleColor,
                    )
                }
            }
            if (etcFocused) {
                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    text = stringResource(id = withdraw_reason_etc_length, value.checkLength()),
                    modifier = Modifier.align(Alignment.End),
                    style = BongBaekTheme.typography.captionRegular12,
                    color = bongBaekColors.txtDisplaySecondary,
                )
            }
        }
    }
}

@Preview
@Composable
private fun WithdrawReasonSelectorPreview() {
    BongBaekTheme {
        var selectedReason by remember { mutableStateOf<WithdrawType?>(null) }

        WithdrawReasonSelector(
            value = "",
            onValueChange = {},
            selectedReason = selectedReason,
            onReasonSelect = { selectedReason = it },
        )
    }
}
