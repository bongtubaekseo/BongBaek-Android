package com.bongtu.baekseo.presentation.profileedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.IncomeType
import com.bongtu.baekseo.core.local.datastore.UsernameDataStore
import com.bongtu.baekseo.core.util.TextFieldValidator.validateName
import com.bongtu.baekseo.data.model.member.ProfileInfo
import com.bongtu.baekseo.data.repository.member.MemberRepository
import com.bongtu.baekseo.presentation.profileedit.ProfileEditContract.ProfileEditSideEffect
import com.bongtu.baekseo.presentation.profileedit.ProfileEditContract.ProfileEditUiState
import com.bongtu.baekseo.presentation.profileedit.navigation.ProfileEdit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val memberRepository: MemberRepository,
    private val usernameDataStore: UsernameDataStore,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileEditUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initProfileEditState(savedStateHandle.toRoute<ProfileEdit>())
    }

    private val _sideEffect = MutableSharedFlow<ProfileEditSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private fun initProfileEditState(
        args: ProfileEdit,
    ) = _uiState.update {
        it.copy(
            userName = args.userName,
            userBirth = args.userBirth,
            userIncome = args.userIncome,
            originalName = args.userName,
            originalBirth = args.userBirth,
            originalIncome = args.userIncome,
        )
    }

    fun patchUserProfile() = viewModelScope.launch {
        updateLoadState(UiState.Loading)

        memberRepository.putProfileInfo(
            profileInfo = ProfileInfo(
                memberName = uiState.value.userName,
                memberBirthday = uiState.value.userBirth,
                memberIncome = uiState.value.userIncome.label,
            ),
        ).onSuccess {
            updateLoadState(UiState.Success(Unit))
            _sideEffect.emit(ProfileEditSideEffect.NavigateToSetting)
            usernameDataStore.setUsername(uiState.value.userName)
        }.onFailure {
            updateLoadState(UiState.Failure(it.message ?: "Unknown Error"))
        }
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

    fun updateIncomeButtonState(incomeType: IncomeType): Boolean? =
        with(uiState.value) {
            return when (userIncome) {
                IncomeType.NONE -> null
                incomeType -> true
                else -> false
            }
        }

    private fun updateLoadState(newLoadState: UiState<Unit>) = _uiState.update {
        it.copy(loadState = newLoadState)
    }
}
