package com.bongtu.baekseo.presentation.record.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.MainTabRoute
import com.bongtu.baekseo.presentation.record.RecordDefaultRoute
import com.bongtu.baekseo.presentation.record.RecordDeleteRoute
import com.bongtu.baekseo.presentation.record.RecordViewModel
import com.bongtu.baekseo.presentation.record.navigation.RecordRoute.Default
import com.bongtu.baekseo.presentation.record.navigation.RecordRoute.Delete
import kotlinx.serialization.Serializable

fun NavController.navigateToRecord(navOptions: NavOptions? = null) = navigate(Record, navOptions)

fun NavGraphBuilder.recordGraph(
    modifier: Modifier = Modifier,
) {
    composable<Record> {
        RecordScreen(
            modifier = modifier,
        )
    }
}

fun NavGraphBuilder.recordNestedGraph(
    navController: NavHostController,
    viewModel: RecordViewModel,
) {
    composable<Default> {
        RecordDefaultRoute(
            onDeleteNavigate = { navController.navigate(Delete) },
            viewModel = viewModel,
        )
    }

    composable<Delete> {
        RecordDeleteRoute(
            onBackClick = {
                // TODO: 삭제 초기화
                navController.navigateUp()
            },
            onDeleteClick = {
                // TODO: 삭제 로직
                navController.navigateUp()
            },
            viewModel = viewModel,
        )
    }
}

@Serializable
data object Record : MainTabRoute