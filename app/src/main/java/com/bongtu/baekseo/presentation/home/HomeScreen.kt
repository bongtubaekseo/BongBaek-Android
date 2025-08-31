package com.bongtu.baekseo.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.string.home_schedule_more
import com.bongtu.baekseo.R.string.home_schedule_title
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.ScheduleCardType
import com.bongtu.baekseo.core.compositionlocal.safeDrawingWithBottomNavBar
import com.bongtu.baekseo.core.designsystem.component.card.BongBaekScheduleCard
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.excludeTop
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.data.model.event.HomeEvent
import com.bongtu.baekseo.presentation.home.HomeContract.HomeSideEffect.NavigateToEdit
import com.bongtu.baekseo.presentation.home.HomeContract.HomeSideEffect.NavigateToRecommend
import com.bongtu.baekseo.presentation.home.HomeContract.HomeSideEffect.NavigateToSchedule
import com.bongtu.baekseo.presentation.home.HomeContract.HomeState
import com.bongtu.baekseo.presentation.home.component.HomePageCard
import com.bongtu.baekseo.presentation.home.component.HomePageEmptyCard
import com.bongtu.baekseo.presentation.home.component.HomeRecommendCard
import com.bongtu.baekseo.presentation.home.component.HomeScheduleEmptyCard
import com.bongtu.baekseo.presentation.home.component.HomeTopBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomeRoute(
    navigateToRecommend: () -> Unit,
    navigateToEdit: () -> Unit,
    navigateToSchedule: () -> Unit,
    navigateToMyPage: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        viewModel.fetchHomeEvent()
        viewModel.getUsername()
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    NavigateToSchedule -> navigateToSchedule()
                    NavigateToEdit -> navigateToEdit()
                    NavigateToRecommend -> navigateToRecommend()
                }
            }
    }

    HomeScreen(
        uiState = uiState,
        navigateToEdit = navigateToEdit,
        navigateToRecommend = navigateToRecommend,
        navigateToSchedule = navigateToSchedule,
        navigateToMyPage = navigateToMyPage,
        modifier = modifier,
    )
}

@Composable
fun HomeScreen(
    uiState: HomeState,
    navigateToEdit: () -> Unit,
    navigateToRecommend: () -> Unit,
    navigateToSchedule: () -> Unit,
    navigateToMyPage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState.homeLoadState) {
        is UiState.Empty -> {
            // TODO: 빈 상태
        }

        is UiState.Failure -> {
            // TODO: 에러 상태
        }

        is UiState.Loading -> {
            // TODO: 로딩 상태
            // 임시로 흰 화면이 깜빡거려 같은 배경색으로 대체
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = BongBaekTheme.colors.gray900),
            )
        }

        is UiState.Success -> {
            HomeSuccessScreen(
                items = uiState.homeLoadState.data,
                name = uiState.name,
                navigateToEdit = navigateToEdit,
                navigateToRecommend = navigateToRecommend,
                navigateToSchedule = navigateToSchedule,
                navigateToMyPage = navigateToMyPage,
                modifier = modifier,
            )
        }
    }
}

@Composable
fun HomeSuccessScreen(
    items: ImmutableList<HomeEvent>,
    name: String,
    navigateToEdit: () -> Unit,
    navigateToRecommend: () -> Unit,
    navigateToSchedule: () -> Unit,
    navigateToMyPage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = {
        items.size
    })
    val isCardMultiple = items.size > 1
    val recommendCardPadding = if (isCardMultiple) 0.dp else 30.dp
    val contentPadding by remember(pagerState) {
        derivedStateOf {
            val page = pagerState.currentPage
            val last = (pagerState.pageCount - 1).coerceAtLeast(0)
            val start = when {
                !isCardMultiple || page == 0 -> 20.dp
                page == last -> 32.dp
                else -> 26.dp
            }
            val end = when {
                !isCardMultiple || page == last -> 20.dp
                page == 0 -> 32.dp
                else -> 26.dp
            }
            PaddingValues(start = start, end = end)
        }
    }

    Column(
        modifier = modifier
            .background(color = BongBaekTheme.colors.gray900)
            .windowInsetsPadding(WindowInsets.safeDrawingWithBottomNavBar.excludeTop())
            .verticalScroll(rememberScrollState()),
    ) {
        HomeTopBar(
            onLogoIconClick = navigateToMyPage,
            modifier = Modifier.padding(20.dp),
        )

        if (items.isEmpty()) {
            HomePageEmptyCard(
                onBadgeClick = navigateToEdit,
                modifier = Modifier
                    .padding(horizontal = 20.dp),
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = contentPadding,
                    pageSpacing = 8.dp,
                    key = { page ->
                        items[page].eventId
                    },
                ) { page ->
                    val item = items[page]

                    HomePageCard(
                        hostname = item.hostName,
                        eventType = item.eventCategory,
                        daysLeft = item.dDay,
                        eventDate = item.eventDate,
                    )
                }

                if (isCardMultiple) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 22.dp,
                                bottom = 24.dp,
                            ),
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 6.dp,
                            alignment = Alignment.CenterHorizontally,
                        ),
                    ) {
                        repeat(pagerState.pageCount) { iteration ->
                            val color =
                                if (pagerState.currentPage == iteration) BongBaekTheme.colors.white
                                else BongBaekTheme.colors.pageIndicator

                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(color)
                                    .size(5.dp),
                            )
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp),
        ) {
            HomeRecommendCard(
                onClick = navigateToRecommend,
                modifier = Modifier.padding(top = recommendCardPadding),
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 60.dp,
                        bottom = 20.dp,
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(id = home_schedule_title, name),
                    style = BongBaekTheme.typography.titleSemiBold18,
                    color = BongBaekTheme.colors.white,
                )

                if (items.isNotEmpty()) {
                    Text(
                        text = stringResource(id = home_schedule_more),
                        modifier = Modifier.noRippleClickable(navigateToSchedule),
                        color = BongBaekTheme.colors.gray200,
                        style = BongBaekTheme.typography.captionRegular12,
                    )
                }
            }

            items.forEachIndexed { index, item ->
                BongBaekScheduleCard(
                    scheduleCardType = ScheduleCardType.HOME,
                    hostName = item.hostName,
                    hostNickname = item.hostNickname,
                    eventCategory = item.eventCategory,
                    relationship = item.relationship,
                    cost = item.cost,
                    eventDate = item.eventDate,
                    modifier = Modifier.padding(
                        bottom = if (index == items.lastIndex) 36.dp else 10.dp
                    ),
                    location = item.location,
                )
            }

            if (items.isEmpty()) {
                HomeScheduleEmptyCard(
                    onBadgeClick = navigateToEdit,
                    modifier = Modifier.padding(bottom = 40.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeSuccessScreenPreview() {
    val items = persistentListOf<HomeEvent>(
        HomeEvent(
            eventId = "eventId1",
            hostName = "헤헤",
            hostNickname = "초록승준",
            eventCategory = "생일",
            relationship = "친구",
            cost = 10000,
            eventDate = "2025-02-11",
            dDay = 1,
            location = "강남",
        ),
        HomeEvent(
            eventId = "eventId2",
            hostName = "헤헤",
            hostNickname = "초록승준",
            eventCategory = "생일",
            relationship = "친구",
            cost = 10000,
            eventDate = "2025-02-11",
            dDay = 1,
            location = "강남",
        ),
        HomeEvent(
            eventId = "eventId3",
            hostName = "헤헤",
            hostNickname = "초록승준",
            eventCategory = "생일",
            relationship = "친구",
            cost = 10000,
            eventDate = "2025-02-11",
            dDay = 1,
            location = "강남",
        ),
    )

    BongBaekTheme {
        HomeSuccessScreen(
            items = items,
            name = "",
            navigateToEdit = {},
            navigateToRecommend = {},
            navigateToSchedule = {},
            navigateToMyPage = {},
        )
    }
}
