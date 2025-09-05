package com.bongtu.baekseo.core.designsystem.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bongtu.baekseo.R.string.dialog_cancel
import com.bongtu.baekseo.R.string.dialog_delete_confirm
import com.bongtu.baekseo.R.string.dialog_delete_description
import com.bongtu.baekseo.R.string.dialog_delete_title
import com.bongtu.baekseo.R.string.dialog_error_update_description
import com.bongtu.baekseo.R.string.dialog_error_update_title
import com.bongtu.baekseo.R.string.dialog_feature_update_description
import com.bongtu.baekseo.R.string.dialog_feature_update_title
import com.bongtu.baekseo.R.string.dialog_logout_confirm
import com.bongtu.baekseo.R.string.dialog_logout_description
import com.bongtu.baekseo.R.string.dialog_logout_title
import com.bongtu.baekseo.R.string.dialog_update_confirm
import com.bongtu.baekseo.R.string.dialog_withdraw_confirm
import com.bongtu.baekseo.R.string.dialog_withdraw_description
import com.bongtu.baekseo.R.string.dialog_withdraw_title
import com.bongtu.baekseo.core.common.type.DialogType
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

@Composable
fun BongBaekDialog(
    dialogType: DialogType,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
            contentAlignment = Alignment.Center
        ) {
            BongBaekDialogContent(
                dialogType = dialogType,
                onDismissRequest = onDismissRequest,
                onConfirmClick = onConfirmClick,
                modifier = Modifier,
            )
        }
    }
}

@Composable
private fun BongBaekDialogContent(
    dialogType: DialogType,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val bongBaekColors = BongBaekTheme.colors
    val bongBeakTypo = BongBaekTheme.typography

    val (titleRes, descriptionRes) = remember(dialogType) {
        when (dialogType) {
            DialogType.DELETE -> dialog_delete_title to dialog_delete_description
            DialogType.LOGOUT -> dialog_logout_title to dialog_logout_description
            DialogType.WITHDRAW -> dialog_withdraw_title to dialog_withdraw_description
            DialogType.ERROR_UPDATE -> dialog_error_update_title to dialog_error_update_description
            DialogType.FEATURE_UPDATE -> dialog_feature_update_title to dialog_feature_update_description
        }
    }
    val confirmRes = remember(dialogType) {
        when (dialogType) {
            DialogType.DELETE -> dialog_delete_confirm
            DialogType.LOGOUT -> dialog_logout_confirm
            DialogType.WITHDRAW -> dialog_withdraw_confirm
            else -> dialog_update_confirm
        }
    }
    val (confirmColor, confirmStyle, confirmPadding) = remember(dialogType) {
        when (dialogType) {
            DialogType.DELETE, DialogType.WITHDRAW -> Triple(
                bongBaekColors.secondaryRed,
                bongBeakTypo.body2Regular16,
                16.dp,
            )

            DialogType.LOGOUT -> Triple(
                bongBaekColors.secondaryRed,
                bongBeakTypo.body1Medium16,
                3.dp,
            )

            DialogType.ERROR_UPDATE, DialogType.FEATURE_UPDATE -> Triple(
                bongBaekColors.white,
                bongBeakTypo.body1Medium16,
                3.dp,
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(color = bongBaekColors.gray750)
            .padding(
                start = 20.dp,
                top = 20.dp,
                end = 20.dp,
                bottom = 16.dp,
            ),
    ) {
        Text(
            text = stringResource(id = titleRes),
            style = bongBeakTypo.titleSemiBold20,
            color = bongBaekColors.white,
        )

        Spacer(modifier = Modifier.size(6.dp))

        Text(
            text = stringResource(id = descriptionRes),
            style = bongBeakTypo.captionRegular12,
            color = bongBaekColors.gray300,
        )

        Row(
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (dialogType != DialogType.ERROR_UPDATE) {
                Text(
                    text = stringResource(id = dialog_cancel),
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 8.dp,
                        )
                        .noRippleClickable(onDismissRequest),
                    style = bongBeakTypo.body2Regular16,
                    color = bongBaekColors.gray300,
                )
            }

            Text(
                text = stringResource(id = confirmRes),
                modifier = Modifier
                    .padding(
                        horizontal = confirmPadding,
                        vertical = 8.dp,
                    )
                    .noRippleClickable(onConfirmClick),
                style = confirmStyle,
                color = confirmColor,
            )
        }
    }
}

@Preview
@Composable
private fun BongBaekDialogContentPreview() {
    BongBaekTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            BongBaekDialogContent(
                dialogType = DialogType.DELETE,
                onDismissRequest = {},
                onConfirmClick = {},
            )

            BongBaekDialogContent(
                dialogType = DialogType.LOGOUT,
                onDismissRequest = {},
                onConfirmClick = {},
            )

            BongBaekDialogContent(
                dialogType = DialogType.WITHDRAW,
                onDismissRequest = {},
                onConfirmClick = {},
            )

            BongBaekDialogContent(
                dialogType = DialogType.ERROR_UPDATE,
                onDismissRequest = {},
                onConfirmClick = {},
            )

            BongBaekDialogContent(
                dialogType = DialogType.FEATURE_UPDATE,
                onDismissRequest = {},
                onConfirmClick = {},
            )
        }
    }
}