package com.bongtu.baekseo.presentation.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.bongtu.baekseo.core.util.noRippleClickable
import com.bongtu.baekseo.presentation.main.MainTab
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun MainBottomBar(
    tabs: ImmutableList<MainTab>,
    currentTab: MainTab?,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = BongBaekTheme.colors.gnbDisplayBase,
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                ),
            )
            .padding(
                top = 12.dp,
                bottom = 5.dp,
            )
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        tabs.forEach { tab ->
            MainNavigationBarItem(
                tab = tab,
                onClick = { onTabSelected(tab) },
                isSelected = tab == currentTab,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun MainNavigationBarItem(
    tab: MainTab,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    val (contentColor, iconRes) =
        if (isSelected) BongBaekTheme.colors.txtDisplayPrimary to tab.selectedIconRes
        else BongBaekTheme.colors.txtStatusDisabled to tab.unselectedIconRes

    Column(
        modifier = modifier
            .semantics(mergeDescendants = true) { role = Role.Tab }
            .noRippleClickable(onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = stringResource(tab.title),
            tint = contentColor,
        )

        Text(
            text = stringResource(tab.title),
            style = BongBaekTheme.typography.body2Regular14,
            color = contentColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainBottomBarPreview() {
    BongBaekTheme {
        MainBottomBar(
            tabs = MainTab.entries.toImmutableList(),
            currentTab = MainTab.HOME,
            onTabSelected = {},
        )
    }
}
