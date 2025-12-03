package com.bongtu.baekseo.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.IncomeType
import com.bongtu.baekseo.core.local.datastore.TokenDataStore
import com.bongtu.baekseo.data.repository.member.MemberRepository
import com.bongtu.baekseo.presentation.setting.SettingContract.SettingSideEffect
import com.bongtu.baekseo.presentation.setting.SettingContract.SettingSideEffect.RestartApp
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
                updateSettingLoadUiState(UiState.Success(Unit))
            }
            .onFailure {
                updateSettingLoadUiState(UiState.Failure(it.message ?: "Unknown Error"))
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

    fun updateSettingLoadUiState(newUiState: UiState<Unit>) = _uiState.update {
        it.copy(loadState = newUiState)
    }
}
