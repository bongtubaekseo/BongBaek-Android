package com.bongtu.baekseo.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.designsystem.component.textfield.TextFieldValidateResult
import com.bongtu.baekseo.data.model.map.Place
import com.bongtu.baekseo.presentation.edit.EditContract.EditSideEffect.EditLocationSideEffect
import com.bongtu.baekseo.presentation.edit.EditContract.EditSideEffect.EditMainSideEffect
import com.bongtu.baekseo.presentation.edit.EditContract.EditUiState
import com.bongtu.baekseo.presentation.edit.type.EditEntryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    // TODO: repository 추가 예정
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<EditContract.EditSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private val _nameValidate =
        MutableStateFlow<TextFieldValidateResult>(TextFieldValidateResult.Default)
    val nameValidate = _nameValidate.asStateFlow()

    private val _nickNameValidate =
        MutableStateFlow<TextFieldValidateResult>(TextFieldValidateResult.Default)
    val nickNameValidate = _nickNameValidate.asStateFlow()

    private val _costValidate =
        MutableStateFlow<TextFieldValidateResult>(TextFieldValidateResult.Default)
    val costValidate = _costValidate.asStateFlow()

    fun updateName(newName: String) {
        _uiState.update {
            it.copy(name = newName)
        }
        _nameValidate.value = TextFieldValidateResult.validate(newName)
    }

    fun updateNickname(newNickname: String) {
        _uiState.update {
            it.copy(nickname = newNickname)
        }
        _nickNameValidate.value = TextFieldValidateResult.validate(newNickname)
    }

    fun updateEventCategory(newEventCategory: String) = _uiState.update {
        it.copy(eventCategory = newEventCategory)
    }

    fun updateRelationship(newRelationship: String) = _uiState.update {
        it.copy(relationship = newRelationship)
    }

    fun updateCost(newCost: String) {
        _uiState.update {
            it.copy(cost = newCost)
        }
        _costValidate.value = TextFieldValidateResult.validateCost(newCost)
    }

    fun updateAttendLabel(newAttendLabel: String) = _uiState.update {
        it.copy(
            attendLabel = newAttendLabel,
        )
    }

    fun updateEventDate(newEventDate: String) = _uiState.update {
        it.copy(
            eventDate = newEventDate,
        )
    }

    fun updateNote(newNote: String) = _uiState.update {
        it.copy(
            note = newNote,
        )
    }

    fun updatePlace(newPlace: Place?) = _uiState.update {
        it.copy(selectedPlace = newPlace)
    }

    fun updateSearchTerm(newSearchTerm: String) = _uiState.update {
        it.copy(searchTerm = newSearchTerm)
    }


    fun updateButtonState(): Boolean = with(uiState.value) {
        name.isNotBlank() && nameValidate.value == TextFieldValidateResult.Default &&
                nickname.isNotBlank() && nickNameValidate.value == TextFieldValidateResult.Default &&
                eventCategory.isNotBlank() &&
                relationship.isNotBlank() &&
                cost.isNotBlank() && costValidate.value == TextFieldValidateResult.Default &&
                attendLabel.isNotBlank() &&
                eventDate.isNotBlank()
    }

    fun submitEventInformation(entryType: EditEntryType) {
        when (entryType) {
            EditEntryType.FROM_RECORD -> saveEventInformation()
            EditEntryType.FROM_SCHEDULE -> patchEventInformation()
            EditEntryType.FROM_DETAIL -> patchEventInformation()
            EditEntryType.FROM_RESULT -> saveEventInformation()
        }
    }

    private fun patchEventInformation() =
        viewModelScope.launch {
            // TODO: 수정 API 호출
            _sideEffect.emit(EditMainSideEffect.NavigateToComplete)

        }

    private fun saveEventInformation() =
        viewModelScope.launch {
            // TODO: 생성 API 호출
            _sideEffect.emit(EditMainSideEffect.NavigateToComplete)
        }

    fun navigateToLocation() = viewModelScope.launch {
        _sideEffect.emit(EditMainSideEffect.NavigateToLocation)
    }

    fun navigateToEditMain() = viewModelScope.launch {
        _sideEffect.emit(EditLocationSideEffect.NavigateToEditMain)
    }
}