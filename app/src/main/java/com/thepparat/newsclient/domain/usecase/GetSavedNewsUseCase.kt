package com.thepparat.newsclient.domain.usecase

import com.thepparat.newsclient.data.model.Article
import com.thepparat.newsclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {
    fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNews()
    }
}