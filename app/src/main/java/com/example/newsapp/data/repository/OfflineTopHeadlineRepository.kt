package com.example.newsapp.data.repository

import com.example.newsapp.data.local.DatabaseService
import com.example.newsapp.data.local.entity.Source
import com.example.newsapp.data.model.toSourceEntity
import com.example.newsapp.data.remote.NetworkService
import com.example.newsapp.di.ActivityScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@ActivityScope
class OfflineTopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {


    fun getTopHeadlineSources(): Flow<List<Source>> {

        return flow {

            emit(networkService.getTopHeadlines())
        }.map {
            it.sources.map { apiSource ->
                apiSource.toSourceEntity()
            }
        }.flatMapConcat { articles ->
            flow {
                emit(databaseService.deleteAllAndInsertAll((articles)))
            }
        }.flatMapConcat {
            databaseService.getSources()
        }
    }

    fun getSourcesDirectlyFromDB(): Flow<List<Source>> {
        return databaseService.getSources()
    }
}






