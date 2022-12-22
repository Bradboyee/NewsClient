package com.thepparat.newsclient.domain.usecase

import com.thepparat.newsclient.data.model.APIResponse
import com.thepparat.newsclient.data.util.Resource
import com.thepparat.newsclient.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(
        searchQuery: String,
        country: String,
        page: Int
    ): Resource<APIResponse> {
        return newsRepository.getSearchedNews(searchQuery, country, page);
    }
}