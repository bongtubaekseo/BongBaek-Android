package com.bongtu.baekseo.presentation.recommend.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.core.designsystem.component.textfield.SearchTextField
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme

private const val MAP_RATIO = 320 / 312

@Composable
fun RecommendEventLocationContent(
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        SearchTextField(
            text = searchValue,
            onTextChange = onSearchValueChange,
        )

        // TODO: 지도로 대체
        Box(
            modifier = Modifier
                .background(
                    color = BongBaekTheme.colors.primaryLight,
                    shape = RoundedCornerShape(12.dp),
                )
                .aspectRatio(MAP_RATIO.toFloat()),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "지도",
                style = BongBaekTheme.typography.headBold24,
                color = BongBaekTheme.colors.white,
            )
        }
    }
}

@Preview
@Composable
private fun RecommendEventLocationContentPreview() {
    var searchValue by remember { mutableStateOf("") }

    BongBaekTheme {
        RecommendEventLocationContent(
            searchValue = searchValue,
            onSearchValueChange = { searchValue = it },
        )
    }
}
