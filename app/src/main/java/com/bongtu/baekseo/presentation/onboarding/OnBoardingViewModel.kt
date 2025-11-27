package com.bongtu.baekseo.presentation.onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.IncomeType
import com.bongtu.baekseo.core.local.datastore.ApiKeyDataStore
import com.bongtu.baekseo.core.local.datastore.TokenDataStore
import com.bongtu.baekseo.core.local.datastore.UsernameDataStore
import com.bongtu.baekseo.core.util.TextFieldValidator.validateName
import com.bongtu.baekseo.data.repository.auth.AuthRepository
import com.bongtu.baekseo.presentation.onboarding.OnBoardingContract.OnBoardingSideEffect
import com.bongtu.baekseo.presentation.onboarding.OnBoardingContract.OnBoardingSideEffect.NavigateToHome
import com.bongtu.baekseo.presentation.onboarding.OnBoardingContract.OnBoardingSideEffect.NavigateToLogin
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
    savedStateHandle: SavedStateHandle,
    private val apiKeyDataStore: ApiKeyDataStore,
    private val usernameDataStore: UsernameDataStore,
    private val tokenDataStore: TokenDataStore,
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val oauthId: String? = savedStateHandle.get<String>(OAUTH_ID_KEY)
    private val oauthProvider: String? = savedStateHandle.get<String>(OAUTH_PROVIDER_KEY)

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

    fun updateIncome(newIncome: IncomeType) = _uiState.update {
        it.copy(income = newIncome)
    }

    fun postSignUp() =
        viewModelScope.launch {
            updateOnBoardingUiState(UiState.Loading)

            authRepository.postSignUp(
                oauthId = oauthId.orEmpty(),
                oauthProvider = oauthProvider.orEmpty(),
                memberName = uiState.value.name,
                memberBirthday = uiState.value.birth,
                memberIncome = uiState.value.income.label,
            ).onSuccess { response ->
                updateOnBoardingUiState(UiState.Success(Unit))

                saveUsername(response.name)
                saveApiKey(response.apiKey)
                tokenDataStore.setTokens(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken,
                )
                _sideEffect.emit(NavigateToHome)
            }.onFailure {
                updateOnBoardingUiState(UiState.Failure(it.message ?: "Unknown Error"))
            }
        }

    fun navigateToLogin() = viewModelScope.launch {
        updateOnBoardingUiState(UiState.Loading)
        updateOnBoardingState(
            newName = "",
            newBirth = "",
            newIncome = IncomeType.NONE,
            newNameError = "",
        )
        _sideEffect.emit(NavigateToLogin)
    }

    fun updateOnBoardingUiState(value: UiState<Unit>) =
        _uiState.update { currentState ->
            currentState.copy(
                loadState = value,
            )
        }

    fun updateOnBoardingState(
        newName: String,
        newBirth: String,
        newIncome: IncomeType,
        newNameError: String,
    ) = _uiState.update {
        it.copy(
            name = newName,
            birth = newBirth,
            income = newIncome,
            nameError = newNameError,
        )
    }

    fun updateButtonState(): Boolean =
        with(uiState.value) {
            return name.isNotEmpty() && birth.isNotEmpty() && nameError.isEmpty()
        }

    fun updateIncomeButtonState(incomeType: IncomeType): Boolean? =
        with(uiState.value) {
            return when (income) {
                IncomeType.NONE -> null
                incomeType -> true
                else -> false
            }
        }

    private fun saveUsername(name: String) =
        viewModelScope.launch {
            usernameDataStore.setUsername(name)
        }

    private fun saveApiKey(apiKey: String) =
        viewModelScope.launch {
            apiKeyDataStore.setApiKey(apiKey)
        }

    companion object {
        private const val OAUTH_ID_KEY = "oauthId"
        private const val OAUTH_PROVIDER_KEY = "oauthProvider"
    }
}
