package com.bongtu.baekseo.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.domain.usecase.auth.SetSocialLoginUseCase
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
    private val setSocialLoginUseCase: SetSocialLoginUseCase,
    private val getUpdateFlagUseCase: GetUpdateFlagUseCase,
) : ViewModel() {
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

    fun socialLogin(
        oauthProvider: String,
        idToken: String,
    ) = viewModelScope.launch {
        updateLoginUiState(UiState.Loading)

        setSocialLoginUseCase(
            oauthProvider = oauthProvider,
            idToken = idToken,
        ).onSuccess { response ->
            updateOAuthId(response.oauthId)
            if (response.isCompletedSignUp) {
                _sideEffect.emit(NavigateToHome)
                updateLoginUiState(UiState.Empty)
            } else {
                updateLoginUiState(UiState.Success(Unit))
            }
        }.onFailure {
            Timber.d("socialLogin: $it")
            updateLoginUiState(UiState.Failure(it.message ?: "Unknown Error"))
        }
    }

    fun updateLoginUiState(value: UiState<Unit>) =
        _uiState.update { currentState ->
            currentState.copy(
                loadState = value,
            )
        }

    private fun updateOAuthId(newOAuthId: String) =
        _uiState.update {
            it.copy(oauthId = newOAuthId)
        }
}
