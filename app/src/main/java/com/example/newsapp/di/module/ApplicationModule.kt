package com.example.newsapp.di.module

import com.example.newsapp.NewsApplication
import dagger.Module

@Module
class ApplicationModule(private val application: NewsApplication) {
}