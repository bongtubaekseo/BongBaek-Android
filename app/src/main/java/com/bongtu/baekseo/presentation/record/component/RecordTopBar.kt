package com.bongtu.baekseo.presentation.record.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
    isDeleting: Boolean,
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit = {},
    onDeleteButtonClick: () -> Unit = {},
    onDeletingDeleteButtonClick: () -> Unit = {},
    isDeletable: Boolean = false,
) {
    val (title, topBarType) = when (isDeleting) {
        true -> record_top_bar_delete_title to TopBarType.BOTH_ICONS
        false -> record_top_bar_title to TopBarType.TRAILING_ICON
    }

    BongBaekTopBar(
        modifier = modifier,
        title = stringResource(title),
        topBarType = topBarType,
        leadingIcon = if (isDeleting) {
            { TopBarLeadingIcon(onBackButtonClick = onBackButtonClick) }
        } else null,
        trailingIcon = if (!isDeleting) {
            {
                TopBarDefaultTrailingIcon(
                    onAddButtonClick = {},
                    onDeleteButtonClick = onDeleteButtonClick,
                )
            }
        } else {
            {
                val colors = BongBaekTheme.colors
                val textColor = remember(isDeletable) {
                    if (isDeletable) {
                        colors.secondaryRed
                    } else {
                        colors.gray500
                    }
                }
                Text(
                    text = stringResource(record_top_bar_delete_button),
                    style = BongBaekTheme.typography.titleSemiBold16,
                    color = textColor,
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 10.dp)
                        .padding(end = 8.dp)
                        .noRippleClickable(onClick = {
                            if (isDeletable) {
                                onDeletingDeleteButtonClick()
                            }
                        }),
                )
            }
        },
    )
}

@Composable
private fun TopBarLeadingIcon(
    onBackButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = ImageVector.vectorResource(ic_arrow_back),
        contentDescription = null,
        modifier = modifier
            .padding(12.dp)
            .noRippleClickable(onClick = onBackButtonClick),
        tint = BongBaekTheme.colors.white,
    )
}

@Composable
private fun TopBarDefaultTrailingIcon(
    onAddButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
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
            tint = BongBaekTheme.colors.gray400,
            modifier = Modifier
                .padding(14.dp)
                .size(20.dp)
                .noRippleClickable(onClick = onDeleteButtonClick),
        )
    }
}