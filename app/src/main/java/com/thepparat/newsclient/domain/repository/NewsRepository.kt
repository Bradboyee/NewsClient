package com.thepparat.newsclient.domain.repository

import com.thepparat.newsclient.data.model.APIResponse
import com.thepparat.newsclient.data.model.Article
import com.thepparat.newsclient.data.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface NewsRepository {
    suspend fun getNewsHeadlines(country: String, page: Int): Resource<APIResponse>
    suspend fun getSearchedNews(searchQuery:String,country: String,page: Int): Resource<APIResponse>
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>
}