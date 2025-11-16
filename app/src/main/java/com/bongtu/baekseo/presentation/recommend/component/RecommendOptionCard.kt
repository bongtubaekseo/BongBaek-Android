package com.bongtu.baekseo.presentation.recommend.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.string.recommendation_select
import com.bongtu.baekseo.R.string.recommendation_select_description
import com.bongtu.baekseo.R.string.recommendation_select_title
import com.bongtu.baekseo.core.common.type.CheckBoxType
import com.bongtu.baekseo.core.designsystem.component.badge.BongBaekSmallBadge
import com.bongtu.baekseo.core.designsystem.component.checkbox.BongBaekCheckBox
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun RecommendOptionCard(
    isChecked: Boolean,
    onCheckBoxClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (cardBackground, badgeBackground, textColor) =
        if (isChecked) Triple(
            BongBaekTheme.colors.statusFocused,
            BongBaekTheme.colors.bgDisplayCard,
            BongBaekTheme.colors.txtDisplaySecondary,
        )
        else Triple(
            BongBaekTheme.colors.bgDisplayCard,
            BongBaekTheme.colors.btnInteractiveDisabled,
            BongBaekTheme.colors.txtDisplayTertiary,
        )

    Row(
        modifier = modifier
            .background(
                color = cardBackground,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(
                horizontal = 20.dp,
                vertical = 18.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BongBaekSmallBadge(
                    title = stringResource(recommendation_select),
                    backgroundColor = badgeBackground,
                )

                Text(
                    text = stringResource(recommendation_select_title),
                    style = BongBaekTheme.typography.titleSemiBold16,
                    color = BongBaekTheme.colors.txtDisplaySecondary,
                )
            }

            Text(
                text = stringResource(recommendation_select_description),
                style = BongBaekTheme.typography.body2Regular14,
                color = textColor,
            )
        }

        Spacer(modifier = Modifier.width(24.dp))

        BongBaekCheckBox(
            isChecked = isChecked,
            onCheckedChange = onCheckBoxClick,
            checkBoxType = CheckBoxType.GRAY,
        )
    }
}

@Preview
@Composable
private fun RecommendOptionCardPreview() {
    BongBaekTheme {
        var isChecked by remember { mutableStateOf(false) }

        RecommendOptionCard(
            isChecked = isChecked,
            onCheckBoxClick = { isChecked = it },
        )
    }
}
