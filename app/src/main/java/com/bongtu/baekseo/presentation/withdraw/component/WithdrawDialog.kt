package com.bongtu.baekseo.presentation.withdraw.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bongtu.baekseo.R.string.withdraw_dialog_cancel
import com.bongtu.baekseo.R.string.withdraw_dialog_confirm
import com.bongtu.baekseo.R.string.withdraw_dialog_description
import com.bongtu.baekseo.R.string.withdraw_dialog_title
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

@Composable
fun WithdrawDialog(
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
        Card(
            modifier = Modifier.padding(horizontal = 20.dp),
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = BongBaekTheme.colors.gray750)
                    .padding(
                        start = 20.dp,
                        top = 20.dp,
                        end = 20.dp,
                        bottom = 16.dp,
                    ),
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = stringResource(id = withdraw_dialog_title),
                        style = BongBaekTheme.typography.titleSemiBold20,
                        color = BongBaekTheme.colors.white,
                    )

                    Spacer(modifier = Modifier.size(6.dp))

                    Text(
                        text = stringResource(id = withdraw_dialog_description),
                        style = BongBaekTheme.typography.captionRegular12,
                        color = BongBaekTheme.colors.gray300,
                    )
                }

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.End)
                        .padding(top = 10.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp,
                            )
                            .noRippleClickable(onDismissRequest),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(id = withdraw_dialog_cancel),
                            style = BongBaekTheme.typography.body2Regular16,
                            color = BongBaekTheme.colors.gray300,
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp,
                            )
                            .noRippleClickable(onConfirmClick),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(id = withdraw_dialog_confirm),
                            style = BongBaekTheme.typography.body2Regular16,
                            color = BongBaekTheme.colors.secondaryRed,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun WithdrawDialogPreview() {
    BongBaekTheme {
        WithdrawDialog(
            onDismissRequest = {},
            onConfirmClick = {},
        )
    }
}