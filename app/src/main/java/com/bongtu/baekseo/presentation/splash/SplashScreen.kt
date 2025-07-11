package com.bongtu.baekseo.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_splash_logo
import com.bongtu.baekseo.R.drawable.ic_splash_name
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun SplashRoute(modifier: Modifier = Modifier) {
    SplashScreen(
        modifier = modifier,
    )
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    val backgroundColors = listOf(
        BongBaekTheme.colors.gray750,
        BongBaekTheme.colors.gray900,
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = backgroundColors,
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = ic_splash_logo),
                contentDescription = null,
                modifier = Modifier.size(34.dp),
            )

            Image(
                imageVector = ImageVector.vectorResource(id = ic_splash_name),
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    BongBaekTheme {
        SplashScreen(

        )
    }
}
