package com.example.newsapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.model.ApiSource
import com.example.newsapp.data.remote.NetworkService
import com.example.newsapp.utils.AppConstant.INITIAL_PAGE
import com.example.newsapp.utils.AppConstant.PAGE_SIZE


class TopHeadlinePagingSource(private val networkService: NetworkService) :
    PagingSource<Int, ApiSource>() {
    override fun getRefreshKey(state: PagingState<Int, ApiSource>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiSource> {
        return try {
            val page = params.key ?: INITIAL_PAGE
            val response = networkService.getPaginatedTopHeadline(
                page = page,
                pageSize = PAGE_SIZE
            )
            LoadResult.Page(
                data = response.sources,
                prevKey = if (page == INITIAL_PAGE) null else page.minus(1),
                nextKey = if (response.sources.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}