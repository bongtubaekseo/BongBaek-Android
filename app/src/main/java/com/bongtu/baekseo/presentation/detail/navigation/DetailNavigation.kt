package com.bongtu.baekseo.presentation.detail.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.presentation.detail.DetailRoute
import com.bongtu.baekseo.presentation.edit.navigation.EditEvent
import kotlinx.serialization.Serializable

fun NavController.navigateToDetail(eventId: String, navOptions: NavOptions? = null) =
    navigate(Detail(eventId), navOptions)

fun NavGraphBuilder.detailGraph(
    navigateUp: () -> Unit,
    navigateToEdit: (EditEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Detail> { backStackEntry ->
        DetailRoute(
            navigateUp = navigateUp,
            navigateToEdit = navigateToEdit,
            modifier = modifier,
        )
    }
}

@Serializable
data class Detail(
    val eventId: String,
) : Route