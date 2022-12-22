package com.thepparat.newsclient.data.repository.datasourceimpl

import com.thepparat.newsclient.data.db.ArticleDao
import com.thepparat.newsclient.data.model.Article
import com.thepparat.newsclient.data.repository.datasource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(private val dao: ArticleDao) : NewsLocalDataSource {
    override suspend fun saveArticleToDB(article: Article) {
        return dao.insert(article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return dao.getAllArticles()
    }

    override suspend fun deleteArticleFromDB(article: Article) {
        return dao.deleteArticles(article)
    }
}