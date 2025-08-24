package com.bongtu.baekseo.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.domain.usecase.auth.CheckAutoLoginUseCase
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
) : ViewModel() {
    private val _sideEffect = MutableSharedFlow<SplashSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun postTokenReissue() {
        viewModelScope.launch {
            checkAutoLoginUseCase.invoke()
                .onSuccess {
                    _sideEffect.emit(NavigateToHome)
                }.onFailure { error ->
                    val message = error.message.orEmpty()

                    if (message.contains("401") || message.contains("404"))
                        _sideEffect.emit(RestartApp)
                    else
                        _sideEffect.emit(NavigateToOnBoarding)
                }
        }
    }
}