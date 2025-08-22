package com.bongtu.baekseo.presentation.record.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_arrow_back
import com.bongtu.baekseo.R.drawable.ic_delete
import com.bongtu.baekseo.R.drawable.ic_plus
import com.bongtu.baekseo.R.string.record_top_bar_delete_button
import com.bongtu.baekseo.R.string.record_top_bar_delete_title
import com.bongtu.baekseo.R.string.record_top_bar_title
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

@Composable
fun RecordTopBar(
    isDeleteMode: Boolean,
    isDeleteButtonEnabled: Boolean,
    navigateToAdd: () -> Unit,
    onEnterDeleteModeClick: () -> Unit,
    onExitDeleteModeClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (title, topBarType) = when (isDeleteMode) {
        true -> record_top_bar_delete_title to TopBarType.BOTH_ICONS
        false -> record_top_bar_title to TopBarType.TRAILING_ICON
    }

    BongBaekTopBar(
        modifier = modifier
            .statusBarsPadding(),
        title = stringResource(title),
        topBarType = topBarType,
        leadingIcon = if (isDeleteMode) {
            {
                TopBarDeleteLeadingIcon(
                    onExitDeleteModeClick = onExitDeleteModeClick
                )
            }
        } else null,
        trailingIcon = if (!isDeleteMode) {
            {
                TopBarDefaultTrailingIcon(
                    onAddButtonClick = navigateToAdd,
                    onEnterDeleteModeClick = onEnterDeleteModeClick,
                )
            }
        } else {
            {
                TopBarDeleteTrailingIcon(
                    isDeleteButtonEnabled = isDeleteButtonEnabled,
                    onDeleteClick = onDeleteClick,
                )
            }
        },
    )
}

@Composable
private fun TopBarDefaultTrailingIcon(
    onAddButtonClick: () -> Unit,
    onEnterDeleteModeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(ic_plus),
            contentDescription = null,
            modifier = Modifier
                .padding(14.dp)
                .size(20.dp)
                .noRippleClickable(onClick = onAddButtonClick),
            tint = BongBaekTheme.colors.gray400,
        )
        Icon(
            imageVector = ImageVector.vectorResource(ic_delete),
            contentDescription = null,
            modifier = Modifier
                .padding(14.dp)
                .size(20.dp)
                .noRippleClickable(onClick = onEnterDeleteModeClick),
            tint = BongBaekTheme.colors.gray400,
        )
    }
}

@Composable
private fun TopBarDeleteLeadingIcon(
    onExitDeleteModeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = ImageVector.vectorResource(ic_arrow_back),
        contentDescription = null,
        modifier = modifier
            .padding(12.dp)
            .noRippleClickable(onClick = onExitDeleteModeClick),
        tint = BongBaekTheme.colors.white,
    )
}

@Composable
private fun TopBarDeleteTrailingIcon(
    isDeleteButtonEnabled: Boolean,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = BongBaekTheme.colors
    val textColor = remember(isDeleteButtonEnabled) {
        if (isDeleteButtonEnabled) {
            colors.secondaryRed
        } else {
            colors.gray500
        }
    }

    Text(
        text = stringResource(record_top_bar_delete_button),
        style = BongBaekTheme.typography.titleSemiBold16,
        color = textColor,
        modifier = modifier
            .padding(vertical = 12.dp, horizontal = 10.dp)
            .padding(end = 8.dp)
            .noRippleClickable(onClick = {
                if (isDeleteButtonEnabled) {
                    onDeleteClick()
                }
            }),
    )
}