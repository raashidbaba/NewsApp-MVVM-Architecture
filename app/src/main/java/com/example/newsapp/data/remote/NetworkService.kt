package com.example.newsapp.data.remote

import com.example.newsapp.data.model.TopHeadlineSources
import com.example.newsapp.data.model.TopHeadlinesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String): TopHeadlinesResponse

    @GET("top-headlines/sources")
    suspend fun getPaginatedTopHeadline(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): TopHeadlineSources
}