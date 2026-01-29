package com.bongtu.baekseo.presentation.contents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.compositionlocal.safeDrawingWithBottomNavBar
import com.bongtu.baekseo.core.designsystem.component.fab.BongBaekFAB
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.DateFormatter
import com.bongtu.baekseo.core.util.noRippleClickable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@Composable
fun ContentsDetailScreen(
    onBackClick: () -> Unit,
    eventType: String,
    title: String,
    date: String,
    imageUrls: ImmutableList<String>,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val showFab by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 1 }
    }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BongBaekTheme.colors.bgDisplayPrimary),
    ) {
        LazyColumn(
            state = listState,
            contentPadding = WindowInsets.safeDrawingWithBottomNavBar.asPaddingValues(),
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
                        text = DateFormatter.formatToDot(date),
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

        AnimatedVisibility(
            visible = showFab,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 20.dp,
                    bottom = 54.dp,
                ),
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        ) {
            BongBaekFAB(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
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
            date = "2025-12-10",
            imageUrls = persistentListOf(),
        )
    }
}
