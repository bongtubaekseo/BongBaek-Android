package com.bongtu.baekseo.presentation.recommend.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bongtu.baekseo.R
import com.bongtu.baekseo.core.designsystem.component.textfield.SearchTextField
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
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

private const val MAP_RATIO = 320 / 312f
private const val DEFAULT_LATITUDE = 37.5665
private const val DEFAULT_LONGITUDE = 126.9780

@Composable
fun RecommendEventLocationContent(
    latitude: Double,
    longitude: Double,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val defaultPosition = LatLng.from(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)
    val kakaoMapState = remember { mutableStateOf<KakaoMap?>(null) }

    LaunchedEffect(latitude, longitude, kakaoMapState.value) {
        kakaoMapState.value?.let { map ->
            val position = LatLng.from(latitude, longitude)
            map.moveCamera(CameraUpdateFactory.newCenterPosition(position))

            val layer = map.labelManager?.layer
            layer?.removeAll()

            val style = map.labelManager?.addLabelStyles(
                LabelStyles.from(
                    "myStyleId",
                    LabelStyle.from(R.drawable.img_map_marker)
                )
            )
            layer?.addLabel(LabelOptions.from(position).setStyles(style))
            Timber.tag("KakaoMap").d("Map updated with new location: ($latitude, $longitude)")
        }
    }

    Column(
        modifier = modifier
            .clickable {
                onSearchClick()
            },
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        SearchTextField(
            text = searchValue,
            onTextChange = onSearchValueChange,
        )

        AndroidView(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
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
                                Timber.tag("KakaoMap").d("Map ready")
                                val cameraUpdate =
                                    CameraUpdateFactory.newCenterPosition(defaultPosition)
                                kakaoMap.moveCamera(cameraUpdate)

                                val labelStyle = LabelStyle.from(R.drawable.img_map_marker)
                                val labelStyles = LabelStyles.from("myStyleId", labelStyle)
                                val appliedStyles =
                                    kakaoMap.labelManager?.addLabelStyles(labelStyles)
                                val labelLayer = kakaoMap.labelManager?.layer

                                labelLayer?.addLabel(
                                    LabelOptions.from(defaultPosition).setStyles(appliedStyles)
                                )
                            }
                        },
                    )
                }
            },
        )
    }
}

@Preview
@Composable
private fun RecommendEventLocationContentPreview() {
    var searchValue by remember { mutableStateOf("") }

    BongBaekTheme {
        RecommendEventLocationContent(
            latitude = 0.0,
            longitude = 0.0,
            searchValue = searchValue,
            onSearchValueChange = { searchValue = it },
            onSearchClick = {},
        )
    }
}
