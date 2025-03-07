package com.example.newsapp.di.component

import com.example.newsapp.NewsApplication
import com.example.newsapp.di.ActivityScope
import com.example.newsapp.di.module.ApplicationModule
import dagger.Component


@ActivityScope
@Component(modules = [ApplicationModule::class] )
interface ApplicationComponent {

    fun inject( application: NewsApplication)


    }
