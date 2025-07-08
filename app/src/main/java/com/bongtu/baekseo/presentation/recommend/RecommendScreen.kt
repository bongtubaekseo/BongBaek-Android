package com.bongtu.baekseo.presentation.recommend

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.string.button_next
import com.bongtu.baekseo.R.string.recommendation_event_date_description
import com.bongtu.baekseo.R.string.recommendation_event_date_title
import com.bongtu.baekseo.R.string.recommendation_event_date_topbar
import com.bongtu.baekseo.R.string.recommendation_event_location_description
import com.bongtu.baekseo.R.string.recommendation_event_location_skip
import com.bongtu.baekseo.R.string.recommendation_event_location_title
import com.bongtu.baekseo.R.string.recommendation_event_location_topbar
import com.bongtu.baekseo.R.string.recommendation_event_type_description
import com.bongtu.baekseo.R.string.recommendation_event_type_title
import com.bongtu.baekseo.R.string.recommendation_event_type_topbar
import com.bongtu.baekseo.R.string.recommendation_relation_description
import com.bongtu.baekseo.R.string.recommendation_relation_title
import com.bongtu.baekseo.R.string.recommendation_relation_topbar
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.presentation.recommend.component.RecommendDateCard
import com.bongtu.baekseo.presentation.recommend.component.RecommendEventLocationContent
import com.bongtu.baekseo.presentation.recommend.component.RecommendEventSelector
import com.bongtu.baekseo.presentation.recommend.component.RecommendParticipationCard
import com.bongtu.baekseo.presentation.recommend.component.RecommendProgressBar
import com.bongtu.baekseo.presentation.recommend.component.RecommendRelationTypeContent

@Composable
fun RecommendRoute(
    modifier: Modifier = Modifier,
) {
    RecommendScreen(
        modifier = modifier,
    )
}

@Composable
private fun RecommendScreen(
    modifier: Modifier = Modifier,
) {
    // TODO: UIState로 수정
    var currentPageIndex by remember { mutableStateOf(1) }
    var selectedRelation by remember { mutableStateOf<RelationType?>(null) }
    var name by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }
    var contactFrequency by remember { mutableStateOf(0f) }
    var meetFrequency by remember { mutableStateOf(0f) }
    var selectedEvent by remember { mutableStateOf<EventType?>(null) }
    var date by remember { mutableStateOf("") }
    var text by remember { mutableStateOf("") }
    var isEventParticipated by remember { mutableStateOf<Boolean?>(null) }
    var searchValue by remember { mutableStateOf("") }

    val (topbarRes, titleRes, descRes) = when (currentPageIndex) {
        1 -> Triple(
            recommendation_relation_topbar,
            recommendation_relation_title,
            recommendation_relation_description,
        )

        2 -> Triple(
            recommendation_event_type_topbar,
            recommendation_event_type_title,
            recommendation_event_type_description,
        )

        3 -> Triple(
            recommendation_event_date_topbar,
            recommendation_event_date_title,
            recommendation_event_date_description,
        )

        else -> Triple(
            recommendation_event_location_topbar,
            recommendation_event_location_title,
            recommendation_event_location_description,
        )
    }

    Column(
        modifier = modifier
            .background(
                color = BongBaekTheme.colors.gray900,
            )
            .systemBarsPadding(),
    ) {
        BongBaekTopBar(
            title = stringResource(topbarRes),
            topBarType = TopBarType.LEADING_ICON,
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp)
                        .noRippleClickable {
                            // TODO: navigateUp 대체
                            currentPageIndex--
                        },
                    tint = BongBaekTheme.colors.white,
                )
            },
        )

        Spacer(modifier = Modifier.height(8.dp))

        RecommendProgressBar(
            currentIndex = currentPageIndex,
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 28.dp
                ),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(titleRes),
                    style = BongBaekTheme.typography.headBold24,
                    color = BongBaekTheme.colors.white,
                )

                if (currentPageIndex == 4) {
                    Text(
                        text = stringResource(recommendation_event_location_skip),
                        style = BongBaekTheme.typography.body2Regular14,
                        color = BongBaekTheme.colors.gray500,
                        modifier = Modifier
                            .noRippleClickable {
                                // TODO: skip
                            },
                    )
                }
            }


            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = stringResource(descRes),
                style = BongBaekTheme.typography.body2Regular14,
                color = BongBaekTheme.colors.white,
            )

            if (currentPageIndex == 4) Spacer(modifier = Modifier.height(20.dp))
            else Spacer(modifier = Modifier.height(30.dp))

            AnimatedContent(
                targetState = currentPageIndex,
            ) { page ->
                when (page) {
                    // TODO: 내부 로직 수정 필요 -> viewModel
                    1 -> RecommendRelationTypeContent(
                        selectedRelation = selectedRelation,
                        onRelationSelect = { selectedRelation = it },
                        name = name,
                        onNameChange = { name = it },
                        nickname = nickname,
                        onNicknameChange = { nickname = it },
                        isChecked = isChecked,
                        onCheckBoxClick = { isChecked = it },
                        contactFrequency = contactFrequency,
                        onContactFrequencyChange = { contactFrequency = it },
                        meetFrequency = meetFrequency,
                        onMeetFrequencyChange = { meetFrequency = it },
                    )

                    2 -> RecommendEventSelector(
                        selectedEvent = selectedEvent,
                        onEventSelect = { selectedEvent = it },
                    )

                    3 -> {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(14.dp),
                        ) {
                            RecommendDateCard(
                                date = date,
                                text = text,
                                onTextChange = { text = it },
                                onConfirmClick = {
                                    // TODO: date 검증 후 date에 값 update 해주기
                                },
                            )

                            RecommendParticipationCard(
                                isEventParticipated = isEventParticipated,
                                onParticipationSelect = { isEventParticipated = it },
                            )
                        }
                    }

                    4 -> RecommendEventLocationContent(
                        searchValue = searchValue,
                        onSearchValueChange = { searchValue = it },
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            BongBaekButton(
                title = stringResource(button_next),
                onClick = {
                    // TODO: onNext로 대체 필요
                    currentPageIndex++
                    if (currentPageIndex == 5) currentPageIndex = 1
                },
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 24.dp,
                    )
                    .navigationBarsPadding(),
            )
        }
    }
}

@Preview
@Composable
private fun RecommendScreenPreview() {
    BongBaekTheme {
        RecommendScreen()
    }
}
