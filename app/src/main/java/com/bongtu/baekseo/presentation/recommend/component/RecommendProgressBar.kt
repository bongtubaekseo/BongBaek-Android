package com.bongtu.baekseo.presentation.recommend.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.core.designsystem.component.progressbar.BongBaekProgressBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

private const val MAX_PAGE = 4

@Composable
fun RecommendProgressBar(
    currentIndex: Int,
    modifier: Modifier = Modifier,
) {
    val currentPage by animateFloatAsState(
        targetValue = currentIndex / MAX_PAGE.toFloat()
    )

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Text(
            text = "${currentIndex}/$MAX_PAGE",
            style = BongBaekTheme.typography.captionRegular12,
            color = BongBaekTheme.colors.gray400,
        )

        BongBaekProgressBar(
            progress = currentPage,
            modifier = Modifier,
        )
    }
}

@Preview
@Composable
private fun RecommendProgressBarPreview() {
    BongBaekTheme {
        var currentIndex by remember { mutableStateOf(1) }

        RecommendProgressBar(
            currentIndex = currentIndex,
            modifier = Modifier
                .statusBarsPadding()
                .noRippleClickable {
                    currentIndex++
                    if (currentIndex > MAX_PAGE) currentIndex = 0
                },
        )
    }
}
