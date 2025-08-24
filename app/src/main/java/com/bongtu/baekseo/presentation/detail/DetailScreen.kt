package com.bongtu.baekseo.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.drawable.ic_edit
import com.bongtu.baekseo.R.string.record_card_cost
import com.bongtu.baekseo.R.string.record_card_weekday
import com.bongtu.baekseo.R.string.record_detail_cost_title
import com.bongtu.baekseo.R.string.record_detail_delete
import com.bongtu.baekseo.R.string.record_detail_delete_dialog_cancel_label
import com.bongtu.baekseo.R.string.record_detail_delete_dialog_delete_label
import com.bongtu.baekseo.R.string.record_detail_delete_dialog_description
import com.bongtu.baekseo.R.string.record_detail_delete_dialog_title
import com.bongtu.baekseo.R.string.record_detail_memo_placeholder
import com.bongtu.baekseo.R.string.record_detail_memo_title
import com.bongtu.baekseo.R.string.record_detail_title
import com.bongtu.baekseo.R.string.record_detail_title_card_title
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.core.util.toFormattedDateAndDay
import com.bongtu.baekseo.data.model.event.DetailEvent
import com.bongtu.baekseo.presentation.detail.DetailContract.DetailSideEffect.NavigateToEdit
import com.bongtu.baekseo.presentation.detail.DetailContract.DetailSideEffect.NavigateToRecord
import com.bongtu.baekseo.presentation.detail.DetailContract.DetailUiState
import com.bongtu.baekseo.presentation.detail.component.DetailDropDown
import com.bongtu.baekseo.presentation.edit.navigation.EditEvent

private const val MEMO_RATIO = 320f / 152f

@Composable
fun DetailRoute(
    navigateUp: () -> Unit,
    navigateToEdit: (EditEvent) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) { viewModel.fetchDetailEvent() }

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .collect { sideEffect ->
                when (sideEffect) {
                    is NavigateToEdit -> navigateToEdit(sideEffect.editEvent)
                    is NavigateToRecord -> navigateUp()
                }
            }
    }

    DetailScreen(
        uiState = uiState,
        onBackButtonClick = navigateUp,
        onEditButtonClick = viewModel::navigateToEdit,
        onRemoveButtonClick = viewModel::removeDetailEvent,
        modifier = modifier,
    )
}

@Composable
private fun DetailScreen(
    uiState: DetailUiState,
    onBackButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    onRemoveButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = BongBaekTheme.colors.gray900)
            .systemBarsPadding(),
    ) {
        BongBaekTopBar(
            title = stringResource(record_detail_title),
            modifier = Modifier,
            topBarType = TopBarType.BOTH_ICONS,
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_arrow_back),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp)
                        .noRippleClickable(onClick = onBackButtonClick),
                    tint = BongBaekTheme.colors.white,
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_edit),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(14.dp)
                        .size(20.dp)
                        .noRippleClickable(onClick = onEditButtonClick),
                    tint = BongBaekTheme.colors.white,
                )
            },
        )

        DetailContent(
            event = DetailEvent(
                eventId = uiState.eventId,
                hostName = uiState.hostName,
                hostNickname = uiState.hostNickname,
                eventCategory = uiState.eventCategory,
                relationship = uiState.relationship,
                cost = uiState.cost,
                isEventParticipated = uiState.isEventParticipated,
                eventDate = uiState.eventDate,
                note = uiState.note,
                locationInfo = uiState.locationInfo,
            ),
            onDeleteButtonClick = { onRemoveButtonClick(uiState.eventId) },
            modifier = Modifier
                .weight(1f),
        )
    }
}

@Composable
private fun DetailContent(
    event: DetailEvent,
    onDeleteButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isDeleteAlertDialogVisible by remember { mutableStateOf(false) }
    val (noteText, noteTextColor, noteBackgroundColor) = if (event.note.isNullOrBlank()) {
        Triple(
            stringResource(record_detail_memo_placeholder),
            BongBaekTheme.colors.gray500,
            BongBaekTheme.colors.gray800
        )
    } else {
        Triple(
            event.note,
            BongBaekTheme.colors.white,
            BongBaekTheme.colors.gray750
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        RecordDetailTitleCard(
            title = stringResource(
                record_detail_title_card_title, event.hostName, event.eventCategory
            ),
            eventDate = event.eventDate,
            modifier = Modifier.padding(vertical = 20.dp),
        )

        RecordDetailCostCard(
            cost = event.cost,
        )

        DetailDropDown(
            event = event,
            modifier = Modifier.padding(vertical = 20.dp),
        )

        Text(
            text = stringResource(record_detail_memo_title),
            color = BongBaekTheme.colors.white,
            style = BongBaekTheme.typography.titleSemiBold18,
            modifier = Modifier
                .padding(
                    bottom = 10.dp,
                )
                .fillMaxWidth(),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 10.dp))
                .background(color = noteBackgroundColor)
                .aspectRatio(MEMO_RATIO),
            contentAlignment = Alignment.TopStart,
        ) {
            Text(
                text = noteText,
                color = noteTextColor,
                style = BongBaekTheme.typography.body2Regular16,
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 16.dp,
                ),
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        BongBaekButton(
            title = stringResource(record_detail_delete),
            onClick = { isDeleteAlertDialogVisible = !isDeleteAlertDialogVisible },
            buttonType = ButtonType.DELETE,
            modifier = Modifier
                .padding(top = 65.dp, bottom = 40.dp)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = BongBaekTheme.colors.secondaryRed,
                    shape = RoundedCornerShape(10.dp),
                ),
        )

        if (isDeleteAlertDialogVisible) {
            RecordDeleteAlertDialog(
                onDismissRequest = { isDeleteAlertDialogVisible = !isDeleteAlertDialogVisible },
                onDeleteClick = onDeleteButtonClick,
            )
        }
    }
}

@Composable
private fun RecordDetailTitleCard(
    title: String,
    eventDate: String,
    modifier: Modifier = Modifier,
) {
    val (date, weekDay) =  remember(eventDate) { eventDate.toFormattedDateAndDay() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 10.dp))
            .background(color = BongBaekTheme.colors.gray750)
            .padding(20.dp),
    ) {
        Text(
            text = title,
            color = BongBaekTheme.colors.white,
            style = BongBaekTheme.typography.titleSemiBold18,
        )
        Row(
            modifier = Modifier.padding(top = 2.dp),
        ) {
            Text(
                text = date,
                color = BongBaekTheme.colors.gray400,
                style = BongBaekTheme.typography.body2Regular14,
                modifier = Modifier.padding(end = 4.dp),
            )
            Text(
                text = stringResource(record_card_weekday, weekDay),
                color = BongBaekTheme.colors.gray400,
                style = BongBaekTheme.typography.body2Regular14,
            )
        }
    }
}

@Composable
private fun RecordDetailCostCard(
    cost: Int,
    modifier: Modifier = Modifier,
) {
    val bongBaekColors = BongBaekTheme.colors
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = remember {
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0.16f to bongBaekColors.primaryNormal,
                            1f to bongBaekColors.gradientCostCardBackGround,
                        ),
                    )
                },
                shape = RoundedCornerShape(10.dp),
            )
            .border(
                brush = remember {
                    Brush.verticalGradient(
                        colors = listOf(
                            bongBaekColors.gradientCostCardBorder,
                            bongBaekColors.primaryNormal,
                        ),
                    )
                },
                width = 1.dp,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(record_detail_cost_title),
                color = BongBaekTheme.colors.white,
                style = BongBaekTheme.typography.titleSemiBold16,
            )
            Text(
                text = stringResource(record_card_cost, cost),
                color = BongBaekTheme.colors.white,
                style = BongBaekTheme.typography.titleSemiBold18,
            )
        }
    }
}

@Composable
private fun RecordDeleteAlertDialog(
    onDismissRequest: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
        ),
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .background(
                    color = BongBaekTheme.colors.gray750,
                    shape = RoundedCornerShape(10.dp),
                )
                .padding(
                    top = 20.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 16.dp,
                ),
        ) {
            Text(
                text = stringResource(record_detail_delete_dialog_title),
                color = BongBaekTheme.colors.white,
                style = BongBaekTheme.typography.titleSemiBold20,
                modifier = Modifier.padding(bottom = 6.dp),
            )
            Text(
                text = stringResource(record_detail_delete_dialog_description),
                color = BongBaekTheme.colors.gray300,
                style = BongBaekTheme.typography.captionRegular12,
                modifier = Modifier.padding(bottom = 10.dp),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                Text(
                    text = stringResource(record_detail_delete_dialog_cancel_label),
                    color = BongBaekTheme.colors.gray300,
                    style = BongBaekTheme.typography.body2Regular16,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .noRippleClickable(onClick = onDismissRequest),
                )
                Text(
                    text = stringResource(record_detail_delete_dialog_delete_label),
                    color = BongBaekTheme.colors.secondaryRed,
                    style = BongBaekTheme.typography.body2Regular16,
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .noRippleClickable(onClick = onDeleteClick),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecordDetailScreenPreview() {
    BongBaekTheme {
        DetailScreen(
            uiState = DetailUiState(UiState.Loading),
            onBackButtonClick = {},
            onEditButtonClick = {},
            onRemoveButtonClick = {},
        )
    }
}