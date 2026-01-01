package com.example.newsapp.di.module

import android.content.Context
import com.example.newsapp.NewsApplication
import com.example.newsapp.data.remote.NetworkService
import com.example.newsapp.di.ApplicationContext
import com.example.newsapp.di.BaseUrl
import com.example.newsapp.di.NetworkApiKey
import com.example.newsapp.di.NetworkUserAgent
import com.example.newsapp.utils.AppConstant
import com.example.newsapp.utils.DefaultDispatcherProvider
import com.example.newsapp.utils.DispatcherProvider
import com.example.newsapp.utils.HeaderInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: NewsApplication) {

    @ApplicationContext
    @Provides
    fun provideContext(): Context {
        return application
    }

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://newsapi.org/v2/"

    @Provides
    @NetworkApiKey
    fun provideApiKey(): String = AppConstant.API_KEY

    @Provides
    @NetworkUserAgent
    fun provideUserAgent(): String = AppConstant.USER_AGENT

    @Provides
    @Singleton
    fun provideHeaderInterceptor(
        @NetworkApiKey apiKey: String,
        @NetworkUserAgent userAgent: String
    ): HeaderInterceptor =
        HeaderInterceptor(apiKey, userAgent)

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideOkHttpClient(headerInterceptor: HeaderInterceptor): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(headerInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService::class.java)
    }


    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()


}