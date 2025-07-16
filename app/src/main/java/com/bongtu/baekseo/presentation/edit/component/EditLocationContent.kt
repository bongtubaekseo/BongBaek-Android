package com.bongtu.baekseo.presentation.edit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bongtu.baekseo.R.drawable.img_map_marker
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.data.model.map.Place
import com.kakao.vectormap.GestureType
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import timber.log.Timber

private const val MAP_RATIO = 280 / 180f

@Composable
fun EditLocationContent(
    place: Place,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val defaultPosition = LatLng.from(place.latitude, place.longitude)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.gray700,
                shape = RoundedCornerShape(10.dp),
            ),
    ) {
        AndroidView(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .aspectRatio(MAP_RATIO),
            factory = { context ->
                mapView.apply {
                    mapView.start(
                        object : MapLifeCycleCallback() {
                            override fun onMapDestroy() = Unit

                            override fun onMapError(exception: Exception?) = Unit
                        },
                        object : KakaoMapReadyCallback() {
                            override fun onMapReady(kakaoMap: KakaoMap) {
                                GestureType.entries.forEach {
                                    kakaoMap.setGestureEnable(it, false)
                                }

                                Timber.tag("KakaoMap").d("Map ready")
                                val cameraUpdate =
                                    CameraUpdateFactory.newCenterPosition(defaultPosition)
                                kakaoMap.moveCamera(cameraUpdate)

                                val labelStyle = LabelStyle.from(img_map_marker)
                                val labelStyles = LabelStyles.from("myStyleId", labelStyle)
                                val appliedStyles =
                                    kakaoMap.labelManager?.addLabelStyles(labelStyles)
                                val labelLayer = kakaoMap.labelManager?.layer

                                labelLayer?.addLabel(
                                    LabelOptions.from(defaultPosition)
                                        .setStyles(appliedStyles)
                                )
                            }
                        },
                    )
                }
            },
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
                text = place.name,
                style = BongBaekTheme.typography.body1Medium16,
                color = BongBaekTheme.colors.white,
            )

            Text(
                text = place.address,
                style = BongBaekTheme.typography.body2Regular14,
                color = BongBaekTheme.colors.gray400,
            )
        }
    }
}

@Preview
@Composable
private fun EditLocationContentPreview() {
    BongBaekTheme {
        EditLocationContent(
            place = Place(
                id = "",
                name = "장소 이름",
                address = "장소 주소",
                roadAddress = "",
                latitude = 0.0,
                longitude = 0.0,
            ),
        )
    }
}
