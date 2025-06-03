package com.example.newsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.data.repository.TopHeadlinePagingRepository
import com.example.newsapp.data.repository.TopHeadlineRepository
import com.example.newsapp.di.ActivityContext
import com.example.newsapp.ui.base.ViewModelProviderFactory
import com.example.newsapp.ui.topheadline.TopHeadlineAdapter
import com.example.newsapp.ui.topheadline.TopHeadlineViewModel
import com.example.newsapp.ui.topheadlinepagination.TopHeadlinePaginationAdapter
import com.example.newsapp.ui.topheadlinepagination.TopHeadlinePaginationViewModel
import com.example.newsapp.utils.DispatcherProvider
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {
    @ActivityContext
    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideTopHeadlineViewModel(topHeadlineRepository: TopHeadlineRepository): TopHeadlineViewModel {
        return ViewModelProvider(activity,
            ViewModelProviderFactory(TopHeadlineViewModel::class) {
                TopHeadlineViewModel(topHeadlineRepository)
            })[TopHeadlineViewModel::class.java]
    }


    @Provides
    fun provideTopHeadlinesPaginationViewModel(
        repository: TopHeadlinePagingRepository,
        dispatcherProvider: DispatcherProvider
    ): TopHeadlinePaginationViewModel {
        return ViewModelProvider(
            activity,
            ViewModelProviderFactory(TopHeadlinePaginationViewModel::class) {
                TopHeadlinePaginationViewModel(repository, dispatcherProvider)
            })[TopHeadlinePaginationViewModel::class.java]
    }


    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineAdapter(ArrayList())

    @Provides
    fun provideTopHeadlinesPaginationAdapter() = TopHeadlinePaginationAdapter()


}


