package com.bongtu.baekseo.presentation.record

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
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.common.type.EventType
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.core.util.toFormattedDateWithDay
import com.bongtu.baekseo.data.model.RecordEvent
import com.bongtu.baekseo.presentation.record.component.RecordDetailDropDown
import com.bongtu.baekseo.presentation.record.type.AttendType
import java.time.LocalDate

private const val MEMO_RATIO = 320f / 152f

@Composable
private fun RecordDetailScreen(
    onBackButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {/* TODO: state 수정 예정 임시 더미 데이터 사용 */
    val event = RecordEvent(
        eventId = "eventId",
        hostName = "김봉백",
        hostNickName = "봉봉",
        category = EventType.WEDDING.label,
        relationship = RelationType.FAMILY.label,
        cost = 50000,
        eventDate = LocalDate.of(2024, 6, 10),
    )
    val (location, address) = "주소" to "주소"                                // TODO: RecordEvent 수정 예정
    val (attendLabel, note) = AttendType.ATTEND.label to null as String? // TODO: RecordEvent 수정 예정

    val (noteText, noteTextColor, noteBackgroundColor) = if (note.isNullOrBlank()) {
        Triple(
            stringResource(record_detail_memo_placeholder),
            BongBaekTheme.colors.gray500,
            BongBaekTheme.colors.gray800
        )
    } else {
        Triple(
            note,
            BongBaekTheme.colors.white,
            BongBaekTheme.colors.gray750
        )
    }

    var isDeleteAlertDialogVisible by remember { mutableStateOf(false) }

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

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            RecordDetailTitleCard(
                title = stringResource(
                    record_detail_title_card_title, event.hostName, event.category
                ),
                eventDate = LocalDate.of(2024, 8, 10),
                modifier = Modifier.padding(vertical = 20.dp),
            )

            RecordDetailCostCard(
                cost = event.cost,
            )

            RecordDetailDropDown(
                event = event,
                attendLabel = attendLabel,
                location = location,
                address = address,
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
                    onDeleteClick = { /* TODO: 삭제 로직 구현*/ },
                )
            }
        }
    }
}

@Composable
fun RecordDetailTitleCard(
    title: String,
    eventDate: LocalDate,
    modifier: Modifier = Modifier,
) {
    val (date, weekDay) = eventDate.toFormattedDateWithDay()
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
                        colorStops = arrayOf(
                            0f to bongBaekColors.gradientCostCardBorder,
                            1f to bongBaekColors.primaryNormal,
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
                )
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
        RecordDetailScreen(
            onBackButtonClick = {},
            onEditButtonClick = {},
        )
    }
}