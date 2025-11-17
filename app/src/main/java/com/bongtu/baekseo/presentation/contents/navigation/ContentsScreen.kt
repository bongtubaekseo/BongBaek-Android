package com.bongtu.baekseo.presentation.contents.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun ContentsRoute(
    modifier: Modifier = Modifier,
) {
    ContentsScreen()
}

@Composable
private fun ContentsScreen(
    modifier: Modifier = Modifier,
) {

}

@Preview
@Composable
private fun ContentsScreenPreview() {
    BongBaekTheme {
        ContentsScreen()
    }
}
