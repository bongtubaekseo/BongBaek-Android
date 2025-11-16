package com.bongtu.baekseo.core.designsystem.component.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_arrow_right
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

/**
 * small badge component
 *
 * @param title - badge title
 */
@Composable
fun BongBaekSmallBadge(
    title: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = BongBaekTheme.colors.bgDisplayChips,
) {
    Row(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(4.dp),
            )
            .padding(
                horizontal = 8.dp,
                vertical = 2.dp,
            ),
    ) {
        Text(
            text = title,
            style = BongBaekTheme.typography.captionRegular12,
            color = BongBaekTheme.colors.statusFocused,
        )
    }
}

/**
 * medium badge component
 *
 * small badge와 다르게 click event를 받습니다.
 *
 * @param title - badge title
 * @param onClick - click event
 */
@Composable
fun BongBaekMediumBadge(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(
                color = BongBaekTheme.colors.bgDisplayPrimary,
                shape = RoundedCornerShape(4.dp),
            )
            .padding(
                start = 10.dp,
                top = 6.dp,
                end = 8.dp,
                bottom = 6.dp,
            )
            .noRippleClickable(onClick),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = BongBaekTheme.typography.captionRegular12,
            color = BongBaekTheme.colors.txtDisplayTertiary,
        )
        Icon(
            imageVector = ImageVector.vectorResource(ic_arrow_right),
            contentDescription = null,
            tint = BongBaekTheme.colors.iconDisabledSecondary,
            modifier = Modifier.size(14.dp),
        )
    }
}

@Preview
@Composable
private fun BongBaekBadgePreview() {
    BongBaekTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            BongBaekSmallBadge(
                title = "경조사 종류",
            )
            BongBaekMediumBadge(
                title = "일정 추가하기",
                onClick = { },
            )
        }
    }
}
