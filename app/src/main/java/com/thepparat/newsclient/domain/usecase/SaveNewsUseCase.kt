package com.thepparat.newsclient.domain.usecase

import com.thepparat.newsclient.data.model.Article
import com.thepparat.newsclient.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article){
        return newsRepository.saveNews(article)
    }
}