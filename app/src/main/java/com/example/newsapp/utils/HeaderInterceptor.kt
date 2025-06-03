package com.example.newsapp.utils

import com.example.newsapp.di.NetworkApiKey
import com.example.newsapp.di.NetworkUserAgent
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(
    @NetworkApiKey private val apiKey: String,
    @NetworkUserAgent private val userAgent: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("X-Api-Key", apiKey)
            .header("User-Agent", userAgent)
            .build()
        return chain.proceed(newRequest)
    }

}