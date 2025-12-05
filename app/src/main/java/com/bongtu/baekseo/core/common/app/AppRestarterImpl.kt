package com.bongtu.baekseo.core.common.app

import android.content.Context
import android.content.Intent
import com.bongtu.baekseo.presentation.main.MainActivity
import com.jakewharton.processphoenix.ProcessPhoenix
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val IS_START_LOGIN = "is_start_login"

class AppRestarterImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : AppRestarter {

    override fun restartApp(isStartLogin: Boolean) {
        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra(IS_START_LOGIN, isStartLogin)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        ProcessPhoenix.triggerRebirth(context, intent)
    }
}
