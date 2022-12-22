package com.thepparat.newsclient.presentation.di

import com.thepparat.newsclient.data.db.ArticleDao
import com.thepparat.newsclient.data.repository.datasource.NewsLocalDataSource
import com.thepparat.newsclient.data.repository.datasourceimpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Provides
    @Singleton
    fun provideNewsLocalDataSource(articleDao: ArticleDao): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(articleDao)

    }
}