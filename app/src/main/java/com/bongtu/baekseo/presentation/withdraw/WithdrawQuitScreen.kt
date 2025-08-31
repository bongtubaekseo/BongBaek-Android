package com.bongtu.baekseo.presentation.withdraw

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bongtu.baekseo.R.drawable.img_withdraw_gift
import com.bongtu.baekseo.R.string.withdraw_quit_button
import com.bongtu.baekseo.R.string.withdraw_quit_information
import com.bongtu.baekseo.R.string.withdraw_quit_title
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.compositionlocal.safeDrawingWithBottomNavBar
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun WithdrawQuitScreen(
    onRestartApp: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WithdrawViewModel = hiltViewModel(),
) {
    BackHandler {
        // 뒤로 가기 방지
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900)
            .statusBarsPadding()
            .windowInsetsPadding(WindowInsets.safeDrawingWithBottomNavBar)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Spacer(modifier = Modifier.height(74.dp))

            Text(
                text = stringResource(id = withdraw_quit_title),
                style = BongBaekTheme.typography.headBold24,
                color = BongBaekTheme.colors.gray100,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(id = withdraw_quit_information),
                style = BongBaekTheme.typography.body2Regular14,
                color = BongBaekTheme.colors.gray400,
            )
        }

        Image(
            painter = painterResource(id = img_withdraw_gift),
            contentDescription = null,
        )

        BongBaekButton(
            title = stringResource(id = withdraw_quit_button),
            onClick = {
                onRestartApp(false)
                viewModel.clearToken()
            },
            buttonType = ButtonType.PRIMARY,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 36.dp,
                ),
        )
    }
}

@Preview
@Composable
private fun WithdrawQuitScreenPreview() {
    BongBaekTheme {
        WithdrawQuitScreen(
            onRestartApp = {},
        )
    }
}