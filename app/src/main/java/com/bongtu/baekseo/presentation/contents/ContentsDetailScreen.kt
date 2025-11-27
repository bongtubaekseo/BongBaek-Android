package com.bongtu.baekseo.presentation.contents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ContentsDetailScreen(
    onBackClick: () -> Unit,
    eventType: String,
    title: String,
    date: String,
    imageUrls: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(BongBaekTheme.colors.bgDisplayPrimary),
    ) {
        item {
            Icon(
                imageVector = ImageVector.vectorResource(ic_arrow_back),
                contentDescription = null,
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 20.dp,
                    )
                    .noRippleClickable(onBackClick),
                tint = BongBaekTheme.colors.iconInteractiveDefault,
            )
        }

        item {
            Column(
                modifier = Modifier
                    .padding(
                        top = 12.dp,
                        start = 20.dp,
                        end = 20.dp,
                        bottom = 20.dp,
                    )
            ) {
                Text(
                    text = eventType,
                    color = BongBaekTheme.colors.statusFocused,
                    style = BongBaekTheme.typography.captionRegular12,
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = title,
                    color = BongBaekTheme.colors.txtDisplayPrimary,
                    style = BongBaekTheme.typography.titleSemiBold20,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = date,
                    color = BongBaekTheme.colors.txtDisplayTertiary,
                    style = BongBaekTheme.typography.body2Regular14,
                )
            }
        }

        items(
            items = imageUrls,
        ) {
            HorizontalDivider(
                thickness = 10.dp,
                color = BongBaekTheme.colors.borderDisplayTitle,
            )

            AsyncImage(
                model = it,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
        }
    }
}

@Preview
@Composable
private fun ContentsDetailScreenPreview() {
    BongBaekTheme {
        ContentsDetailScreen(
            onBackClick = {},
            eventType = EventType.WEDDING.label,
            title = "제목이 오는 위치제목이 오는 위치제목이 오는 위치제목이 오는 위치",
            date = "날짜가 오는 위치",
            imageUrls = persistentListOf(),
        )
    }
}
