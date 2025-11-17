package com.bongtu.baekseo.presentation.recommend.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_check_primary
import com.bongtu.baekseo.R.string.recommendation_participation_no
import com.bongtu.baekseo.R.string.recommendation_participation_title
import com.bongtu.baekseo.R.string.recommendation_participation_yes
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

@Composable
fun RecommendParticipationCard(
    isEventParticipated: Boolean?,
    onParticipationSelect: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = BongBaekTheme.colors.bgDisplayCard,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(20.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_check_primary),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            Text(
                text = stringResource(recommendation_participation_title),
                style = BongBaekTheme.typography.titleSemiBold18,
                color = BongBaekTheme.colors.txtDisplaySecondary,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SelectorItem(
                title = stringResource(recommendation_participation_yes),
                isSelected = isEventParticipated == true,
                onSelect = { onParticipationSelect(true) },
                modifier = Modifier.weight(1f),
            )

            SelectorItem(
                title = stringResource(recommendation_participation_no),
                isSelected = isEventParticipated == false,
                onSelect = { onParticipationSelect(false) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun SelectorItem(
    title: String,
    isSelected: Boolean?,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (backgroundColor, borderColor, textColor) =
        if (isSelected == true) Triple(
            BongBaekTheme.colors.statusFocused,
            BongBaekTheme.colors.statusFocused,
            BongBaekTheme.colors.txtInteractiveInverse,
        )
        else Triple(
            BongBaekTheme.colors.btnInteractivePrimary,
            BongBaekTheme.colors.borderFieldDefault,
            BongBaekTheme.colors.txtInteractivePrimary,
        )

    val textStyle =
        if (isSelected == true) BongBaekTheme.typography.body1Medium16
        else BongBaekTheme.typography.body2Regular16

    Text(
        text = title,
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp),
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(vertical = 12.dp)
            .noRippleClickable(onSelect),
        style = textStyle,
        color = textColor,
        textAlign = TextAlign.Center,
    )
}

@Preview
@Composable
private fun RecommendParticipationCardPreview() {
    BongBaekTheme {
        var isEventParticipated by remember { mutableStateOf<Boolean?>(null) }

        RecommendParticipationCard(
            isEventParticipated = isEventParticipated,
            onParticipationSelect = { isEventParticipated = it },
        )
    }
}
