package com.bongtu.baekseo.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.domain.usecase.auth.CheckAutoLoginUseCase
import com.bongtu.baekseo.presentation.splash.SplashContract.SplashSideEffect
import com.bongtu.baekseo.presentation.splash.SplashContract.SplashSideEffect.NavigateToHome
import com.bongtu.baekseo.presentation.splash.SplashContract.SplashSideEffect.NavigateToOnBoarding
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
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
//                    _sideEffect.emit(NavigateToHome)
                    _sideEffect.emit(NavigateToOnBoarding)
                }.onFailure { error ->
                    Timber.e("토큰 재발급 실패: ${error.message}")
                    _sideEffect.emit(NavigateToOnBoarding)
                }
        }
    }
}