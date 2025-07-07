package com.bongtu.baekseo.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.bongtu.baekseo.R.drawable.ic_dollar
import com.bongtu.baekseo.R.drawable.ic_home
import com.bongtu.baekseo.R.drawable.ic_pen
import com.bongtu.baekseo.R.string.home
import com.bongtu.baekseo.R.string.recommendation
import com.bongtu.baekseo.R.string.record
import com.bongtu.baekseo.core.common.navigation.MainTabRoute
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.presentation.dummy.navigation.Dummy
import com.bongtu.baekseo.presentation.record.navigation.Record

enum class MainTab(
    @DrawableRes val iconRes: Int,
    @StringRes val title: Int,
    val route: MainTabRoute,
) {
    HOME(
        iconRes = ic_home,
        title = home,
        route = Dummy,
    ),
    RECOMMEND(
        iconRes = ic_dollar,
        title = recommendation,
        route = Dummy,
    ),
    RECORD(
        iconRes = ic_pen,
        title = record,
        route = Record,
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}
