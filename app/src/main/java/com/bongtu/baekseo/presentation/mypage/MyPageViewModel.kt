package com.bongtu.baekseo.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.IncomeType
import com.bongtu.baekseo.core.local.datastore.TokenDataStore
import com.bongtu.baekseo.core.util.TextFieldValidator.validateName
import com.bongtu.baekseo.data.model.member.ProfileInfo
import com.bongtu.baekseo.data.repository.member.MemberRepository
import com.bongtu.baekseo.presentation.mypage.MyPageContract.MyPageSideEffect
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
    private val memberRepository: MemberRepository,
    private val tokenDataStore: TokenDataStore,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<MyPageSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun fetchUserProfile() = viewModelScope.launch {
        updateMyPageLoadUiState(UiState.Loading)

        memberRepository.getProfileInfo()
            .onSuccess { response ->
                _uiState.update {
                    it.copy(
                        userName = response.memberName,
                        userBirth = response.memberBirthday,
                        userIncome = IncomeType.getIncomeType(response.memberIncome),
                    )
                }
                updateMyPageLoadUiState(UiState.Success(Unit))
            }
            .onFailure {
                updateMyPageLoadUiState(UiState.Failure(it.message ?: "Unknown Error"))
            }
    }

    fun patchUserProfile() = viewModelScope.launch {
        memberRepository.putProfileInfo(
            profileInfo = ProfileInfo(
                memberName = uiState.value.userName,
                memberBirthday = uiState.value.userBirth,
                memberIncome = uiState.value.userIncome.label,
            ),
        ).onSuccess {
            _sideEffect.emit(MyPageSideEffect.ProfileEditSideEffect.NavigateToMyPage)
        }.onFailure {
            // TODO: 실패 처리
            Timber.d("patchUserProfile: $it")
        }
    }

    fun logout() =
        viewModelScope.launch {
            memberRepository.postLogout()
                .onSuccess {
                    _sideEffect.emit(MyPageSideEffect.MainSideEffect.RestartApp)
                    tokenDataStore.clearInfo()
                }
                .onFailure {
                    Timber.d("logout: $it")
                }
        }

    fun updateOriginProfileState(newName: String, newBirth: String, newIncome: IncomeType) =
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

    fun updateUserIncome(newIncome: IncomeType) = _uiState.update {
        it.copy(userIncome = newIncome)
    }

    fun updateMyPageLoadUiState(newUiState: UiState<Unit>) = _uiState.update {
        it.copy(myPageLoadState = newUiState)
    }
}