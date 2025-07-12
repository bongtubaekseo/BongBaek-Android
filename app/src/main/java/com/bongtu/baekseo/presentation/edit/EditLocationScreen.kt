package com.bongtu.baekseo.presentation.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.bongtu.baekseo.R.drawable.ic_caution
import com.bongtu.baekseo.R.drawable.ic_close
import com.bongtu.baekseo.R.string.edit_location_sarch_item_empty
import com.bongtu.baekseo.R.string.edit_location_top_bar_title
import com.bongtu.baekseo.core.common.type.ButtonType
import com.bongtu.baekseo.core.common.type.TopBarType
import com.bongtu.baekseo.core.designsystem.component.button.BongBaekButton
import com.bongtu.baekseo.core.designsystem.component.dropdownmenu.BongBaekDropdownMenu
import com.bongtu.baekseo.core.designsystem.component.textfield.SearchTextField
import com.bongtu.baekseo.core.designsystem.component.topbar.BongBaekTopBar
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun EditLocationRoute(

    modifier: Modifier = Modifier,
) {
    var searchValue by remember { mutableStateOf("") }
    var selectedSearchItem by remember { mutableStateOf("") }
    val dummyItems = persistentListOf<String>()

    EditLocationScreen(
        searchValue = searchValue,
        onSearchValueChange = { searchValue = it },
        searchItems = dummyItems,                   // TODO: 검색 결과
        selectedSearchItem = selectedSearchItem,    // TODO: 검색 결과
        onSearchItemSelected = {},                  // TODO: 검색 결과
        isButtonEnabled = true,
        modifier = modifier,
    )
}

@Composable
fun EditLocationScreen(
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    searchItems: ImmutableList<String>,
    selectedSearchItem: String,
    onSearchItemSelected: (String) -> Unit,
    isButtonEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val isEmptySearchItems = searchItems.isEmpty()

    Column(
        modifier = modifier
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
                            // TODO: navigateUp 예정
                        },
                    tint = BongBaekTheme.colors.white,
                )
            },
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 20.dp
                )
                .verticalScroll(rememberScrollState()),
        ) {
            SearchTextField(
                text = searchValue,
                onTextChange = onSearchValueChange,
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        bottom = 14.dp
                    ),
            )

            // TODO: 지도로 대체
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        color = BongBaekTheme.colors.primaryLight,
                        shape = RoundedCornerShape(12.dp),
                    ),
                contentAlignment = Alignment.Center,
            ) {
                if (isEmptySearchItems) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .background(
                                color = BongBaekTheme.colors.gray750,
                                shape = RoundedCornerShape(10.dp),
                            )
                            .padding(
                                top = 55.dp,
                                bottom = 55.dp,
                            ),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(ic_caution),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(bottom = 6.dp),
                            tint = BongBaekTheme.colors.primaryNormal,
                        )
                        Text(
                            text = stringResource(edit_location_sarch_item_empty),
                            style = BongBaekTheme.typography.body1Medium16,
                            color = BongBaekTheme.colors.white,
                        )
                    }
                } else {
                    BongBaekDropdownMenu(
                        expanded = expanded,
                        items = searchItems,
                        maxItemSize = 3,
                        selectedItem = selectedSearchItem,
                        onDismissRequest = { expanded = false },
                        onItemSelected = onSearchItemSelected,
                        modifier = Modifier,
                    )
                }

                Text(
                    text = "지도",
                    style = BongBaekTheme.typography.headBold24,
                    color = BongBaekTheme.colors.white,
                )
            }

            BongBaekButton(
                title = stringResource(edit_location_top_bar_title),
                onClick = {
                    // TODO: 저장 로직
                },
                buttonType = ButtonType.PRIMARY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 23.dp,
                        bottom = 36.dp,
                    )
                    .systemBarsPadding(),
                enabled = isButtonEnabled,
            )
        }
    }
}

@Preview
@Composable
private fun EditLocationScreenPreview() {
    BongBaekTheme {
        EditLocationScreen(
            searchValue = "",
            onSearchValueChange = {},
            isButtonEnabled = true,
            searchItems = persistentListOf(),
            selectedSearchItem = "",
            onSearchItemSelected = {},
        )
    }
}