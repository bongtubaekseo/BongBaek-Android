package com.bongtu.baekseo.presentation.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import com.jakewharton.processphoenix.ProcessPhoenix
import dagger.hilt.android.AndroidEntryPoint

private const val IS_START_LOGIN = "is_start_login"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isStartLogin = intent.getBooleanExtra(IS_START_LOGIN, false)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
        )
        window.isNavigationBarContrastEnforced = false
        setContent {
            BongBaekTheme {
                MainScreen(
                    isStartLogin = isStartLogin,
                    onRestartApp = {
                        restartApp(this, it)
                    },
                )
            }
        }
    }

    private fun restartApp(context: Context, isStartLogin: Boolean) {
        val intent = Intent(context, this::class.java).apply {
            putExtra(IS_START_LOGIN, isStartLogin)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        ProcessPhoenix.triggerRebirth(context, intent)
    }
}
