package com.bongtu.baekseo.presentation.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bongtu.baekseo.R.drawable.ic_close
import com.bongtu.baekseo.R.string.edit_location_button
import com.bongtu.baekseo.R.string.edit_location_top_bar_title
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.KakaoMapView
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.component.dropdownmenu.BongBaekDropdownMenu
import com.bongtu.baekseo.core.designsystem.component.textfield.SearchTextField
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.data.model.map.Place
import com.bongtu.baekseo.presentation.edit.EditContract.EditSideEffect
import com.kakao.vectormap.LatLng
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.filterIsInstance

private const val MAP_RATIO = 320 / 468f
private const val DEFAULT_LATITUDE = 37.5665
private const val DEFAULT_LONGITUDE = 126.9780

@Composable
fun EditLocationRoute(
    navigateUp: () -> Unit,
    viewModel: EditViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchTerm by viewModel.searchTerm.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycle = lifecycleOwner.lifecycle)
            .filterIsInstance<EditSideEffect.EditLocationSideEffect>()
            .collect { navigateUp() }
    }

    EditLocationScreen(
        searchValue = searchTerm,
        navigateToUp = {
            navigateUp()
            viewModel.clearSearchResult()
        },
        onSearchValueChange = viewModel::updateSearchTerm,
        searchResult = uiState.searchResult,
        selectedPlace = uiState.selectedPlace,
        onPlaceSelect = viewModel::updatePlace,
        modifier = modifier,
    )
}

@Composable
fun EditLocationScreen(
    searchValue: String,
    navigateToUp: () -> Unit,
    onSearchValueChange: (String) -> Unit,
    searchResult: ImmutableList<Place>,
    selectedPlace: Place?,
    onPlaceSelect: (Place?) -> Unit,
    modifier: Modifier = Modifier,
) {
    val defaultPosition = selectedPlace?.let {
        LatLng.from(it.latitude, it.longitude)
    } ?: LatLng.from(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)

    var isExpanded by remember { mutableStateOf(false) }
    var rowWidthPx by remember { mutableIntStateOf(0) }
    var tempSelectedPlace by remember { mutableStateOf<Place?>(selectedPlace) }

    LaunchedEffect(searchResult) {
        if (searchResult.isNotEmpty()) isExpanded = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = BongBaekTheme.colors.gray900,
            )
            .systemBarsPadding(),
    ) {
        BongBaekTopBar(
            title = stringResource(edit_location_top_bar_title),
            topBarType = TopBarType.LEADING_ICON,
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(ic_close),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(12.dp)
                        .noRippleClickable {
                            navigateToUp()
                            onSearchValueChange("")
                        },
                    tint = BongBaekTheme.colors.white,
                )
            },
        )

        Column(
            modifier = Modifier
                .padding(
                    horizontal = 20.dp,
                )
                .onGloballyPositioned { coordinates ->
                    rowWidthPx = coordinates.size.width
                },
        ) {
            Column {
                SearchTextField(
                    text = searchValue,
                    onTextChange = onSearchValueChange,
                    modifier = Modifier
                        .padding(
                            top = 16.dp,
                        ),
                )

                BongBaekDropdownMenu<Place>(
                    expanded = isExpanded,
                    items = searchResult,
                    selectedItem = tempSelectedPlace,
                    onDismissRequest = { isExpanded = false },
                    onItemSelect = { place ->
                        tempSelectedPlace = place
                    },
                    label = { it.name },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { rowWidthPx.toDp() }),
                    offset = DpOffset(0.dp, 12.dp),
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Box {
                KakaoMapView(
                    position = defaultPosition,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .aspectRatio(MAP_RATIO),
                )

                tempSelectedPlace?.let { place ->
                    LocationCard(
                        name = place.name,
                        address = place.address,
                        modifier = Modifier.align(Alignment.BottomCenter),
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            BongBaekButton(
                title = stringResource(edit_location_button),
                onClick = {
                    onPlaceSelect(tempSelectedPlace)
                    navigateToUp()
                    onSearchValueChange("")
                },
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 36.dp,
                    ),
                enabled = tempSelectedPlace != null,
            )
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
            .padding(12.dp)
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.gray750,
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
            color = BongBaekTheme.colors.white,
        )

        Text(
            text = address,
            style = BongBaekTheme.typography.body2Regular14,
            color = BongBaekTheme.colors.gray400,
        )
    }
}

@Preview
@Composable
private fun EditLocationScreenPreview() {
    BongBaekTheme {
        EditLocationScreen(
            searchValue = "",
            onSearchValueChange = {},
            navigateToUp = {},
            searchResult = persistentListOf(),
            selectedPlace = null,
            onPlaceSelect = {},
        )
    }
}
