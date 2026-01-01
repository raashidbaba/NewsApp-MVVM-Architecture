package com.example.newsapp.data.model

import com.google.gson.annotations.SerializedName

data class NewsSourcesResponse(
    @SerializedName("sources")
    val sources: List<ApiSource>,
    @SerializedName("status")
    val status: String
)