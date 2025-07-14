package com.bongtu.baekseo.presentation.detail.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.presentation.detail.DetailRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToDetail(eventId: String, navOptions: NavOptions? = null) =
    navigate(Detail(eventId), navOptions)

fun NavGraphBuilder.detailGraph(
    navigateUp: () -> Unit,
    navigateToEdit: () -> Unit,
    navigateToRecord: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Detail> { backStackEntry ->
        val args = backStackEntry.toRoute<Detail>()
        DetailRoute(
            eventId = args.eventId,
            navigateUp = navigateUp,
            navigateToEdit = navigateToEdit,    // TODO: Caching
            navigateToRecord = navigateToRecord,
            modifier = modifier,
        )
    }
}

@Serializable
data class Detail(
    val eventId: String,
) : Route