package com.bongtu.baekseo.presentation.splash

class SplashContract {
    sealed class SplashSideEffect {
        data object NavigateToHome : SplashSideEffect()
        data object NavigateToOnBoarding : SplashSideEffect()
        data object RestartApp: SplashSideEffect()
    }
}