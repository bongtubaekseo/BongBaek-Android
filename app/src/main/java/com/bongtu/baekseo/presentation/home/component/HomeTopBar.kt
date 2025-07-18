package com.bongtu.baekseo.presentation.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(top = 30.dp, start = 20.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_name),
            contentDescription = null,
            tint = Color.Unspecified,
        )
    }
}

@Preview
@Composable
private fun HomeTopBarPreview() {
    BongBaekTheme {
        HomeTopBar()
    }
}