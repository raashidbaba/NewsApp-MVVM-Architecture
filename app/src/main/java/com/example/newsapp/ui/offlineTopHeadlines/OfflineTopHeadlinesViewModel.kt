package com.example.newsapp.ui.offlineTopHeadlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.local.entity.Source
import com.example.newsapp.data.repository.OfflineTopHeadlineRepository
import com.example.newsapp.ui.base.UiState
import com.example.newsapp.utils.DispatcherProvider
import com.example.newsapp.utils.NetworkHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class OfflineTopHeadlinesViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    private val dispatcherProvider: DispatcherProvider,
    private val offlineTopHeadlineRepository: OfflineTopHeadlineRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<UiState<List<Source>>>(UiState.Loading)
        val uiState: StateFlow<UiState<List<Source>>> = _uiState


    init {
        if (networkHelper.isNetworkConnected()){
            fetchSources()
        }else{
            fetchSourcesDirectlyFromDb()
        }
    }


    private fun fetchSources() {
        viewModelScope.launch(dispatcherProvider.main) {
            offlineTopHeadlineRepository.getTopHeadlineSources()
                .flowOn(dispatcherProvider.io)
                .catch {
                    _uiState.value = UiState.Error(it.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }

        }
    }


    private fun fetchSourcesDirectlyFromDb(){
        viewModelScope.launch(dispatcherProvider.main) {
            offlineTopHeadlineRepository.getSourcesDirectlyFromDB()
                .flowOn(dispatcherProvider.io)
                .catch {
                    _uiState.value = UiState.Error(it.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

}