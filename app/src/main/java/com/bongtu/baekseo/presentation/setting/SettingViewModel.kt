package com.bongtu.baekseo.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.IncomeType
import com.bongtu.baekseo.core.local.datastore.TokenDataStore
import com.bongtu.baekseo.core.local.datastore.UsernameDataStore
import com.bongtu.baekseo.core.util.TextFieldValidator.validateName
import com.bongtu.baekseo.data.model.member.ProfileInfo
import com.bongtu.baekseo.data.repository.member.MemberRepository
import com.bongtu.baekseo.presentation.setting.SettingContract.SettingSideEffect
import com.bongtu.baekseo.presentation.setting.SettingContract.SettingSideEffect.MainSideEffect.RestartApp
import com.bongtu.baekseo.presentation.setting.SettingContract.SettingSideEffect.ProfileEditSideEffect.NavigateToSetting
import com.bongtu.baekseo.presentation.setting.SettingContract.SettingUiState
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
class SettingViewModel @Inject constructor(
    private val memberRepository: MemberRepository,
    private val tokenDataStore: TokenDataStore,
    private val usernameDataStore: UsernameDataStore,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<SettingSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun fetchUserProfile() = viewModelScope.launch {
        updateSettingLoadUiState(UiState.Loading)

        memberRepository.getProfileInfo()
            .onSuccess { response ->
                _uiState.update {
                    it.copy(
                        userName = response.memberName,
                        userBirth = response.memberBirthday,
                        userIncome = IncomeType.getIncomeType(response.memberIncome),
                    )
                }
                updateOriginProfileState(
                    newName = _uiState.value.userName,
                    newBirth = uiState.value.userBirth,
                    newIncome = uiState.value.userIncome,
                )
                updateSettingLoadUiState(UiState.Success(Unit))
            }
            .onFailure {
                updateSettingLoadUiState(UiState.Failure(it.message ?: "Unknown Error"))
            }
    }

    fun patchUserProfile() = viewModelScope.launch {
        updateSettingLoadUiState(UiState.Loading)

        memberRepository.putProfileInfo(
            profileInfo = ProfileInfo(
                memberName = uiState.value.userName,
                memberBirthday = uiState.value.userBirth,
                memberIncome = uiState.value.userIncome.label,
            ),
        ).onSuccess {
            _sideEffect.emit(NavigateToSetting)
            saveUsername(uiState.value.userName)
        }.onFailure {
            // TODO: 실패 처리
            Timber.d("patchUserProfile: $it")
        }
    }

    fun logout() =
        viewModelScope.launch {
            memberRepository.postLogout()
                .onSuccess {
                    _sideEffect.emit(RestartApp)
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

    fun clearProfileEditState() = _uiState.update {
        it.copy(
            originalName = "",
            originalBirth = "",
            originalIncome = IncomeType.NONE,
            nameError = "",
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

    fun updateSettingLoadUiState(newUiState: UiState<Unit>) = _uiState.update {
        it.copy(loadState = newUiState)
    }

    fun updateIncomeButtonState(incomeType: IncomeType): Boolean? =
        with(uiState.value) {
            return when (userIncome) {
                IncomeType.NONE -> null
                incomeType -> true
                else -> false
            }
        }

    private fun saveUsername(name: String) =
        viewModelScope.launch {
            usernameDataStore.setUsername(name)
        }
}
