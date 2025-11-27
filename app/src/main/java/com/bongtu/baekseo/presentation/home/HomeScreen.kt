package com.bongtu.baekseo.presentation.home

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_arrow_right
import com.bongtu.baekseo.R.drawable.ic_home_logo
import com.bongtu.baekseo.R.drawable.ic_home_name
import com.bongtu.baekseo.R.string.home_contents_description
import com.bongtu.baekseo.R.string.home_contents_title
import com.bongtu.baekseo.R.string.home_schedule_description
import com.bongtu.baekseo.R.string.home_schedule_more
import com.bongtu.baekseo.R.string.home_schedule_title
import com.bongtu.baekseo.R.string.market_url
import com.bongtu.baekseo.core.common.type.DialogType
import com.bongtu.baekseo.core.compositionlocal.safeDrawingWithBottomNavBar
import com.bongtu.baekseo.core.designsystem.component.dialog.BongBaekDialog
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.excludeTop
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.core.util.openUrl
import com.bongtu.baekseo.data.model.content.Contents
import com.bongtu.baekseo.data.model.event.HomeEvent
import com.bongtu.baekseo.presentation.home.component.HomeAlarmCard
import com.bongtu.baekseo.presentation.home.component.HomeAlarmEmptyCard
import com.bongtu.baekseo.presentation.home.component.HomeContentCard
import com.bongtu.baekseo.presentation.home.component.HomeEventCard
import com.bongtu.baekseo.presentation.home.component.HomeEventEmptyCard
import com.bongtu.baekseo.presentation.home.component.HomeRecommendCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomeRoute(
    navigateToRecord: () -> Unit,
    navigateToRecommend: () -> Unit,
    navigateToContents: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isUpdateDialogVisible by viewModel.isUpdateDialogVisible.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val packageName = context.packageName
    val marketUrl = stringResource(market_url, packageName)

    LaunchedEffect(Unit) {
        viewModel.fetchHomeEvent()
        viewModel.fetchHomeContent()
    }

    HomeScreen(
        eventList = uiState.homeEventList,
        contentsList = uiState.contentList,
        navigateToRecommend = navigateToRecommend,
        navigateToRecord = navigateToRecord,
        navigateToContents = navigateToContents,
        modifier = modifier,
    )

    if (isUpdateDialogVisible) {
        BongBaekDialog(
            dialogType = DialogType.ERROR_UPDATE,
            onDismissRequest = { (context as? Activity)?.finish() },
            onConfirmClick = { context.openUrl(marketUrl) },
        )
    }
}

@Composable
fun HomeScreen(
    eventList: ImmutableList<HomeEvent>,
    contentsList: ImmutableList<Contents>,
    navigateToRecord: () -> Unit,
    navigateToRecommend: () -> Unit,
    navigateToContents: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(color = BongBaekTheme.colors.bgDisplayPrimary)
            .windowInsetsPadding(WindowInsets.safeDrawingWithBottomNavBar.excludeTop())
            .verticalScroll(rememberScrollState()),
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(
                    top = 32.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 30.dp,
                ),
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        bottom = 12.dp,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_home_logo),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )

                Spacer(modifier = Modifier.size(4.dp))

                Icon(
                    imageVector = ImageVector.vectorResource(ic_home_name),
                    contentDescription = null,
                    tint = BongBaekTheme.colors.txtDisplayPrimary,
                )
            }

            if (eventList.isNotEmpty()) {
                val alarmCardData = eventList.first()

                HomeAlarmCard(
                    hostname = alarmCardData.hostName,
                    eventType = alarmCardData.eventCategory,
                    daysLeft = alarmCardData.dDay,
                    eventDate = alarmCardData.eventDate,
                    modifier = Modifier.padding(vertical = 20.dp),
                )
            } else {
                HomeAlarmEmptyCard(
                    modifier = Modifier.padding(vertical = 20.dp),
                )
            }

            Row(
                modifier = Modifier.padding(
                    top = 20.dp,
                    bottom = 18.dp,
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = stringResource(id = home_schedule_title),
                        style = BongBaekTheme.typography.titleSemiBold20,
                        color = BongBaekTheme.colors.txtDisplayPrimary,
                    )

                    Spacer(modifier = Modifier.size(2.dp))

                    Text(
                        text = stringResource(id = home_schedule_description),
                        style = BongBaekTheme.typography.captionRegular12,
                        color = BongBaekTheme.colors.txtDisplayTertiary,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                if (eventList.isNotEmpty()) {
                    Row(
                        modifier = Modifier.noRippleClickable(navigateToRecord),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(id = home_schedule_more),
                            style = BongBaekTheme.typography.body2Regular14,
                            color = BongBaekTheme.colors.txtDisplaySecondary,
                        )

                        Icon(
                            imageVector = ImageVector.vectorResource(id = ic_arrow_right),
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = BongBaekTheme.colors.iconInteractiveDefault,
                        )
                    }
                }
            }

            eventList.forEachIndexed { index, item ->
                HomeEventCard(
                    hostName = item.hostName,
                    hostNickname = item.hostNickname,
                    eventCategory = item.eventCategory,
                    relationship = item.relationship,
                    cost = item.cost,
                    eventDate = item.eventDate,
                    location = item.location,
                    modifier = Modifier.padding(
                        bottom = if (index == eventList.lastIndex) 0.dp else 12.dp,
                    ),
                )
            }

            if (eventList.isEmpty()) {
                HomeEventEmptyCard(
                    onBadgeClick = navigateToRecord,
                )
            }
        }

        Spacer(
            modifier = Modifier
                .background(color = BongBaekTheme.colors.borderDisplayTitle)
                .fillMaxWidth()
                .height(10.dp),
        )

        Column(
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    start = 20.dp,
                    end = 20.dp,
                ),
        ) {
            HomeRecommendCard(
                onClick = navigateToRecommend,
                modifier = Modifier.padding(vertical = 20.dp),
            )

            Row(
                modifier = Modifier.padding(
                    top = 30.dp,
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = stringResource(id = home_contents_title),
                        style = BongBaekTheme.typography.titleSemiBold20,
                        color = BongBaekTheme.colors.txtDisplayPrimary,
                    )

                    Spacer(modifier = Modifier.size(2.dp))

                    Text(
                        text = stringResource(id = home_contents_description),
                        style = BongBaekTheme.typography.captionRegular12,
                        color = BongBaekTheme.colors.txtDisplayTertiary,
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.noRippleClickable(navigateToContents),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(id = home_schedule_more),
                        style = BongBaekTheme.typography.body2Regular14,
                        color = BongBaekTheme.colors.txtDisplaySecondary,
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(id = ic_arrow_right),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = BongBaekTheme.colors.iconInteractiveDefault,
                    )
                }
            }
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    bottom = 50.dp,
                ),
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = contentsList,
                key = { content -> content.contentId },
            ) { item ->
                HomeContentCard(
                    contentTitle = item.contentTitle,
                    contentCategory = item.contentCategory,
                    thumbnailUrl = item.thumbnailUrl,
                    modifier = Modifier.width(220.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val eventList = persistentListOf(
        HomeEvent(
            eventId = "eventId1",
            hostName = "일이삼사오육칠팔구십",
            hostNickname = "하하",
            eventCategory = "생일",
            relationship = "친구",
            cost = 10000,
            eventDate = "2026-02-18",
            dDay = 1,
            location = "강남",
        ),
        HomeEvent(
            eventId = "eventId2",
            hostName = "헤헤",
            hostNickname = "하하",
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
            hostNickname = "하하",
            eventCategory = "생일",
            relationship = "친구",
            cost = 10000,
            eventDate = "2025-02-11",
            dDay = 1,
            location = "강남",
        ),
    )

    val contentsList = persistentListOf(
        Contents(
            contentId = "contentId1",
            contentTitle = "contentTitle1",
            contentCategory = "contentCategory1",
            thumbnailUrl = "https://i.ifh.cc/TX21OR.jpg",
        ),
        Contents(
            contentId = "contentId2",
            contentTitle = "contentTitle2",
            contentCategory = "contentCategory2",
            thumbnailUrl = "https://i.ifh.cc/TX21OR.jpg",
        ),
        Contents(
            contentId = "contentId3",
            contentTitle = "contentTitle3",
            contentCategory = "contentCategory3",
            thumbnailUrl = "https://i.ifh.cc/TX21OR.jpg",
        ),
    )

    BongBaekTheme {
        HomeScreen(
            eventList = eventList,
            contentsList = contentsList,
            navigateToRecord = {},
            navigateToRecommend = {},
            navigateToContents = {},
        )
    }
}
