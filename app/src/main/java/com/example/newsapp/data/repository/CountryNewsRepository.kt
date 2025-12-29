package com.example.newsapp.data.repository

import com.example.newsapp.data.model.ApiSource
import com.example.newsapp.data.model.Code
import com.example.newsapp.data.remote.NetworkService
import com.example.newsapp.di.ActivityScope
import com.example.newsapp.utils.AppConstant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject


@ActivityScope
class CountryNewsRepository @Inject constructor(private val networkService: NetworkService) {

    fun getCountriesList(): Flow<List<Code>> {
        return flow {
            emit(AppConstant.COUNTRIES)
        }
    }

    fun getCountriesNews(value: String): Flow<List<ApiSource>> {
        return flow {
            emit(networkService.getCountryBasedNews(value))
        }.map {
            it.sources
        }

    }

}