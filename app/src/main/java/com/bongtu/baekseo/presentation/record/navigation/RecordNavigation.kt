package com.bongtu.baekseo.presentation.record.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.bongtu.baekseo.core.common.navigation.MainTabRoute
import com.bongtu.baekseo.presentation.record.RecordRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToRecord(navOptions: NavOptions? = null) = navigate(Record, navOptions)

fun NavGraphBuilder.recordGraph(
    modifier: Modifier = Modifier,
) {
    composable<Record> {
        RecordRoute(
            modifier = modifier,
        )
    }
}

@Serializable
data object Record : MainTabRoute