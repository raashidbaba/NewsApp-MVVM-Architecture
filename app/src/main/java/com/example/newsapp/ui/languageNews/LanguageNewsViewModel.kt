package com.example.newsapp.ui.languageNews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.ApiSource
import com.example.newsapp.data.model.Code
import com.example.newsapp.data.repository.LanguageNewsRepository
import com.example.newsapp.ui.base.UiState
import com.example.newsapp.utils.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class LanguageNewsViewModel @Inject constructor(
    private val repository: LanguageNewsRepository,
    private val dispatcherProvider: DispatcherProvider
) :
    ViewModel() {

    private val _uiStateLanguage = MutableStateFlow<UiState<List<Code>>>(UiState.Loading)
    val uiStateLanguage: StateFlow<UiState<List<Code>>> = _uiStateLanguage

    private val _uiStateLanguageNews = MutableStateFlow<UiState<List<ApiSource>>>(UiState.Loading)
    val uiStateLanguageNews: StateFlow<UiState<List<ApiSource>>> = _uiStateLanguageNews


    fun getLanguageList() {
        viewModelScope.launch(dispatcherProvider.main) {
            repository.getLanguageList()
                .flowOn(dispatcherProvider.io)
                .catch {
                    _uiStateLanguage.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiStateLanguage.value = UiState.Success(it)
                }
        }
    }

    fun getLanguageBasedNews(language: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            repository.getLanguageBaseNews(language)
                .flowOn(dispatcherProvider.io)
                .catch {
                    _uiStateLanguageNews.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiStateLanguageNews.value = UiState.Success(it)
                }
        }
    }


}