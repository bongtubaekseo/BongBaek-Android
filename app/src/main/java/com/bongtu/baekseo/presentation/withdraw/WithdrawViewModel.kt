package com.bongtu.baekseo.presentation.withdraw

import androidx.lifecycle.ViewModel
import com.bongtu.baekseo.core.common.type.WithdrawType
import com.bongtu.baekseo.presentation.withdraw.WithdrawContract.WithdrawUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(WithdrawUiState())
    val uiState = _uiState.asStateFlow()

    fun updateReasonType(newReasonType: WithdrawType) = _uiState.update {
        it.copy(reasonType = newReasonType)
    }

    fun updateButtonState(): Boolean =
        with(uiState.value) {
            return reasonType != null && (reasonType != WithdrawType.ETC || etcReason.isNotBlank() && etcReason.length < 50)
        }

    fun updateEtcReason(newEtcReason: String) = _uiState.update {
        it.copy(etcReason = newEtcReason)
    }
}