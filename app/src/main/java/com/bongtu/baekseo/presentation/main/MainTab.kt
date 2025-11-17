package com.bongtu.baekseo.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.bongtu.baekseo.R.drawable.ic_contents_selected
import com.bongtu.baekseo.R.drawable.ic_contents_unselected
import com.bongtu.baekseo.R.drawable.ic_home_selected
import com.bongtu.baekseo.R.drawable.ic_home_unselected
import com.bongtu.baekseo.R.drawable.ic_recommend_selected
import com.bongtu.baekseo.R.drawable.ic_recommend_unselected
import com.bongtu.baekseo.R.drawable.ic_record_selected
import com.bongtu.baekseo.R.drawable.ic_record_unselected
import com.bongtu.baekseo.R.drawable.ic_setting_selected
import com.bongtu.baekseo.R.drawable.ic_setting_unselected
import com.bongtu.baekseo.R.string.contents
import com.bongtu.baekseo.R.string.home
import com.bongtu.baekseo.R.string.recommendation
import com.bongtu.baekseo.R.string.record
import com.bongtu.baekseo.R.string.setting
import com.bongtu.baekseo.core.common.navigation.MainTabRoute
import com.bongtu.baekseo.core.common.navigation.Route
import com.bongtu.baekseo.presentation.contents.navigation.Contents
import com.bongtu.baekseo.presentation.home.navigation.Home
import com.bongtu.baekseo.presentation.mypage.navigation.Setting
import com.bongtu.baekseo.presentation.recommend.navigation.Recommend
import com.bongtu.baekseo.presentation.record.navigation.Record

enum class MainTab(
    @DrawableRes val selectedIconRes: Int,
    @DrawableRes val unselectedIconRes: Int,
    @StringRes val title: Int,
    val route: MainTabRoute,
) {
    HOME(
        selectedIconRes = ic_home_selected,
        unselectedIconRes = ic_home_unselected,
        title = home,
        route = Home,
    ),
    RECOMMEND(
        selectedIconRes = ic_recommend_selected,
        unselectedIconRes = ic_recommend_unselected,
        title = recommendation,
        route = Recommend,
    ),
    RECORD(
        selectedIconRes = ic_record_selected,
        unselectedIconRes = ic_record_unselected,
        title = record,
        route = Record,
    ),
    CONTENTS(
        selectedIconRes = ic_contents_selected,
        unselectedIconRes = ic_contents_unselected,
        title = contents,
        route = Contents
    ),
    SETTING(
        selectedIconRes = ic_setting_selected,
        unselectedIconRes = ic_setting_unselected,
        title = setting,
        route = Setting,
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
