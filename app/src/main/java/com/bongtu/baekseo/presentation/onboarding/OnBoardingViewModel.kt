package com.bongtu.baekseo.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.local.datastore.UsernameDataStore
import com.bongtu.baekseo.presentation.onboarding.OnBoardingContract.OnBoardingSideEffect
import com.bongtu.baekseo.presentation.onboarding.OnBoardingContract.OnBoardingSideEffect.NavigateToHome
import com.bongtu.baekseo.presentation.onboarding.OnBoardingContract.OnBoardingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val usernameDataStore: UsernameDataStore,
) : ViewModel() {
    private val _kakaoLoginState = MutableStateFlow<SocialLoginState>(SocialLoginState.Idle)
    val kakaoLoginState = _kakaoLoginState.asStateFlow()

    private val _uiState = MutableStateFlow(OnBoardingUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<OnBoardingSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun saveUsername(name: String) {
        viewModelScope.launch {
            usernameDataStore.setUsername(name)
            _sideEffect.emit(NavigateToHome)
        }
    }

    fun updateName(newName: String) = _uiState.update {
        it.copy(name = newName)
    }

    fun updateBirth(newBirth: String) = _uiState.update {
        it.copy(birth = newBirth)
    }

    fun updateIncome(newIncome: String) = _uiState.update {
        it.copy(income = newIncome)
    }

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