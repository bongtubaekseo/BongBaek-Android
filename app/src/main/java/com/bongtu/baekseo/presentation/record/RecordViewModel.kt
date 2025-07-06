package com.bongtu.baekseo.presentation.record

import androidx.lifecycle.ViewModel
import com.bongtu.baekseo.data.repository.DummyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val dummyRepository: DummyRepository,
) : ViewModel() {

}