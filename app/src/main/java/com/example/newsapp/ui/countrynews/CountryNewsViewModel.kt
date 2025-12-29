package com.example.newsapp.ui.countrynews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.ApiSource
import com.example.newsapp.data.model.Code
import com.example.newsapp.data.repository.CountryNewsRepository
import com.example.newsapp.ui.base.UiState
import com.example.newsapp.utils.DispatcherProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountryNewsViewModel @Inject constructor(
    private val repository: CountryNewsRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiStateCountry = MutableStateFlow<UiState<List<Code>>>(UiState.Loading)
    val uiStateCountry: StateFlow<UiState<List<Code>>> = _uiStateCountry

    private val _uiStateNews = MutableStateFlow<UiState<List<ApiSource>>>(UiState.Loading)
    val uiStateNews: StateFlow<UiState<List<ApiSource>>> = _uiStateNews


    fun getCountriesList() {
        viewModelScope.launch(dispatcherProvider.main) {
            repository.getCountriesList()
                .flowOn(dispatcherProvider.io)
                .catch {
                    _uiStateCountry.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiStateCountry.value = UiState.Success(it)
                }
        }
    }


    fun getCountryNews(value: String) {
        viewModelScope.launch(dispatcherProvider.main) {
            repository.getCountriesNews(value)
                .flowOn(dispatcherProvider.io)
                .catch {
                    _uiStateNews.value = UiState.Error(it.message.toString())
                }
                .collect {
                    _uiStateNews.value = UiState.Success(it)
                }
        }
    }


}