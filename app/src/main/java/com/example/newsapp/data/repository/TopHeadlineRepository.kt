package com.example.newsapp.data.repository

import com.example.newsapp.data.model.ApiSource
import com.example.newsapp.data.remote.NetworkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {

    fun getTopHeadlines(): Flow<List<ApiSource>> {
        return flow {
            emit(networkService.getTopHeadlines())
        }.map {
            it.sources
        }
    }

}