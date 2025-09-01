package com.bongtu.baekseo.presentation.mypage

import androidx.lifecycle.ViewModel
import com.bongtu.baekseo.core.common.type.IncomeType
import com.bongtu.baekseo.core.util.TextFieldValidator.validateName
import com.bongtu.baekseo.presentation.mypage.MyPageContract.MyPageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchUserProfile() =        // TODO: 서버 통신 후 데이터 받아오기
        _uiState.update { currentState ->
            currentState.copy(
                userName = "봉투백서의겸손한야수",
                userBirth = "2000-01-05",
                userIncome = IncomeType.OVER_200.label,
            )
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