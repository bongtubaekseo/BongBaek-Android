package com.bongtu.baekseo.presentation.withdraw.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_check
import com.bongtu.baekseo.R.drawable.ic_withdraw_check
import com.bongtu.baekseo.R.string.withdraw_reason_account
import com.bongtu.baekseo.R.string.withdraw_reason_error
import com.bongtu.baekseo.R.string.withdraw_reason_etc
import com.bongtu.baekseo.R.string.withdraw_reason_privacy
import com.bongtu.baekseo.R.string.withdraw_reason_uncomfortable
import com.bongtu.baekseo.R.string.withdraw_reason_use
import com.bongtu.baekseo.core.common.type.WithdrawType
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

@Composable
fun WithdrawReasonSelector(
    selectedReason: WithdrawType?,
    onReasonSelect: (WithdrawType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        WithdrawType.entries.forEach { item ->
            WithdrawSelectorItem(
                reason = item,
                isSelected = selectedReason == item,
                modifier = Modifier
                    .noRippleClickable {
                        onReasonSelect(item)
                    },
            )
        }
    }
}

@Composable
private fun WithdrawSelectorItem(
    reason: WithdrawType,
    isSelected: Boolean?,
    modifier: Modifier = Modifier,
) {
    val bongBaekColors = BongBaekTheme.colors
    val iconRes = remember(isSelected) {
        if (isSelected == true) ic_check
        else ic_withdraw_check
    }
    val titleRes = remember(reason) {
        when (reason) {
            WithdrawType.UNCOMFORTABLE -> withdraw_reason_uncomfortable
            WithdrawType.PRIVACY -> withdraw_reason_privacy
            WithdrawType.USE -> withdraw_reason_use
            WithdrawType.ERROR -> withdraw_reason_error
            WithdrawType.ACCOUNT -> withdraw_reason_account
            WithdrawType.ETC -> withdraw_reason_etc
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (isSelected == true) {
                    Modifier.border(
                        width = 1.dp,
                        color = bongBaekColors.primaryNormal,
                        shape = RoundedCornerShape(10.dp),
                    )
                } else {
                    Modifier
                }
            )
            .background(
                color = bongBaekColors.gray750,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null,
            tint = Color.Unspecified,
        )

        Text(
            text = stringResource(id = titleRes),
            style = BongBaekTheme.typography.body1Medium16,
            color = BongBaekTheme.colors.gray100,
        )
    }
}

@Preview
@Composable
private fun WithdrawReasonSelectorPreview() {
    BongBaekTheme {
        var selectedReason by remember { mutableStateOf<WithdrawType?>(null) }

        WithdrawReasonSelector(
            selectedReason = selectedReason,
            onReasonSelect = { selectedReason = it },
        )
    }
}