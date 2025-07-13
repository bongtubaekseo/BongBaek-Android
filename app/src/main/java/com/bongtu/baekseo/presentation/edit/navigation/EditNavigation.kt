package com.bongtu.baekseo.presentation.edit.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.presentation.edit.EditLocationRoute
import com.bongtu.baekseo.presentation.edit.EditMainRoute
import com.bongtu.baekseo.presentation.edit.EditRoute
import com.bongtu.baekseo.presentation.edit.EditViewModel
import com.bongtu.baekseo.presentation.edit.navigation.EditRoute.Location
import com.bongtu.baekseo.presentation.edit.navigation.EditRoute.Main
import com.bongtu.baekseo.presentation.edit.type.EditType
import kotlinx.serialization.Serializable

fun NavController.navigateToEdit(editType: EditType, navOptions: NavOptions? = null) =
    navigate(Edit(editType), navOptions)

fun NavGraphBuilder.editGraph(
    navigateToUp: () -> Unit,
    setBottomBarVisible: (Boolean) -> Unit,
    navigateToFinal: () -> Unit,
    navigateToRecord: () -> Unit,
    modifier: Modifier = Modifier,
) {

    composable<Edit> { backStackEntry ->
        val navArgs = backStackEntry.toRoute<Edit>()

        EditRoute(
            navigateUp = navigateToUp,
            editType = navArgs.editType,
            setBottomBarVisible = setBottomBarVisible,
            navigateToFinal = navigateToFinal,
            navigateToRecord = navigateToRecord,
            modifier = modifier,
        )
    }
}

fun NavGraphBuilder.nestedEditGraph(
    editType: EditType,
    navigateUp: () -> Unit,
    navigateToFinal: () -> Unit,
    navigateToRecord: () -> Unit,
    viewModel: EditViewModel,
    modifier: Modifier = Modifier,
) {
    composable<Main> {
        EditMainRoute(
            editType = editType,
            navigateUp = navigateUp,
            navigateToFinal = navigateToFinal,
            navigateToRecord = navigateToRecord,
            viewModel = viewModel,
            modifier = modifier,
        )
    }

    composable<Location> {
        EditLocationRoute(
            navigateUp = navigateUp,
            viewModel = viewModel,
            modifier = modifier,
        )
    }
}

@Serializable
data class Edit(
    val editType: EditType,
) : Route