package com.bongtu.baekseo.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.type.IncomeType
import com.bongtu.baekseo.core.local.datastore.TokenDataStore
import com.bongtu.baekseo.core.util.TextFieldValidator.validateName
import com.bongtu.baekseo.domain.usecase.auth.LogoutUseCase
import com.bongtu.baekseo.presentation.mypage.MyPageContract.MyPageSideEffect
import com.bongtu.baekseo.presentation.mypage.MyPageContract.MyPageSideEffect.RestartApp
import com.bongtu.baekseo.presentation.mypage.MyPageContract.MyPageUiState
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
class MyPageViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val tokenDataStore: TokenDataStore,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MyPageSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun fetchUserProfile() =        // TODO: 서버 통신 후 데이터 받아오기
        _uiState.update { currentState ->
            currentState.copy(
                userName = "봉투백서의겸손한야수",
                userBirth = "2000-01-05",
                userIncome = IncomeType.OVER_200.label,
            )
        }

    fun logout() =
        viewModelScope.launch {
            logoutUseCase()
                .onSuccess {
                    _sideEffect.emit(RestartApp)
                    tokenDataStore.clearInfo()
                }
                .onFailure {
                    Timber.d("logout: $it")
                }
        }

    fun updateOriginProfileState(newName: String, newBirth: String, newIncome: String) =
        _uiState.update {
            it.copy(
                originalName = newName,
                originalBirth = newBirth,
                originalIncome = newIncome,
            )
        }

    fun updateUserName(newName: String) = _uiState.update {
        it.copy(userName = newName, nameError = validateName(newName))
    }

    fun updateUserBirth(newBirth: String) = _uiState.update {
        it.copy(userBirth = newBirth)
    }

    fun updateDialogBirth(newDialogBirth: String) = _uiState.update {
        it.copy(dialogBirth = newDialogBirth)
    }

    fun updateUserIncome(newIncome: String) = _uiState.update {
        it.copy(userIncome = newIncome)
    }
}