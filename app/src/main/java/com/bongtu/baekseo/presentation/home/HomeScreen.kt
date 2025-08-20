package com.bongtu.baekseo.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.string.home_schedule_more
import com.bongtu.baekseo.R.string.home_schedule_title
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.ScheduleCardType
import com.bongtu.baekseo.core.designsystem.component.card.BongBaekScheduleCard
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.data.model.event.HomeEvent
import com.bongtu.baekseo.presentation.home.HomeContract.HomeSideEffect.NavigateToEdit
import com.bongtu.baekseo.presentation.home.HomeContract.HomeSideEffect.NavigateToRecommend
import com.bongtu.baekseo.presentation.home.HomeContract.HomeSideEffect.NavigateToSchedule
import com.bongtu.baekseo.presentation.home.HomeContract.HomeState
import com.bongtu.baekseo.presentation.home.component.HomePageEmptyCard
import com.bongtu.baekseo.presentation.home.component.HomePageMultipleCard
import com.bongtu.baekseo.presentation.home.component.HomePageSingleCard
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
    bottomPadding: Dp,
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
        modifier = modifier
            .padding(bottom = bottomPadding),
    )
}

@Composable
fun HomeScreen(
    uiState: HomeState,
    navigateToEdit: () -> Unit,
    navigateToRecommend: () -> Unit,
    navigateToSchedule: () -> Unit,
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
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = {
        items.size
    })
    val isMultipleCard = remember {
        items.size > 1
    }
    val isFirstPage by remember {
        derivedStateOf { pagerState.currentPage == 0 }
    }
    val isLastPage by remember {
        derivedStateOf { pagerState.currentPage == pagerState.pageCount - 1 }
    }

    val recommendCardPadding = remember(isMultipleCard) {
        if (isMultipleCard) 0.dp else 30.dp
    }
    val pagerStartContentPadding = remember(isMultipleCard, isFirstPage, isLastPage) {
        when {
            !isMultipleCard || isFirstPage -> 20.dp
            isLastPage -> 32.dp
            else -> 26.dp
        }
    }
    val pagerEndContentPadding = remember(isMultipleCard, isFirstPage, isLastPage) {
        when {
            !isMultipleCard || isLastPage -> 20.dp
            isFirstPage -> 32.dp
            else -> 26.dp
        }
    }

    Column(
        modifier = modifier
            .background(color = BongBaekTheme.colors.gray900)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        HomeTopBar()

        if (items.isEmpty()) {
            HomePageEmptyCard(
                onBadgeClick = navigateToEdit,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp),
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = 20.dp,
                        ),
                    contentPadding = PaddingValues(
                        start = pagerStartContentPadding,
                        end = pagerEndContentPadding,
                    ),
                    pageSpacing = 8.dp,
                    key = { page ->
                        items[page].eventId
                    },
                ) { page ->
                    val item = items[page]

                    when (items.size) {
                        1 -> {
                            HomePageSingleCard(
                                hostname = item.hostName,
                                eventType = item.eventCategory,
                                daysLeft = item.dDay,
                                eventDate = item.eventDate,
                            )
                        }

                        else -> {
                            HomePageMultipleCard(
                                hostname = item.hostName,
                                eventType = item.eventCategory,
                                daysLeft = item.dDay,
                                eventDate = item.eventDate,
                            )
                        }
                    }
                }
            }

            if (isMultipleCard) {
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(
                            top = 22.dp,
                            bottom = 24.dp,
                        ),
                    horizontalArrangement = Arrangement.spacedBy(
                        6.dp,
                        Alignment.CenterHorizontally,
                    ),
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) BongBaekTheme.colors.white else BongBaekTheme.colors.pageIndicator
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

        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
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
                        style = BongBaekTheme.typography.captionRegular12,
                        color = BongBaekTheme.colors.gray200,
                    )
                }
            }

            items.forEach { item ->
                BongBaekScheduleCard(
                    scheduleCardType = ScheduleCardType.HOME,
                    hostName = item.hostName,
                    hostNickname = item.hostNickname,
                    eventCategory = item.eventCategory,
                    relationship = item.relationship,
                    cost = item.cost,
                    eventDate = item.eventDate,
                    location = item.location,
                )

                Spacer(modifier = Modifier.size(10.dp))

                if (item == items.last()) {
                    Spacer(modifier = Modifier.size(28.dp))
                }
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
    val items = persistentListOf(
        HomeEvent(
            eventId = "eventId1",
            hostName = "헤헤",
            hostNickname = "초록승준",
            eventCategory = "생일",
            relationship = "친구",
            cost = 10000,
            eventDate = "2025.02.11",
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
            eventDate = "2025.02.11",
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
            eventDate = "2025.02.11",
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
        )
    }
}
