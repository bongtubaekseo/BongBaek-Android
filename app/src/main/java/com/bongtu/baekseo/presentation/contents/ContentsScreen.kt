package com.bongtu.baekseo.presentation.contents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.string.contents_article
import com.bongtu.baekseo.R.string.contents_article_count
import com.bongtu.baekseo.R.string.contents_title
import com.bongtu.baekseo.core.common.type.EventCategoryType
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.compositionlocal.safeDrawingWithBottomNavBar
import com.bongtu.baekseo.core.designsystem.component.fab.BongBaekFAB
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekCategoryBar
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.presentation.contents.components.ContentsArticleCard
import com.bongtu.baekseo.presentation.contents.components.ContentsEmptyView
import com.bongtu.baekseo.presentation.contents.components.ContentsFooter
import kotlinx.coroutines.launch

@Composable
fun ContentsRoute(
    modifier: Modifier = Modifier,
) {
    ContentsScreen(

    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ContentsScreen(
    articles: List<String> = listOf("1", "2", "3", "4", "5", "5", "5", "5", "5", "5", "5"),
    selectedEvent: EventCategoryType = EventCategoryType.ALL,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val showFab by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(BongBaekTheme.colors.bgDisplayPrimary)
            .windowInsetsPadding(WindowInsets.safeDrawingWithBottomNavBar),
    ) {
        Column {
            BongBaekTopBar(
                title = stringResource(contents_title),
                topBarType = TopBarType.TEXT_ONLY_START,
            )

            BongBaekCategoryBar(
                selectedCategory = selectedEvent,
                onCategoryClick = { },
                isEnabled = true,
                modifier = Modifier
                    .padding(vertical = 12.dp),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
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
                    text = stringResource(contents_article_count, articles.size),
                    color = BongBaekTheme.colors.txtDisplaySecondary,
                    style = BongBaekTheme.typography.body2Regular16,
                )
            }

            if (articles.isEmpty()) ContentsEmptyView()

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(
                    top = 20.dp,
                    bottom = 28.dp,
                ),
            ) {
                items(articles.size) { index ->
                    ContentsArticleCard(
                        imageUrl = "",
                        eventType = if (index % 2 == 0) EventType.WEDDING else EventType.FUNERAL,
                        title = "경조사 정보 제목 테스트 ${index + 1} 번째 글입니다.",
                        date = "2025.11.${index + 1}",
                    )

                    if (index == articles.lastIndex)
                        ContentsFooter(
                            modifier = Modifier.padding(top = 12.dp),
                        )
                }
            }
        }

        AnimatedVisibility(
            visible = showFab,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
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
private fun ContentsScreenPreview() {
    BongBaekTheme {
        ContentsScreen()
    }
}
