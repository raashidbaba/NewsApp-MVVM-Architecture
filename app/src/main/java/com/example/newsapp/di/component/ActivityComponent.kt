package com.example.newsapp.di.component

import com.example.newsapp.di.ActivityScope
import com.example.newsapp.di.module.ActivityModule
import com.example.newsapp.ui.topheadline.TopHeadlineActivity
import dagger.Component


@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: TopHeadlineActivity)

}