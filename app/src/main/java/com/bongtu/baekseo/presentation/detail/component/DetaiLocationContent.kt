package com.bongtu.baekseo.presentation.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.core.designsystem.component.KakaoMapView
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.kakao.vectormap.LatLng

private const val MAP_RATIO = 280f / 180f

@Composable
fun DetailLocationContent(
    location: String,
    address: String,
    latitude: Double,
    longitude: Double,
    modifier: Modifier = Modifier,
) {
    val defaultPosition = LatLng.from(latitude, longitude)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.btnInteractiveSecondary,
                shape = RoundedCornerShape(10.dp),
            ),
    ) {
        KakaoMapView(
            position = defaultPosition,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .aspectRatio(MAP_RATIO),
            isGesturesDisabled = true,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 14.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = location,
                style = BongBaekTheme.typography.body1Medium16,
                color = BongBaekTheme.colors.txtDisplayPrimary,
            )

            Text(
                text = address,
                style = BongBaekTheme.typography.body2Regular14,
                color = BongBaekTheme.colors.txtDisplayTertiary,
            )
        }
    }
}

@Preview
@Composable
private fun EditLocationContentPreview() {
    BongBaekTheme {
        DetailLocationContent(
            location = "장소 이름",
            address = "장소 주소",
            latitude = 0.0,
            longitude = 0.0,
        )
    }
}
