package com.thepparat.newsclient.data.repository.datasource

import com.thepparat.newsclient.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse>
    suspend fun getSearchNews(searchQuery:String,country: String,page: Int): Response<APIResponse>
}