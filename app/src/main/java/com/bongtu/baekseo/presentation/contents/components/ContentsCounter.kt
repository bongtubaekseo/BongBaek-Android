package com.bongtu.baekseo.presentation.contents.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.string.contents_article
import com.bongtu.baekseo.R.string.contents_article_count
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun ContentsCounter(
    articleCount: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 12.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(contents_article),
            color = BongBaekTheme.colors.txtDisplayTertiary,
            style = BongBaekTheme.typography.body2Regular16,
        )

        Text(
            text = stringResource(
                contents_article_count,
                articleCount,
            ),
            color = BongBaekTheme.colors.txtDisplaySecondary,
            style = BongBaekTheme.typography.body2Regular16,
        )
    }
}

@Preview
@Composable
private fun ContentsCounterPreview() {
    BongBaekTheme {
        ContentsCounter(1)
    }
}
