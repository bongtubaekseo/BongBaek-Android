package com.bongtu.baekseo.presentation.profileedit.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.core.common.type.IncomeType
import com.bongtu.baekseo.presentation.profileedit.ProfileEditRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToProfileEdit(
    userName: String,
    userBirth: String,
    userIncome: IncomeType,
    navOptions: NavOptions? = null,
) = navigate(
    route = ProfileEdit(
        userName = userName,
        userBirth = userBirth,
        userIncome = userIncome,
    ),
    navOptions = navOptions,
)

fun NavGraphBuilder.profileEditGraph(
    navigateToUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable<ProfileEdit> {
        ProfileEditRoute(
            navigateToUp = navigateToUp,
            modifier = modifier,
        )
    }
}

@Serializable
data class ProfileEdit(
    val userName: String,
    val userBirth: String,
    val userIncome: IncomeType,
) : Route
