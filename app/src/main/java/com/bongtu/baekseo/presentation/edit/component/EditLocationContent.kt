package com.bongtu.baekseo.presentation.edit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.data.model.map.Place

private const val MAP_RATIO = 280f / 180f

@Composable
fun EditLocationContent(
    place: Place,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.gray700,
                shape = RoundedCornerShape(10.dp),
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = BongBaekTheme.colors.primaryLight,
                    shape = RoundedCornerShape(10.dp),
                )
                .aspectRatio(MAP_RATIO),
        ) {
            // TODO: 지도로 대체
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp,
                ),
        ) {
            Text(
                text = place.name,
                style = BongBaekTheme.typography.body1Medium16,
                color = BongBaekTheme.colors.white,
                modifier = Modifier
                    .padding(bottom = 2.dp),
            )
            Text(
                text = place.address,
                style = BongBaekTheme.typography.body2Regular14,
                color = BongBaekTheme.colors.gray400,
            )
        }
    }
}
