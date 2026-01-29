package com.bongtu.baekseo.presentation.contents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.string.contents_title
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.EventCategoryType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.compositionlocal.safeDrawingWithBottomNavBar
import com.bongtu.baekseo.core.designsystem.component.fab.BongBaekFAB
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekCategoryBar
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.OnBottomReached
import com.bongtu.baekseo.data.model.content.ContentsDetail
import com.bongtu.baekseo.presentation.contents.ContentsContract.ContentsSideEffect.NavigateToContentsDetail
import com.bongtu.baekseo.presentation.contents.ContentsContract.ContentsUiState
import com.bongtu.baekseo.presentation.contents.components.ContentsArticleCard
import com.bongtu.baekseo.presentation.contents.components.ContentsCounter
import com.bongtu.baekseo.presentation.contents.components.ContentsEmptyView
import com.bongtu.baekseo.presentation.contents.components.ContentsFooter
import kotlinx.coroutines.launch

@Composable
fun ContentsRoute(
    navigateToContentsDetail: (ContentsDetail) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ContentsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToContentsDetail -> navigateToContentsDetail(sideEffect.contentsDetail)
                }
            }
    }

    ContentsScreen(
        uiState = uiState,
        onLoadMore = viewModel::fetchNextContents,
        onCategoryClick = viewModel::updateCategory,
        onArticleClick = viewModel::fetchContentsDetail,
        modifier = modifier,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ContentsScreen(
    uiState: ContentsUiState,
    onLoadMore: () -> Unit,
    onCategoryClick: (EventCategoryType) -> Unit,
    onArticleClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()

    listState.OnBottomReached(buffer = 1) {
        onLoadMore()
    }

    val showFab by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 1 }
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
                selectedCategory = uiState.selectedEvent,
                onCategoryClick = onCategoryClick,
                isEnabled = true,
                modifier = Modifier
                    .padding(vertical = 12.dp),
            )

            Crossfade(
                targetState = uiState.loadState,
            ) { loadState ->
                when (loadState) {
                    is UiState.Empty -> {
                        ContentsCounter(
                            articleCount = uiState.articleCount,
                        )

                        ContentsEmptyView()
                    }

                    is UiState.Failure -> {
                        // TODO: 에러 상태
                    }

                    is UiState.Loading -> {
                        // TODO: 로딩 상태
                    }

                    is UiState.Success -> {
                        LazyColumn(
                            modifier = Modifier
                                .padding(horizontal = 20.dp),
                            state = listState,
                        ) {
                            item {
                                ContentsCounter(
                                    articleCount = uiState.articleCount,
                                )
                            }

                            itemsIndexed(
                                items = uiState.articles,
                                key = { _, article -> article.contentId },
                            ) { index, article ->
                                ContentsArticleCard(
                                    imageUrl = article.thumbnailUrl,
                                    onCardClick = {
                                        onArticleClick(article.contentId)
                                    },
                                    eventType = article.contentCategory,
                                    title = article.contentTitle,
                                    date = article.displayDate,
                                    modifier = Modifier
                                        .padding(
                                            top = if (index == 0) 20.dp else 12.dp,
                                        ),
                                )

                                if (index == uiState.articles.lastIndex) {
                                    ContentsFooter(
                                        modifier = Modifier.padding(
                                            top = 12.dp,
                                            bottom = 28.dp,
                                        ),
                                    )
                                }
                            }
                        }
                    }
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
        ContentsScreen(
            uiState = ContentsUiState(),
            onLoadMore = {},
            onCategoryClick = {},
            onArticleClick = {},
        )
    }
}
