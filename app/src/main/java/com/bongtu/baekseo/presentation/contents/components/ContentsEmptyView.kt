package com.bongtu.baekseo.presentation.contents.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.img_contents_empty
import com.bongtu.baekseo.R.string.contents_empty_desc
import com.bongtu.baekseo.R.string.contents_empty_title
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun ContentsEmptyView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(contents_empty_title),
            color = BongBaekTheme.colors.txtDisplayPrimary,
            style = BongBaekTheme.typography.headBold24,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(contents_empty_desc),
            color = BongBaekTheme.colors.txtDisplayTertiary,
            style = BongBaekTheme.typography.body2Regular14,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(img_contents_empty),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 100.dp),
            contentScale = ContentScale.FillWidth,
        )
    }
}

@Preview
@Composable
private fun ContentsEmptyViewPreview() {
    BongBaekTheme {
        ContentsEmptyView()
    }
}
