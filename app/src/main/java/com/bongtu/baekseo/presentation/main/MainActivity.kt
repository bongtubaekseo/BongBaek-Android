package com.bongtu.baekseo.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bongtu.baekseo.core.common.app.AppRestarter
import com.bongtu.baekseo.core.designsystem.theme.BongBaekTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val IS_START_LOGIN = "is_start_login"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appRestarter: AppRestarter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isStartLogin = intent.getBooleanExtra(IS_START_LOGIN, false)

        enableEdgeToEdge()
        window.isNavigationBarContrastEnforced = false

        setContent {
            BongBaekTheme {
                MainScreen(
                    isStartLogin = isStartLogin,
                    onRestartApp = {
                        appRestarter.restartApp(isStartLogin = it)
                    },
                )
            }
        }
    }
}
