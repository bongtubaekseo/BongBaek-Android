package com.bongtu.baekseo.presentation.record.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.MainTabRoute
import com.bongtu.baekseo.presentation.record.RecordDefaultRoute
import com.bongtu.baekseo.presentation.record.RecordScreen
import com.bongtu.baekseo.presentation.record.RecordViewModel
import com.bongtu.baekseo.presentation.record.navigation.RecordRoute.Default
import kotlinx.serialization.Serializable

fun NavController.navigateToRecord(navOptions: NavOptions? = null) = navigate(Record, navOptions)

fun NavGraphBuilder.recordGraph(
    setBottomBarVisible: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Record> {
        RecordScreen(
            setBottomBarVisible = setBottomBarVisible,
            modifier = modifier,
        )
    }
}

fun NavGraphBuilder.recordNestedGraph(
    setBottomBarVisible: (Boolean) -> Unit,
    navController: NavHostController,
    viewModel: RecordViewModel,
) {
    composable<Default> {
        RecordDefaultRoute(
            setBottomBarVisible = setBottomBarVisible,
            viewModel = viewModel,
        )
    }

    // TODO: 수정 페이지
}

@Serializable
data object Record : MainTabRoute