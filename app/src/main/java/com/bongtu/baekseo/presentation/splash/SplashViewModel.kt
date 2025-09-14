package com.bongtu.baekseo.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.BuildConfig
import com.bongtu.baekseo.domain.usecase.auth.CheckAutoLoginUseCase
import com.bongtu.baekseo.domain.usecase.config.CheckAppVersionUseCase
import com.bongtu.baekseo.presentation.splash.SplashContract.SplashSideEffect
import com.bongtu.baekseo.presentation.splash.SplashContract.SplashSideEffect.NavigateToHome
import com.bongtu.baekseo.presentation.splash.SplashContract.SplashSideEffect.NavigateToOnBoarding
import com.bongtu.baekseo.presentation.splash.SplashContract.SplashSideEffect.RestartApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkAutoLoginUseCase: CheckAutoLoginUseCase,
    private val checkAppVersionUseCase: CheckAppVersionUseCase,
) : ViewModel() {
    private val _sideEffect = MutableSharedFlow<SplashSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun initialize() {
        viewModelScope.launch {
            checkAppVersionUseCase(BuildConfig.VERSION_NAME)

            checkAutoLoginUseCase()
                .onSuccess {
                    _sideEffect.emit(NavigateToHome)
                }.onFailure { error ->
                    if (error is IllegalStateException) {
                        _sideEffect.emit(RestartApp)
                    } else {
                        _sideEffect.emit(NavigateToOnBoarding)
                    }
                }
        }
    }
}
