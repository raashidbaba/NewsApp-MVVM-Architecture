package com.example.newsapp.di.module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.data.repository.CategoryNewsRepository
import com.example.newsapp.data.repository.CountryNewsRepository
import com.example.newsapp.data.repository.LanguageNewsRepository
import com.example.newsapp.data.repository.OfflineTopHeadlineRepository
import com.example.newsapp.data.repository.TopHeadlinePagingRepository
import com.example.newsapp.data.repository.TopHeadlineRepository
import com.example.newsapp.di.ActivityContext
import com.example.newsapp.ui.base.ViewModelProviderFactory
import com.example.newsapp.ui.categorynews.CategoryNewsAdapter
import com.example.newsapp.ui.categorynews.CategoryNewsViewModel
import com.example.newsapp.ui.countrynews.CountryNewsAdapter
import com.example.newsapp.ui.countrynews.CountryNewsViewModel
import com.example.newsapp.ui.languageNews.LanguageNewsAdapter
import com.example.newsapp.ui.languageNews.LanguageNewsViewModel
import com.example.newsapp.ui.offlineTopHeadlines.OfflineTopHeadlinesAdapter
import com.example.newsapp.ui.offlineTopHeadlines.OfflineTopHeadlinesViewModel
import com.example.newsapp.ui.topheadline.TopHeadlineSourcesAdapter
import com.example.newsapp.ui.topheadline.TopHeadlineViewModel
import com.example.newsapp.ui.topheadlinepagination.TopHeadlinePaginationAdapter
import com.example.newsapp.ui.topheadlinepagination.TopHeadlinePaginationViewModel
import com.example.newsapp.utils.DispatcherProvider
import com.example.newsapp.utils.NetworkHelper
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
        return ViewModelProvider(
            activity,
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
    fun provideCategoryNewsViewModel(
        repository: CategoryNewsRepository,
        dispatcherProvider: DispatcherProvider
    ): CategoryNewsViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(CategoryNewsViewModel::class) {
            CategoryNewsViewModel(repository, dispatcherProvider = dispatcherProvider)
        })[CategoryNewsViewModel::class.java]
    }

    @Provides
    fun provideCountryNewsViewModel(
        repository: CountryNewsRepository,
        dispatcherProvider: DispatcherProvider
    ): CountryNewsViewModel {
        return ViewModelProvider(activity, ViewModelProviderFactory(CountryNewsViewModel::class) {
            CountryNewsViewModel(repository, dispatcherProvider = dispatcherProvider)
        })[CountryNewsViewModel::class.java]
    }

    @Provides
    fun provideLanguageNewsViewModel(
        repository: LanguageNewsRepository,
        dispatcherProvider: DispatcherProvider
    ): LanguageNewsViewModel {
        return ViewModelProvider(
            activity,
            ViewModelProviderFactory(LanguageNewsViewModel::class)
            {
                LanguageNewsViewModel(repository, dispatcherProvider)
            })[LanguageNewsViewModel::class.java]
    }


    @Provides
    fun provideOfflineTopHeadlinesViewModel(
        networkHelper: NetworkHelper,
        dispatcherProvider: DispatcherProvider,
        repository: OfflineTopHeadlineRepository
    ): OfflineTopHeadlinesViewModel {
        return ViewModelProvider(
            activity,
            ViewModelProviderFactory(OfflineTopHeadlinesViewModel::class) {
                OfflineTopHeadlinesViewModel(
                    networkHelper = networkHelper,
                    dispatcherProvider = dispatcherProvider,
                    repository
                )
            })[OfflineTopHeadlinesViewModel::class.java]
    }


    @Provides
    fun provideTopHeadlineAdapter() = TopHeadlineSourcesAdapter(ArrayList())

    @Provides
    fun provideTopHeadlinesPaginationAdapter() = TopHeadlinePaginationAdapter()

    @Provides
    fun provideCategoryNewsAdapter() = CategoryNewsAdapter(ArrayList())

    @Provides
    fun provideCountryNewsAdapter() = CountryNewsAdapter(ArrayList())

    @Provides
    fun provideLanguageNewsAdapter() = LanguageNewsAdapter(ArrayList())

    @Provides
    fun provideOfflineTopHeadlinesAdapter() = OfflineTopHeadlinesAdapter(ArrayList())
}


