package com.bongtu.baekseo.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_home_name
import com.bongtu.baekseo.R.drawable.img_home_logo
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

@Composable
fun HomeTopBar(
    onLogoIconClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = ic_home_name),
            contentDescription = null,
            modifier = Modifier
                .padding(
                    vertical = 6.dp,
                ),
            tint = Color.Unspecified,
        )

        Icon(
            painter = painterResource(id = img_home_logo),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
                .noRippleClickable(onClick = onLogoIconClick),
            tint = Color.Unspecified,
        )
    }
}

@Preview
@Composable
private fun HomeTopBarPreview() {
    BongBaekTheme {
        HomeTopBar(
            onLogoIconClick = {},
        )
    }
}