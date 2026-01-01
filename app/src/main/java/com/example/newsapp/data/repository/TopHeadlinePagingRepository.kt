package com.example.newsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.data.model.ApiSource
import com.example.newsapp.data.remote.NetworkService
import com.example.newsapp.di.ActivityScope
import com.example.newsapp.utils.AppConstant.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlinePagingRepository @Inject constructor(private val networkService: NetworkService) {
    fun getTopHeadlines(): Flow<PagingData<ApiSource>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { TopHeadlinePagingSource(networkService) }
        ).flow
    }
}