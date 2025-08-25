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

const val IS_START_ONBOARDING = "is_start_onBoarding"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isStartOnBoarding = intent.getBooleanExtra(IS_START_ONBOARDING, false)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
        )
        window.isNavigationBarContrastEnforced = false
        setContent {
            BongBaekTheme {
                MainScreen(
                    isStartOnBoarding = isStartOnBoarding,
                    onRestartApp = {
                        restartApp(this, it)
                    },
                )
            }
        }
    }

    private fun restartApp(context: Context, isStartOnBoarding: Boolean) {
        val intent = Intent(context, this::class.java).apply {
            putExtra(IS_START_ONBOARDING, isStartOnBoarding)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        ProcessPhoenix.triggerRebirth(context, intent)
    }
}
