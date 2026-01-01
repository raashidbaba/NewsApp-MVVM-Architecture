package com.example.newsapp.data.repository

import com.example.newsapp.data.model.ApiSource
import com.example.newsapp.data.model.Code
import com.example.newsapp.data.remote.NetworkService
import com.example.newsapp.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LanguageNewsRepository @Inject constructor(private val networkService: NetworkService) {

    fun getLanguageList(): Flow<List<Code>> {
        return flow {
            emit(AppConstant.LANGUAGES)
        }

    }

    fun getLanguageBaseNews(language: String): Flow<List<ApiSource>>{
        return flow {
            emit(networkService.getLanguageBasedNews(language))
        }.map { it.sources }
    }


}