package com.bongtu.baekseo.presentation.recommend

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.string.button_next
import com.bongtu.baekseo.R.string.recommendation_event_date_description
import com.bongtu.baekseo.R.string.recommendation_event_date_title
import com.bongtu.baekseo.R.string.recommendation_event_date_topbar
import com.bongtu.baekseo.R.string.recommendation_event_location_button
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
import com.bongtu.baekseo.data.model.map.Place
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendSideEffect
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendSideEffect.MainSideEffect.NavigateToLoading
import com.bongtu.baekseo.presentation.recommend.RecommendContract.RecommendUiState
import com.bongtu.baekseo.presentation.recommend.component.RecommendDateCard
import com.bongtu.baekseo.presentation.recommend.component.RecommendEventLocationContent
import com.bongtu.baekseo.presentation.recommend.component.RecommendEventSelector
import com.bongtu.baekseo.presentation.recommend.component.RecommendParticipationCard
import com.bongtu.baekseo.presentation.recommend.component.RecommendProgressBar
import com.bongtu.baekseo.presentation.recommend.component.RecommendRelationTypeContent
import kotlinx.coroutines.flow.filterIsInstance

@Composable
fun RecommendMainRoute(
    navigateToUp: () -> Unit,
    navigateToLoading: () -> Unit,
    viewModel: RecommendViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchTerm by viewModel.searchTerm.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val onBackClick: () -> Unit = {
        if (uiState.pageIndex == 1) navigateToUp()
        else viewModel.updatePageIndex(uiState.pageIndex - 1)
    }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .filterIsInstance<RecommendSideEffect.MainSideEffect>()
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToLoading -> navigateToLoading()
                }
            }
    }

    BackHandler {
        onBackClick()
    }

    RecommendMainScreen(
        uiState = uiState,
        searchTerm = searchTerm,
        onSearchTermChange = viewModel::updateSearchTerm,
        onBackClick = onBackClick,
        fetchExpense = viewModel::fetchExpense,
        onPageIndexChange = viewModel::updatePageIndex,
        onNameChange = viewModel::updateName,
        onNicknameChange = viewModel::updateNickname,
        onRelationSelect = viewModel::updateRelationType,
        onCheckBoxClick = viewModel::updateIsHighAccuracy,
        onContactFrequencyChange = viewModel::updateContactFrequency,
        onMeetFrequencyChange = viewModel::updateMeetFrequency,
        onEventSelect = viewModel::updateEventType,
        onDateChange = viewModel::updateEventDate,
        onParticipationSelect = viewModel::updateIsEventParticipated,
        onLocationSelect = viewModel::updateEventLocation,
        checkButtonEnabled = viewModel::updateButtonState,
        modifier = modifier,
    )
}

@Composable
private fun RecommendMainScreen(
    uiState: RecommendUiState,
    searchTerm: String,
    onSearchTermChange: (String) -> Unit,
    onBackClick: () -> Unit,
    fetchExpense: () -> Unit,
    onPageIndexChange: (Int) -> Unit,
    onNameChange: (String) -> Unit,
    onNicknameChange: (String) -> Unit,
    onRelationSelect: (RelationType) -> Unit,
    onCheckBoxClick: (Boolean) -> Unit,
    onContactFrequencyChange: (Float) -> Unit,
    onMeetFrequencyChange: (Float) -> Unit,
    onEventSelect: (EventType) -> Unit,
    onDateChange: (String) -> Unit,
    onParticipationSelect: (Boolean) -> Unit,
    onLocationSelect: (Place?) -> Unit,
    checkButtonEnabled: () -> Boolean,
    modifier: Modifier = Modifier,
) {
    var isTitleVisible by remember { mutableStateOf(true) }
    var text by remember { mutableStateOf("") }
    val (topbarRes, titleRes, descRes) = when (uiState.pageIndex) {
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
    val buttonTitleRes = remember(uiState.pageIndex) {
        when (uiState.pageIndex) {
            4 -> recommendation_event_location_button
            else -> button_next
        }
    }
    val scrollState = rememberScrollState()
    val isButtonEnabled = remember(
        uiState.pageIndex,
        uiState.name,
        uiState.nickname,
        uiState.relationType,
        uiState.isHighAccuracy,
        uiState.contactFrequency,
        uiState.meetFrequency,
        uiState.eventType,
        uiState.eventDate,
        uiState.isEventParticipated,
        uiState.selectedPlace,
    ) { checkButtonEnabled() }

    LaunchedEffect(uiState.pageIndex) {
        isTitleVisible = true
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
                        .noRippleClickable(onBackClick),
                    tint = BongBaekTheme.colors.white,
                )
            },
        )

        Spacer(modifier = Modifier.height(8.dp))

        RecommendProgressBar(
            currentIndex = uiState.pageIndex,
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 28.dp,
                ),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .then(
                    if (uiState.pageIndex == 1) Modifier.verticalScroll(scrollState)
                    else Modifier
                ),
        ) {
            AnimatedVisibility(
                visible = isTitleVisible,
            ) {
                Column {
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

                        if (uiState.pageIndex == 4) {
                            Text(
                                text = stringResource(recommendation_event_location_skip),
                                style = BongBaekTheme.typography.body2Regular14,
                                color = BongBaekTheme.colors.gray500,
                                modifier = Modifier
                                    .noRippleClickable {
                                        onLocationSelect(null)
                                        fetchExpense()
                                    },
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = stringResource(descRes),
                        style = BongBaekTheme.typography.body2Regular14,
                        color = BongBaekTheme.colors.gray400,
                    )
                    if (uiState.pageIndex == 4) Spacer(modifier = Modifier.height(20.dp))
                    else Spacer(modifier = Modifier.height(30.dp))
                }
            }

            AnimatedContent(
                targetState = uiState.pageIndex,
            ) { page ->
                when (page) {
                    1 -> RecommendRelationTypeContent(
                        name = uiState.name,
                        onNameChange = onNameChange,
                        nameError = uiState.nameError,
                        nickname = uiState.nickname,
                        onNicknameChange = onNicknameChange,
                        nicknameError = uiState.nicknameError,
                        onFocusChange = { isTitleVisible = !it },
                        selectedRelation = uiState.relationType,
                        onRelationSelect = onRelationSelect,
                        isChecked = uiState.isHighAccuracy,
                        onCheckBoxClick = onCheckBoxClick,
                        contactFrequency = uiState.contactFrequency,
                        onContactFrequencyChange = onContactFrequencyChange,
                        meetFrequency = uiState.meetFrequency,
                        onMeetFrequencyChange = onMeetFrequencyChange,
                    )

                    2 -> RecommendEventSelector(
                        selectedEvent = uiState.eventType,
                        onEventSelect = onEventSelect,
                    )

                    3 -> {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(14.dp),
                        ) {
                            RecommendDateCard(
                                date = uiState.eventDate,
                                text = text,
                                onFocusChange = { isTitleVisible = it },
                                onTextChange = { text = it },
                                onConfirmClick = {
                                    onDateChange(text)
                                    text = ""
                                },
                            )

                            RecommendParticipationCard(
                                isEventParticipated = uiState.isEventParticipated,
                                onParticipationSelect = onParticipationSelect,
                            )
                        }
                    }

                    4 -> RecommendEventLocationContent(
                        selectedPlace = uiState.selectedPlace,
                        onPlaceSelect = onLocationSelect,
                        searchValue = searchTerm,
                        onSearchValueChange = onSearchTermChange,
                        searchResult = uiState.searchResult,
                        onFocusChange = { isTitleVisible = !it },
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            BongBaekButton(
                title = stringResource(buttonTitleRes),
                onClick = {
                    if (uiState.pageIndex == 4) fetchExpense()
                    else onPageIndexChange(uiState.pageIndex + 1)
                },
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 24.dp,
                    )
                    .navigationBarsPadding(),
                enabled = isButtonEnabled,
            )
        }
    }
}

@Preview
@Composable
private fun RecommendScreenPreview() {
    BongBaekTheme {
        RecommendMainScreen(
            uiState = RecommendUiState(),
            searchTerm = "",
            onSearchTermChange = {},
            onBackClick = {},
            fetchExpense = {},
            onPageIndexChange = {},
            onNameChange = {},
            onNicknameChange = {},
            onRelationSelect = {},
            onCheckBoxClick = {},
            onContactFrequencyChange = {},
            onMeetFrequencyChange = {},
            onEventSelect = {},
            onDateChange = {},
            onParticipationSelect = {},
            onLocationSelect = {},
            checkButtonEnabled = { true },
        )
    }
}
