package com.bongtu.baekseo.presentation.home.schedule.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.bongtu.baekseo.presentation.home.navigation.HomeRoute.Schedule

fun NavController.navigateToSchedule(navOptions: NavOptions? = null) =
    navigate(Schedule, navOptions)
