package com.bongtu.baekseo.presentation.recommend.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.core.designsystem.component.KakaoMapView
import com.bongtu.baekseo.core.designsystem.component.dropdownmenu.BongBaekDropdownMenu
import com.bongtu.baekseo.core.designsystem.component.textfield.SearchTextField
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.data.model.map.Place
import com.kakao.vectormap.LatLng
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private const val MAP_RATIO = 320 / 312f
private const val DEFAULT_LATITUDE = 37.5665
private const val DEFAULT_LONGITUDE = 126.9780

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendEventLocationContent(
    selectedPlace: Place?,
    onPlaceSelect: (Place) -> Unit,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    searchResult: ImmutableList<Place>,
    onFocusChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val density = LocalDensity.current
    val isImeVisible = WindowInsets.ime.getBottom(density) > 0
    val defaultPosition = selectedPlace?.let {
        LatLng.from(it.latitude, it.longitude)
    } ?: LatLng.from(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)

    LaunchedEffect(isImeVisible) {
        if (!isImeVisible) {
            focusManager.clearFocus()
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        BongBaekDropdownMenu<Place>(
            expanded = isFocused && searchValue.isNotEmpty(),
            items = searchResult,
            selectedItem = selectedPlace,
            onItemSelect = { place ->
                onPlaceSelect(place)
                onFocusChange(false)
                focusManager.clearFocus()
            },
            label = { it.name },
        ) {
            SearchTextField(
                text = searchValue,
                onTextChange = onSearchValueChange,
                modifier = Modifier
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                onFocusChange = onFocusChange,
                focusManager = focusManager,
            )
        }

        Box {
            KakaoMapView(
                position = defaultPosition,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .aspectRatio(MAP_RATIO),
            )

            selectedPlace?.let { place ->
                LocationCard(
                    name = place.name,
                    address = place.address,
                    modifier = Modifier
                        .align(Alignment.BottomCenter),
                )
            }
        }
    }
}

@Composable
private fun LocationCard(
    name: String,
    address: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.bgDisplayCard,
                shape = RoundedCornerShape(10.dp),
            )
            .padding(
                horizontal = 20.dp,
                vertical = 14.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = name,
            style = BongBaekTheme.typography.titleSemiBold18,
            color = BongBaekTheme.colors.txtDisplayPrimary,
        )

        Text(
            text = address,
            style = BongBaekTheme.typography.body2Regular14,
            color = BongBaekTheme.colors.txtDisplayTertiary,
        )
    }
}

@Preview
@Composable
private fun RecommendEventLocationContentPreview() {
    var searchValue by remember { mutableStateOf("") }

    BongBaekTheme {
        RecommendEventLocationContent(
            selectedPlace = null,
            onPlaceSelect = {},
            searchValue = searchValue,
            onSearchValueChange = { searchValue = it },
            searchResult = persistentListOf(),
            onFocusChange = {},
        )
    }
}
