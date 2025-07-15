package com.bongtu.baekseo.presentation.edit

import androidx.lifecycle.ViewModel
import com.bongtu.baekseo.core.designsystem.component.textfield.TextFieldValidateResult
import com.bongtu.baekseo.data.model.map.Place
import com.bongtu.baekseo.presentation.edit.EditContract.EditUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    // TODO: repository 추가 예정
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditUiState())
    val uiState = _uiState.asStateFlow()

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

    fun updateLocation(newLocation: Place?) = _uiState.update {
        it.copy(selectedPlace = newLocation)
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
}