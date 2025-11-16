package com.bongtu.baekseo.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.img_home_recommend
import com.bongtu.baekseo.R.string.button_recommend
import com.bongtu.baekseo.R.string.home_recommend_card_description
import com.bongtu.baekseo.R.string.home_recommend_card_title
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun HomeRecommendCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.wrapContentSize(),
            ) {
                Text(
                    text = stringResource(id = home_recommend_card_description),
                    style = BongBaekTheme.typography.captionRegular12,
                    color = BongBaekTheme.colors.statusFocused,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = home_recommend_card_title),
                    style = BongBaekTheme.typography.titleSemiBold18,
                    color = BongBaekTheme.colors.txtDisplayTertiary,
                )
            }

            Image(
                painter = painterResource(id = img_home_recommend),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight(),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        BongBaekButton(
            title = stringResource(id = button_recommend),
            onClick = onClick,
            buttonType = ButtonType.PRIMARY,
            modifier = Modifier.fillMaxWidth(),
            textStyle = BongBaekTheme.typography.titleSemiBold16,
            paddingValues = PaddingValues(12.dp),
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
