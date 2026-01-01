package com.example.newsapp.ui.topheadlinepagination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.newsapp.data.model.ApiSource
import com.example.newsapp.data.repository.TopHeadlinePagingRepository
import com.example.newsapp.ui.base.UiState
import com.example.newsapp.utils.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


class TopHeadlinePaginationViewModel @Inject constructor(
    private val repository: TopHeadlinePagingRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<PagingData<ApiSource>>>(UiState.Loading)
    val uiState: StateFlow<UiState<PagingData<ApiSource>>> = _uiState

    init {
        getTopHeadlineSources()
    }

    private fun getTopHeadlineSources() {
        viewModelScope.launch(dispatcherProvider.main) {
            repository.getTopHeadlines()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = UiState.Error(e.message.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}