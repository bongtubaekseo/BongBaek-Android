package com.bongtu.baekseo.presentation.record.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.MainTabRoute
import com.bongtu.baekseo.presentation.record.RecordRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToRecord(navOptions: NavOptions? = null) = navigate(Record, navOptions)

fun NavGraphBuilder.recordGraph(
    setBottomBarVisible: (Boolean) -> Unit,
    navigateToAdd: () -> Unit,
    navigateToDetail: (String) -> Unit,
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    composable<Record> {
        RecordRoute(
            setBottomBarVisible = setBottomBarVisible,
            navigateToDetail = navigateToDetail,
            navigateToAdd = navigateToAdd,
            innerPadding = innerPadding,
            modifier = modifier,
        )
    }
}

@Serializable
data object Record : MainTabRoute