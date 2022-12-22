package com.thepparat.newsclient.data.repository.datasourceimpl

import com.thepparat.newsclient.data.api.NewsAPIService
import com.thepparat.newsclient.data.model.APIResponse
import com.thepparat.newsclient.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService
) :
    NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse> {
        return newsAPIService.getTopHeadlines(country, page)
    }

    override suspend fun getSearchNews(
        searchQuery: String,
        country: String,
        page: Int
    ): Response<APIResponse> {
        return newsAPIService.getSearchNews(searchQuery, country, page)
    }
}