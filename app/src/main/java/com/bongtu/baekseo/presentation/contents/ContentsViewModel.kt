package com.bongtu.baekseo.presentation.contents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bongtu.baekseo.core.common.state.UiState
import com.bongtu.baekseo.core.common.type.EventCategoryType
import com.bongtu.baekseo.data.repository.content.ContentsRepository
import com.bongtu.baekseo.presentation.contents.ContentsContract.ContentsSideEffect
import com.bongtu.baekseo.presentation.contents.ContentsContract.ContentsSideEffect.NavigateToContentsDetail
import com.bongtu.baekseo.presentation.contents.ContentsContract.ContentsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentsViewModel @Inject constructor(
    private val contentsRepository: ContentsRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ContentsUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<ContentsSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    private var currentPage = 0
    private var isLastPage = false

    init {
        fetchContents(currentPage)
    }

    private fun fetchContents(requestedPage: Int) = viewModelScope.launch {
        if (isLastPage || uiState.value.loadState is UiState.Loading) return@launch

        val isInitialLoad = currentPage == 0
        val pageToLoad = requestedPage
        val categoryParam = uiState.value.selectedEvent.paramLabel

        updateLoadState(UiState.Loading)

        contentsRepository.getContentsByPage(
            page = pageToLoad,
            category = categoryParam,
        ).onSuccess {
            _uiState.update { currentState ->
                currentState.copy(
                    articles = (currentState.articles + it.contents).toImmutableList(),
                )
            }

            currentPage = it.currentPage
            isLastPage = it.isLast
            if (isInitialLoad) updateArticleCount(it.totalContentsCount)

            updateLoadState(UiState.Success(Unit))
        }.onFailure {
            updateLoadState(UiState.Failure(it.message.orEmpty()))
        }
    }

    fun fetchNextContents() {
        if (!isLastPage) {
            val next = currentPage + 1
            fetchContents(requestedPage = next)
        }
    }

    fun fetchContentsDetail(contentId: String) = viewModelScope.launch {
        contentsRepository.getContentsDetail(contentId)
            .onSuccess {
                _uiState.update { currentState ->
                    currentState.copy(contentsDetail = it)
                }
                _sideEffect.emit(NavigateToContentsDetail(it))
            }.onFailure {
                updateLoadState(UiState.Failure(it.message.orEmpty()))
            }
    }

    private fun updateLoadState(newLoadState: UiState<Unit>) = _uiState.update { currentState ->
        currentState.copy(loadState = newLoadState)
    }

    private fun updateArticleCount(newArticleCount: Int) = _uiState.update { currentState ->
        currentState.copy(articleCount = newArticleCount)
    }

    fun updateCategory(category: EventCategoryType) {
        currentPage = 0
        isLastPage = false

        _uiState.update { currentState ->
            currentState.copy(
                selectedEvent = category,
                articles = persistentListOf(),
            )
        }

        fetchContents(currentPage)
    }
}
