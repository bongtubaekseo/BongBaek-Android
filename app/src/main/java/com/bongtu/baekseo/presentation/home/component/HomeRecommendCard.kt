package com.bongtu.baekseo.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.string.button_recommend
import com.bongtu.baekseo.R.string.home_recommend_card_description
import com.bongtu.baekseo.R.string.home_recommend_card_title
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun HomeRecommendCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.bgDisplaySecondary,
                shape = RoundedCornerShape(8.dp),
            )
            .border(
                width = 1.dp,
                color = BongBaekTheme.colors.borderDividerPrimary,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = home_recommend_card_description),
            style = BongBaekTheme.typography.captionRegular12,
            color = BongBaekTheme.colors.statusFocused,
        )

        Text(
            text = stringResource(id = home_recommend_card_title),
            style = BongBaekTheme.typography.titleSemiBold18,
            color = BongBaekTheme.colors.txtDisplayPrimary,
        )

        Spacer(modifier = Modifier.height(12.dp))

        HomeRecommendButton(
            onClick = onClick,
        )
    }
}

@Composable
fun HomeRecommendButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = BongBaekTheme.colors.btnInteractiveAccent,
        ),
        contentPadding = PaddingValues(10.dp),
    ) {
        Text(
            text = stringResource(id = button_recommend),
            style = BongBaekTheme.typography.body1Medium16,
            color = BongBaekTheme.colors.txtInteractiveInverse,
        )
    }
}

@Preview
@Composable
private fun HomeRecommendCardPreview() {
    BongBaekTheme {
        HomeRecommendCard(
            onClick = { },
        )
    }
}
