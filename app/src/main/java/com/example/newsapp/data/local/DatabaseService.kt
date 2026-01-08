package com.example.newsapp.data.local


import com.example.newsapp.data.local.entity.Source
import kotlinx.coroutines.flow.Flow

interface DatabaseService {

    fun getSources(): Flow<List<Source>>

    fun deleteAllAndInsertAll(sources: List<Source>)

}