package com.bongtu.baekseo.presentation.splash

class SplashContract {
    sealed class SplashSideEffect {
        data object NavigateToHome : SplashSideEffect()
        data object NavigateToLogin : SplashSideEffect()
        data object RestartApp: SplashSideEffect()
    }
}