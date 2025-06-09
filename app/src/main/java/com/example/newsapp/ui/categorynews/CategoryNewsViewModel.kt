package com.example.newsapp.ui.categorynews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.ApiSource
import com.example.newsapp.data.repository.CategoryNewsRepository
import com.example.newsapp.ui.base.UiState
import com.example.newsapp.utils.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryNewsViewModel @Inject constructor(
    private val categoryNewsRepository: CategoryNewsRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _uiStateCategory = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val uiStateCategory: StateFlow<UiState<List<String>>> = _uiStateCategory

    private val _uiStateCategoryNews = MutableStateFlow<UiState<List<ApiSource>>>(UiState.Loading)
    val uiStateCategoryNews: StateFlow<UiState<List<ApiSource>>> = _uiStateCategoryNews


    fun getCategories() {
        viewModelScope.launch(dispatcherProvider.main) {
            categoryNewsRepository.getCategories()
                .flowOn(dispatcherProvider.io)
                .catch {
                    _uiStateCategory.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiStateCategory.value = UiState.Success(it)
                }
        }
    }

    fun getNewsFromCategories(category: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            categoryNewsRepository.getCategoryNews(category)
                .flowOn(dispatcherProvider.io)
                .catch {
                    _uiStateCategoryNews.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiStateCategoryNews.value = UiState.Success(it)
                }
        }
    }


}