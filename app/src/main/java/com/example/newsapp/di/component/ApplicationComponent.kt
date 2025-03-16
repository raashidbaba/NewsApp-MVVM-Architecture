package com.example.newsapp.di.component

import android.content.Context
import com.example.newsapp.NewsApplication
import com.example.newsapp.data.remote.NetworkService
import com.example.newsapp.data.repository.TopHeadlineRepository
import com.example.newsapp.di.ApplicationContext
import com.example.newsapp.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: NewsApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getNetworkService(): NetworkService

    fun getTopHeadlineRepository(): TopHeadlineRepository

}