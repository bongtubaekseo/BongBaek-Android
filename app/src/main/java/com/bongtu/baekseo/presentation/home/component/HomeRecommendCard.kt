package com.bongtu.baekseo.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = BongBaekTheme.colors.gray750)
            .border(
                width = 1.dp,
                color = BongBaekTheme.colors.lineNormal,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.wrapContentSize(),
            ) {
                Text(
                    text = stringResource(id = home_recommend_card_description),
                    style = BongBaekTheme.typography.captionRegular12,
                    color = BongBaekTheme.colors.gray300,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = home_recommend_card_title),
                    style = BongBaekTheme.typography.titleSemiBold18,
                    color = BongBaekTheme.colors.white,
                )
            }

            Image(
                painter = painterResource(id = img_home_recommend),
                contentDescription = null,
                modifier = Modifier.size(width = 46.dp, height = 54.dp),
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
