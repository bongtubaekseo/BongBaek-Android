package com.bongtu.baekseo.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.LoginType
import com.bongtu.baekseo.domain.usecase.auth.SetKakaoLoginUseCase
import com.bongtu.baekseo.domain.usecase.config.GetUpdateFlagUseCase
import com.bongtu.baekseo.presentation.login.LoginContract.LoginSideEffect
import com.bongtu.baekseo.presentation.login.LoginContract.LoginSideEffect.NavigateToHome
import com.bongtu.baekseo.presentation.login.LoginContract.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val setKakaoLoginUseCase: SetKakaoLoginUseCase,
    private val getUpdateFlagUseCase: GetUpdateFlagUseCase,
) : ViewModel() {
    private val _kakaoLoginState = MutableStateFlow<SocialLoginState>(SocialLoginState.Idle)
    val kakaoLoginState = _kakaoLoginState.asStateFlow()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<LoginSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    val isUpdateDialogVisible = getUpdateFlagUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false,
        )

    fun setUiStateIdle() =
        _kakaoLoginState.tryEmit(SocialLoginState.Idle)

    fun loginWithKakao(token: String) =
        viewModelScope.launch {
            updateLoginUiState(UiState.Loading)

            setKakaoLoginUseCase(
                oauthProvider = LoginType.KAKAO.label,
                idToken = token,
            ).onSuccess { response ->
                updateLoginUiState(UiState.Success(Unit))

                updateKakaoId(response.kakaoId)
                if (response.isCompletedSignUp) {
                    _sideEffect.emit(NavigateToHome)
                } else {
                    _kakaoLoginState.tryEmit(SocialLoginState.Success)
                }
            }.onFailure {
                Timber.d("kakaoLogin: $it")
                _kakaoLoginState.tryEmit(SocialLoginState.Fail)
            }
        }

    fun updateLoginUiState(value: UiState<Unit>) =
        _uiState.update { currentState ->
            currentState.copy(
                loadState = value,
            )
        }

    private fun updateKakaoId(newKakaoId: String) =
        _uiState.update {
            it.copy(kakaoId = newKakaoId)
        }
}