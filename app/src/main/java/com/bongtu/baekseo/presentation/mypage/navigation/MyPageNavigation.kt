package com.bongtu.baekseo.presentation.mypage.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.presentation.mypage.MyPageRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) =
    navigate(MyPage, navOptions)

fun NavGraphBuilder.myPageGraph(
    navController: NavHostController,
    navigateUp: () -> Unit,
    navigateToWithDraw: () -> Unit,
    modifier: Modifier = Modifier,
) {
    navigation<MyPage>(
        startDestination = MyPageMain,
    ) {
        composable<MyPageMain> {
            MyPageRoute(
                navigateUp = navigateUp,
                navigateToEditProfile = {},
                navigateToWithDraw = navigateToWithDraw,
                modifier = modifier,
            )
        }
    }
}

@Serializable
data object MyPage : Route

@Serializable
data object MyPageMain : Route
