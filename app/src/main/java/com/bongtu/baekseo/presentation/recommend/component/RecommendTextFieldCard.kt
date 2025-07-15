package com.bongtu.baekseo.presentation.recommend.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.R.drawable.ic_person
import com.bongtu.baekseo.R.string.recommendation_name_placeholder
import com.bongtu.baekseo.R.string.recommendation_name_title
import com.bongtu.baekseo.R.string.recommendation_nickname_placeholder
import com.bongtu.baekseo.core.designsystem.component.textfield.RoundedBoxTextField
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

@Composable
fun RecommendTextFieldCard(
    name: String,
    onNameChange: (String) -> Unit,
    nameError: String?,
    nickname: String,
    onNicknameChange: (String) -> Unit,
    nicknameError: String?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(
                color = BongBaekTheme.colors.gray750,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(20.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(ic_person),
                contentDescription = null,
                modifier = Modifier.size(22.dp),
                tint = BongBaekTheme.colors.primaryNormal,
            )

            Text(
                text = stringResource(recommendation_name_title),
                style = BongBaekTheme.typography.titleSemiBold18,
                color = BongBaekTheme.colors.gray100,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        RoundedBoxTextField(
            text = name,
            placeholder = stringResource(recommendation_name_placeholder),
            errorText = nameError,
            onTextChange = onNameChange,
        )

        Spacer(modifier = Modifier.height(8.dp))

        RoundedBoxTextField(
            text = nickname,
            placeholder = stringResource(recommendation_nickname_placeholder),
            errorText = nicknameError,
            onTextChange = onNicknameChange,
        )
    }
}


@Preview
@Composable
private fun RecommendTextFieldCardPreview() {
    BongBaekTheme {
        var name by remember { mutableStateOf("") }
        var nickname by remember { mutableStateOf("") }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            RecommendTextFieldCard(
                name = name,
                onNameChange = { name = it },
                nameError = null,
                nickname = nickname,
                onNicknameChange = { nickname = it },
                nicknameError = null,
            )
        }
    }
}
