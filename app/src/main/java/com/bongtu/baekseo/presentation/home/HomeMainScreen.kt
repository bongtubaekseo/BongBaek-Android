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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongtu.baekseo.R.string.home_schedule_more
import com.bongtu.baekseo.R.string.home_schedule_title
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.presentation.home.HomeContract.HomeState
import com.bongtu.baekseo.presentation.home.component.HomePageEmptyCard
import com.bongtu.baekseo.presentation.home.component.HomePageMultipleCard
import com.bongtu.baekseo.presentation.home.component.HomePageSingleCard
import com.bongtu.baekseo.presentation.home.component.HomeRecommendCard
import com.bongtu.baekseo.presentation.home.component.HomeScheduleCard
import com.bongtu.baekseo.presentation.home.component.HomeScheduleEmptyCard
import com.bongtu.baekseo.presentation.home.component.HomeTopBar
import com.bongtu.baekseo.presentation.home.model.HomeEvent
import com.bongtu.baekseo.presentation.home.model.HomeEventInfo
import com.bongtu.baekseo.presentation.home.model.HomeHostInfo
import com.bongtu.baekseo.presentation.home.model.HomeLocationInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

@Composable
fun HomeMainRoute(
    navigateToSchedule: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchHomeEvent()
    }

    HomeMainScreen(
        uiState = uiState,
        onBadgeClick = {
            // TODO: 뱃지 클릭 이벤트
        },
        navigateToSchedule = navigateToSchedule,
        modifier = modifier,
    )
}

@Composable
fun HomeMainScreen(
    uiState: HomeState,
    onBadgeClick: () -> Unit,
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
        }

        is UiState.Success -> {
            HomeMainSuccessScreen(
                items = uiState.homeLoadState.data,
                onBadgeClick = onBadgeClick,
                navigateToSchedule = navigateToSchedule,
                modifier = modifier,
            )
        }
    }
}

@Composable
fun HomeMainSuccessScreen(
    items: ImmutableList<HomeEvent>,
    onBadgeClick: () -> Unit,
    navigateToSchedule: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = {
        items.size
    })
    val recommendCardPadding = if (items.isEmpty()) 30.dp else 10.dp
    val pagerContentPadding = if (items.size > 1) 32.dp else 20.dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900)
            .verticalScroll(rememberScrollState()),
    ) {
        HomeTopBar()

        if (items.isEmpty()) {
            HomePageEmptyCard(
                onBadgeClick = onBadgeClick,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .padding(horizontal = 20.dp),
            )
        }

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
                    start = 20.dp,
                    end = pagerContentPadding,
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
                            hostname = item.hostInfo.hostName,
                            eventType = item.eventInfo.eventCategory,
                            daysLeft = item.eventInfo.dDay,
                            eventDate = item.eventInfo.eventDate,
                        )
                    }

                    else -> {
                        HomePageMultipleCard(
                            hostname = item.hostInfo.hostName,
                            eventType = item.eventInfo.eventCategory,
                            daysLeft = item.eventInfo.dDay,
                            eventDate = item.eventInfo.eventDate,
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
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

        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
        ) {
            HomeRecommendCard(
                onClick = {
                    // TODO: 경조사비 추천 받기 클릭 이벤트
                },
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
                    text = stringResource(id = home_schedule_title, "봉백"),
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
                HomeScheduleCard(
                    event = item,
                )

                Spacer(modifier = Modifier.size(10.dp))

                if (item == items.last()) {
                    Spacer(modifier = Modifier.size(40.dp))
                }
            }

            if (items.isEmpty()) {
                HomeScheduleEmptyCard(
                    onBadgeClick = onBadgeClick,
                    modifier = Modifier.padding(bottom = 40.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeMainSuccessScreenPreview() {
    val items = persistentListOf(
        HomeEvent(
            eventId = "1",
            hostInfo = HomeHostInfo(
                hostName = "공승준",
                hostNickname = "초록승준",
            ),
            eventInfo = HomeEventInfo(
                eventCategory = EventType.WEDDING,
                relationship = RelationType.FRIEND,
                cost = 10000,
                eventDate = LocalDate.of(2025, 2, 11),
            ),
            locationInfo = HomeLocationInfo(
                location = "강남구 테헤란로 강남 웨딩홀"
            ),
        ),
        HomeEvent(
            eventId = "2",
            hostInfo = HomeHostInfo(
                hostName = "김종명",
                hostNickname = "봉준호",
            ),
            eventInfo = HomeEventInfo(
                eventCategory = EventType.FIRST_BD,
                relationship = RelationType.NEIGHBOR,
                cost = 10000,
                eventDate = LocalDate.of(2025, 2, 11),
            ),
            locationInfo = HomeLocationInfo(
                location = "강남구 테헤란로 강남 웨딩홀"
            ),
        ),
        HomeEvent(
            eventId = "3",
            hostInfo = HomeHostInfo(
                hostName = "김혜정",
                hostNickname = "메정",
            ),
            eventInfo = HomeEventInfo(
                eventCategory = EventType.BIRTHDAY,
                relationship = RelationType.ALUMNI,
                cost = 10000,
                eventDate = LocalDate.of(2025, 2, 11),
            ),
            locationInfo = HomeLocationInfo(
                location = "강남구 테헤란로 강남 웨딩홀"
            ),
        ),
    )

    BongBaekTheme {
        HomeMainSuccessScreen(
            items = items,
            onBadgeClick = {},
            navigateToSchedule = {},
        )
    }
}