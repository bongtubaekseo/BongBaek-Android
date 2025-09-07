package com.bongtu.baekseo.presentation.mypage.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.core.util.sharedViewModel
import com.bongtu.baekseo.presentation.mypage.MyPageRoute
import com.bongtu.baekseo.presentation.mypage.MyPageViewModel
import com.bongtu.baekseo.presentation.mypage.ProfileEditRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) =
    navigate(MyPage, navOptions)

fun NavController.navigateToProfileEdit(navOptions: NavOptions? = null) =
    navigate(ProfileEdit, navOptions)

fun NavGraphBuilder.myPageGraph(
    navController: NavHostController,
    navigateUp: () -> Unit,
    navigateToWithdraw: () -> Unit,
    onRestartApp: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    navigation<MyPage>(
        startDestination = MyPageMain,
    ) {
        composable<MyPageMain> {  backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<MyPageViewModel>(navController)

            MyPageRoute(
                navigateUp = navigateUp,
                navigateToEditProfile = navController::navigateToProfileEdit,
                navigateToWithdraw = navigateToWithdraw,
                onRestartApp = onRestartApp,
                viewModel = viewModel,
                modifier = modifier,
            )
        }

        composable<ProfileEdit> { backStackEntry ->
            val viewModel = backStackEntry.sharedViewModel<MyPageViewModel>(navController)

            ProfileEditRoute(
                navigateUp = navController::navigateUp,
                viewModel = viewModel,
                modifier = modifier,
            )
        }
    }
}

@Serializable
data object MyPage : Route

@Serializable
data object MyPageMain : Route

@Serializable
data object ProfileEdit : Route
