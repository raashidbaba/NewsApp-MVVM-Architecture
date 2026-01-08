package com.example.newsapp.data.local

import com.example.newsapp.data.local.entity.Source
import kotlinx.coroutines.flow.Flow

class AppDatabaseService(private val appDatabase: AppDatabase) : DatabaseService {

    override fun getSources(): Flow<List<Source>> {
        return appDatabase.sourceDao().getAll()
    }

    override fun deleteAllAndInsertAll(sources: List<Source>) {
        appDatabase.sourceDao().deleteAllAndInsertAll(sources)
    }

}