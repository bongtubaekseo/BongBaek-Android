package com.bongtu.baekseo.presentation.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_arrow_right
import com.bongtu.baekseo.R.string.onboarding_bottom_sheet_check_age
import com.bongtu.baekseo.R.string.onboarding_bottom_sheet_check_all
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingBottomSheet(
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = {},
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                )
            )
            .background(color = BongBaekTheme.colors.gray750),
        dragHandle = null,
    ) {
        Column {
            Text(
                text = stringResource(id = onboarding_bottom_sheet_check_all),
                style = BongBaekTheme.typography.titleSemiBold18,
                color = BongBaekTheme.colors.white
            )
        }
    }
}

@Composable
private fun OnBoardingBottomSheetItem(
    modifier: Modifier = Modifier,
    isArrowVisible: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // TODO: checkbox
        Text(
            text = stringResource(id = onboarding_bottom_sheet_check_age),
            modifier = Modifier.padding(start = 8.dp),
            style = BongBaekTheme.typography.body2Regular16,
            color = BongBaekTheme.colors.gray300,
        )

        Spacer(modifier = Modifier.weight(1f))

        if (isArrowVisible) {
            Icon(
                painter = painterResource(id = ic_arrow_right),
                contentDescription = null,
                tint = BongBaekTheme.colors.gray400,
            )
        }
    }
}

@Preview
@Composable
private fun OnBoardingBottomSheetItemPreview() {
    BongBaekTheme {
        OnBoardingBottomSheetItem()
    }
}

@Preview
@Composable
private fun OnBoardingBottomSheetPreview() {
    BongBaekTheme {
        OnBoardingBottomSheet()
    }
}
