package com.bongtu.baekseo.presentation.contents.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.MainTabRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToContents(navOptions: NavOptions? = null) =
    navigate(Contents, navOptions)

fun NavGraphBuilder.contentsGraph(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<Contents> {
    }
}

@Serializable
data object Contents : MainTabRoute
