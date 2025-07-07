package com.bongtu.baekseo.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_home_plus
import com.bongtu.baekseo.R.string.badge_schedule_empty
import com.bongtu.baekseo.R.string.home_schedule_empty
import com.bongtu.baekseo.core.designsystem.component.badge.BongBaekMediumBadge
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun HomeScheduleEmptyCard(
    onBadgeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 34.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = ic_home_plus),
            contentDescription = null,
            tint = Color.Unspecified,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(id = home_schedule_empty),
            style = BongBaekTheme.typography.titleSemiBold18, // TODO: typography 변경
            color = BongBaekTheme.colors.white,
        )

        Spacer(modifier = Modifier.height(8.dp))

        BongBaekMediumBadge(
            title = stringResource(id = badge_schedule_empty),
            onClick = onBadgeClick,
        )
    }
}

@Preview
@Composable
private fun HomeScheduleEmptyCardPreview() {
    BongBaekTheme {
        HomeScheduleEmptyCard(
            onBadgeClick = { },
        )
    }
}