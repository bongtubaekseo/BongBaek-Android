package com.bongtu.baekseo.presentation.contents.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.string.contents_footer
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun ContentsFooter(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(contents_footer),
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.bgDisplaySecondary,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 12.dp),
        color = BongBaekTheme.colors.txtDisplayTertiary,
        style = BongBaekTheme.typography.body2Regular14,
        textAlign = TextAlign.Center,
    )
}

@Preview
@Composable
private fun ContentsFooterPreview() {
    BongBaekTheme {
        ContentsFooter()
    }
}
