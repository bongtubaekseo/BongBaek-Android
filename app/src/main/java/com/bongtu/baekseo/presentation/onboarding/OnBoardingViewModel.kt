package com.bongtu.baekseo.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(

) : ViewModel() {
    private val _kakaoLoginState = MutableStateFlow<SocialLoginState>(SocialLoginState.Idle)
    val kakaoLoginState = _kakaoLoginState.asStateFlow()

    fun kakaoLoginSuccess() {
        _kakaoLoginState.tryEmit(SocialLoginState.Success)
    }

    fun kakaoLoginFail() {
        _kakaoLoginState.tryEmit(SocialLoginState.Fail)
    }

    fun setUiStateIdle() {
        _kakaoLoginState.tryEmit(SocialLoginState.Idle)
    }

    fun loginWithKakao(token: String) {
        viewModelScope.launch {
            _kakaoLoginState.update {
                SocialLoginState.Success
            }
        }
    }
}