package com.bongtu.baekseo.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bongtu.baekseo.R.drawable.ic_arrow_right
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun HomeContentCard(
    contentTitle: String,
    contentCategory: String,
    thumbnailUrl: String,
    modifier: Modifier = Modifier,
) {
    var lineCount by remember { mutableStateOf(0) }
    val bottomPadding = if (lineCount > 1) 16.dp else 38.dp

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(
                color = BongBaekTheme.colors.btnInteractiveSecondary,
                shape = RoundedCornerShape(6.dp),
            ),
    ) {
        AsyncImage(
            model = thumbnailUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(220f / 160f),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 12.dp,
                    start = 16.dp,
                    end = 12.dp,
                    bottom = bottomPadding,
                ),
        ) {
            Text(
                text = contentCategory,
                style = BongBaekTheme.typography.captionRegular12,
                color = BongBaekTheme.colors.statusFocused,
            )

            Spacer(modifier = Modifier.size(2.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = contentTitle,
                    modifier = Modifier.weight(1f),
                    style = BongBaekTheme.typography.body1Medium16,
                    color = BongBaekTheme.colors.txtDisplayPrimary,
                    onTextLayout = { textLayoutResult ->
                        lineCount = textLayoutResult.lineCount
                    },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.size(8.dp))

                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_right),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Top),
                    tint = BongBaekTheme.colors.iconInteractiveDefault,
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeContentsCardPreview() {
    BongBaekTheme {
        HomeContentCard(
            contentTitle = "MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁MZ의 돌잔치 꿀팁",
            contentCategory = "결혼식",
            thumbnailUrl = "https://i.ifh.cc/TX21OR.jpg",
        )
    }
}
