package com.bongtu.baekseo.presentation.recommend.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_recommend_colleague
import com.bongtu.baekseo.R.drawable.ic_recommend_family
import com.bongtu.baekseo.R.drawable.ic_recommend_friends
import com.bongtu.baekseo.R.drawable.ic_recommend_neighbor
import com.bongtu.baekseo.R.drawable.ic_recommend_others
import com.bongtu.baekseo.R.drawable.ic_recommend_work
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable

@Composable
fun RecommendRelationSelector(
    selectedRelation: RelationType?,
    onRelationSelect: (RelationType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        RelationType.entries.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                rowItems.forEach { item ->
                    RelationSelectorItem(
                        relation = item,
                        isSelected = selectedRelation == item,
                        modifier = Modifier
                            .weight(1f)
                            .noRippleClickable {
                                onRelationSelect(item)
                            },
                    )
                }
            }
        }
    }
}

@Composable
private fun RelationSelectorItem(
    relation: RelationType,
    isSelected: Boolean?,
    modifier: Modifier = Modifier,
) {
    val bongBaekColors = BongBaekTheme.colors
    val (backgroundColor, borderColor) =
        if (isSelected == true) bongBaekColors.statusFocused to bongBaekColors.statusFocused
        else bongBaekColors.btnInteractiveTertiary to bongBaekColors.borderFieldDefault
    val (contentColor, iconColor) =
        if (isSelected == true) bongBaekColors.iconInteractiveInverse to bongBaekColors.txtInteractiveInverse
        else bongBaekColors.txtInteractiveSecondary to Color.Unspecified
    val iconRes = when (relation) {
        RelationType.FAMILY -> ic_recommend_family
        RelationType.FRIEND -> ic_recommend_friends
        RelationType.COWORKER -> ic_recommend_work
        RelationType.ALUMNI -> ic_recommend_colleague
        RelationType.NEIGHBOR -> ic_recommend_neighbor
        RelationType.ETC -> ic_recommend_others
    }

    Column(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(10.dp),
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(10.dp),
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 8.dp),
            tint = iconColor,
        )

        Text(
            text = relation.label,
            modifier = Modifier
                .padding(bottom = 10.dp),
            style = BongBaekTheme.typography.body1Medium14,
            color = contentColor,
        )
    }
}

@Preview
@Composable
private fun RecommendRelationSelectorPreview() {
    BongBaekTheme {
        var selectedRelation by remember { mutableStateOf<RelationType?>(null) }

        RecommendRelationSelector(
            selectedRelation = selectedRelation,
            onRelationSelect = { selectedRelation = it },
            modifier = Modifier
                .background(BongBaekTheme.colors.bgDisplayPrimary)
                .fillMaxWidth(),
        )
    }
}
