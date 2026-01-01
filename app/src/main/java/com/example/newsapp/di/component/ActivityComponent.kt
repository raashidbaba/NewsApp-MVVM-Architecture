package com.example.newsapp.di.component

import com.example.newsapp.di.ActivityScope
import com.example.newsapp.di.module.ActivityModule
import com.example.newsapp.ui.categorynews.CategoryDisplayActivity
import com.example.newsapp.ui.categorynews.CategoryNewsActivity
import com.example.newsapp.ui.countrynews.CountryDisplayActivity
import com.example.newsapp.ui.countrynews.CountryNewsActivity
import com.example.newsapp.ui.languageNews.LanguageDisplayActivity
import com.example.newsapp.ui.languageNews.LanguageNewsActivity
import com.example.newsapp.ui.topheadline.TopHeadlineActivity
import com.example.newsapp.ui.topheadlinepagination.TopHeadlinePaginationActivity
import dagger.Component


@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: TopHeadlineActivity)
    fun inject(activity: TopHeadlinePaginationActivity)
    fun inject(activity: CategoryDisplayActivity)
    fun inject(activity: CategoryNewsActivity)
    fun inject(activity: CountryDisplayActivity)
    fun inject(activity: CountryNewsActivity)
    fun inject(activity: LanguageDisplayActivity)
    fun inject(activity: LanguageNewsActivity)
}