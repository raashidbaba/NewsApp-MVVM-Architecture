package com.example.newsapp.data.remote

import com.example.newsapp.data.model.TopHeadlinesResponse
import com.example.newsapp.utils.AppConstant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NetworkService {

    @Headers("X-Api-Key: $API_KEY", "User-Agent: Robin hood")
    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String): TopHeadlinesResponse
}