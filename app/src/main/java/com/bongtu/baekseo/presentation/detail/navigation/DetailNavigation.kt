package com.bongtu.baekseo.presentation.detail.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.presentation.detail.DetailRoute
import com.bongtu.baekseo.presentation.edit.type.EditType
import kotlinx.serialization.Serializable

fun NavController.navigateToDetail(navOptions: NavOptions? = null) =
    navigate(Detail, navOptions)

fun NavController.navigateToDetail(eventId: String, navOptions: NavOptions? = null) =
    navigate(Detail, navOptions)

fun NavGraphBuilder.detailGraph(
    navigateUp: () -> Unit,
    navigateToEdit: (EditType) -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Detail> {
        DetailRoute(
            navigateUp = navigateUp,
            navigateToEdit = { navigateToEdit(EditType.EDIT) },
            modifier = modifier,
        )
    }
}

@Serializable
data object Detail : Route