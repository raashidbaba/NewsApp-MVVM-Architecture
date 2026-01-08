package com.example.newsapp.data.remote

import com.example.newsapp.data.model.NewsSourcesResponse
import com.example.newsapp.data.model.TopHeadlineSources
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("top-headlines/sources")
    suspend fun getTopHeadlines(): TopHeadlineSources

    @GET("top-headlines/sources")
    suspend fun getPaginatedTopHeadline(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): TopHeadlineSources

    @GET("top-headlines/sources")
    suspend fun getCategoryBasedNews(@Query("category") category: String): NewsSourcesResponse

    @GET("top-headlines/sources")
    suspend fun getCountryBasedNews(@Query("country") country: String): NewsSourcesResponse

    @GET("top-headlines/sources")
    suspend fun getLanguageBasedNews(@Query("language")language: String): NewsSourcesResponse
}



