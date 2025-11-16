package com.bongtu.baekseo.presentation.recommend.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_recommend_relation
import com.bongtu.baekseo.R.string.recommendation_relation_selector_title
import com.bongtu.baekseo.core.common.type.RelationType
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun RecommendRelationTypeContent(
    name: String,
    onNameChange: (String) -> Unit,
    nameError: String,
    nickname: String,
    onNicknameChange: (String) -> Unit,
    nicknameError: String,
    onFocusChange: (Boolean) -> Unit,
    selectedRelation: RelationType?,
    onRelationSelect: (RelationType) -> Unit,
    isChecked: Boolean,
    onCheckBoxClick: (Boolean) -> Unit,
    contactFrequency: Float,
    onContactFrequencyChange: (Float) -> Unit,
    meetFrequency: Float,
    onMeetFrequencyChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        RecommendTextFieldCard(
            name = name,
            onNameChange = onNameChange,
            nameError = nameError,
            nickname = nickname,
            onNicknameChange = onNicknameChange,
            nicknameError = nicknameError,
            onFocusChange = onFocusChange,
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_recommend_relation),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified,
            )

            Text(
                text = stringResource(recommendation_relation_selector_title),
                style = BongBaekTheme.typography.titleSemiBold18,
                color = BongBaekTheme.colors.txtDisplaySecondary,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        RecommendRelationSelector(
            selectedRelation = selectedRelation,
            onRelationSelect = onRelationSelect,
        )

        Spacer(modifier = Modifier.height(32.dp))

        RecommendOptionCard(
            isChecked = isChecked,
            onCheckBoxClick = onCheckBoxClick,
            modifier = Modifier
                .fillMaxWidth(),
        )

        AnimatedVisibility(
            visible = isChecked,
        ) {
            RecommendSliderCard(
                contactFrequency = contactFrequency,
                onContactFrequencyChange = onContactFrequencyChange,
                meetFrequency = meetFrequency,
                onMeetFrequencyChange = onMeetFrequencyChange,
                modifier = Modifier
                    .padding(top = 8.dp),
            )
        }

        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Preview
@Composable
private fun RecommendRelationTypeContentPreview() {
    BongBaekTheme {
        var selectedRelation by remember { mutableStateOf<RelationType?>(null) }
        var name by remember { mutableStateOf("") }
        var nickname by remember { mutableStateOf("") }
        var isChecked by remember { mutableStateOf(false) }
        var contactFrequency by remember { mutableStateOf(0f) }
        var meetFrequency by remember { mutableStateOf(0f) }

        RecommendRelationTypeContent(
            selectedRelation = selectedRelation,
            onRelationSelect = { selectedRelation = it },
            name = name,
            onNameChange = { name = it },
            nameError = "",
            nickname = nickname,
            onNicknameChange = { nickname = it },
            nicknameError = "",
            onFocusChange = {},
            isChecked = isChecked,
            onCheckBoxClick = { isChecked = it },
            contactFrequency = contactFrequency,
            onContactFrequencyChange = { contactFrequency = it },
            meetFrequency = meetFrequency,
            onMeetFrequencyChange = { meetFrequency = it },
        )
    }
}
