package com.thepparat.newsclient.domain.usecase

import com.thepparat.newsclient.data.model.APIResponse
import com.thepparat.newsclient.data.util.Resource
import com.thepparat.newsclient.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country: String, page: Int): Resource<APIResponse> {
        return newsRepository.getNewsHeadlines(country, page)
    }
}