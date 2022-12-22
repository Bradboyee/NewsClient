package com.thepparat.newsclient.presentation.di

import com.thepparat.newsclient.data.repository.NewsRepositoryImpl
import com.thepparat.newsclient.data.repository.datasource.NewsLocalDataSource
import com.thepparat.newsclient.data.repository.datasource.NewsRemoteDataSource
import com.thepparat.newsclient.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }
}