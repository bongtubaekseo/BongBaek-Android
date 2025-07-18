package com.bongtu.baekseo.core.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.bongtu.baekseo.R.drawable.img_map_marker
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

@Composable
fun KakaoMapView(
    position: LatLng,
    modifier: Modifier = Modifier,
    isGesturesDisabled: Boolean = false,
) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    var kakaoMap by remember { mutableStateOf<KakaoMap?>(null) }

    val lifecycle = LocalLifecycleOwner.current
    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> mapView.resume()
                Lifecycle.Event.ON_STOP -> mapView.pause()
                Lifecycle.Event.ON_DESTROY -> mapView.finish()
                else -> Unit
            }
        }
        lifecycle.lifecycle.addObserver(observer)
        onDispose {
            lifecycle.lifecycle.removeObserver(observer)
            mapView.finish()
        }
    }

    AndroidView(
        factory = { ctx ->
            mapView.apply {
                start(
                    object : MapLifeCycleCallback() {
                        override fun onMapDestroy() = Unit
                        override fun onMapError(exception: Exception?) = Unit
                    },
                    object : KakaoMapReadyCallback() {
                        override fun onMapReady(map: KakaoMap) {
                            kakaoMap = map
                            if (isGesturesDisabled) GestureType.entries.forEach { map.setGestureEnable(it, false) }

                            map.moveCamera(CameraUpdateFactory.newCenterPosition(position))
                            addMarker(map, position)
                        }
                    }
                )
            }
        },
        modifier = modifier,
        update = {
            kakaoMap?.let { map ->
                map.moveCamera(CameraUpdateFactory.newCenterPosition(position))
                map.labelManager?.layer?.removeAll()
                addMarker(map, position)
            }
        }
    )
}

private fun addMarker(map: KakaoMap, position: LatLng) {
    val labelStyle = LabelStyle.from(img_map_marker)
    val labelStyles = LabelStyles.from("marker", labelStyle)
    val appliedStyles = map.labelManager?.addLabelStyles(labelStyles)
    map.labelManager?.layer?.addLabel(
        LabelOptions.from(position).setStyles(appliedStyles)
    )
}
