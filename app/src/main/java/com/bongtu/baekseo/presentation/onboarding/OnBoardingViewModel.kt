package com.bongtu.baekseo.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.local.datastore.TokenDataStore
import com.bongtu.baekseo.core.local.datastore.UsernameDataStore
import com.bongtu.baekseo.core.util.TextFieldValidator.validateName
import com.bongtu.baekseo.data.repository.auth.AuthRepository
import com.bongtu.baekseo.domain.usecase.auth.SetKakaoLoginUseCase
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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val usernameDataStore: UsernameDataStore,
    private val tokenDataStore: TokenDataStore,
    private val authRepository: AuthRepository,
    private val setKakaoLoginUseCase: SetKakaoLoginUseCase,
) : ViewModel() {
    private val _kakaoLoginState = MutableStateFlow<SocialLoginState>(SocialLoginState.Idle)
    val kakaoLoginState = _kakaoLoginState.asStateFlow()

    private val _uiState = MutableStateFlow(OnBoardingUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<OnBoardingSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun updateName(newName: String) = _uiState.update {
        it.copy(
            name = newName,
            nameError = validateName(newName)
        )
    }

    fun updateBirth(newBirth: String) = _uiState.update {
        it.copy(birth = newBirth)
    }

    fun updateDialogBirth(newDialogBirth: String) = _uiState.update {
        it.copy(dialogBirth = newDialogBirth)
    }

    fun updateIncome(newIncome: String) = _uiState.update {
        it.copy(income = newIncome)
    }

    fun setUiStateIdle() =
        _kakaoLoginState.tryEmit(SocialLoginState.Idle)

    fun loginWithKakao(token: String) =
        viewModelScope.launch {
            setKakaoLoginUseCase(token)
                .onSuccess { response ->
                    updateKakaoId(response.kakaoId)
                    if (response.isCompletedSignUp) {
                        _sideEffect.emit(NavigateToHome)
                    } else {
                        _kakaoLoginState.tryEmit(SocialLoginState.Success)
                    }
                }
                .onFailure {
                    Timber.d("kakaoLogin: $it")
                    _kakaoLoginState.tryEmit(SocialLoginState.Fail)
                }
        }

    fun postSignUp() =
        viewModelScope.launch {
            authRepository.postSignUp(
                kakaoId = uiState.value.kakaoId,
                memberName = uiState.value.name,
                memberBirthday = uiState.value.birth,
                memberIncome = uiState.value.income,
            ).onSuccess { response ->
                saveUsername(response.name)
                tokenDataStore.setTokens(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                )
                _sideEffect.emit(NavigateToHome)
            }.onFailure {
                updateOnBoardingUiState(UiState.Failure(it.message ?: "Unknown Error"))
            }
        }

    private fun updateOnBoardingUiState(value: UiState<Nothing>) =
        _uiState.update { currentState ->
            currentState.copy(
                loadState = value,
            )
        }

    private fun updateKakaoId(newKakaoId: String) =
        _uiState.update {
            it.copy(kakaoId = newKakaoId)
        }

    fun updateButtonState(): Boolean =
        with(uiState.value) {
            return name.isNotEmpty() && birth.isNotEmpty() && nameError.isEmpty()
        }

    private fun saveUsername(name: String) =
        viewModelScope.launch {
            usernameDataStore.setUsername(name)
        }
}