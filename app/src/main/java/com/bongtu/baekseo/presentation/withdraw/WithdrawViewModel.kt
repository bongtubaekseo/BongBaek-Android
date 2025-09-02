package com.bongtu.baekseo.presentation.withdraw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.type.WithdrawType
import com.bongtu.baekseo.core.local.datastore.TokenDataStore
import com.bongtu.baekseo.presentation.withdraw.WithdrawContract.WithdrawUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val tokenDataStore: TokenDataStore,
) : ViewModel() {
    private val _uiState = MutableStateFlow(WithdrawUiState())
    val uiState = _uiState.asStateFlow()

    fun updateReasonType(newReasonType: WithdrawType) {
        _uiState.update {
            it.copy(
                reasonType = newReasonType,
            )
        }
        if (newReasonType != WithdrawType.ETC) updateEtcReason("")
    }

    fun updateButtonState(): Boolean =
        with(uiState.value) {
            reasonType != null && (reasonType != WithdrawType.ETC || etcReason.isNotBlank() && etcReason.length < 50)
        }

    fun updateEtcReason(newEtcReason: String) = _uiState.update {
        it.copy(etcReason = newEtcReason)
    }

    fun clearToken() = viewModelScope.launch {
        tokenDataStore.clearInfo()
    }
}