package com.thepparat.newsclient.domain.usecase

import android.app.Application
import androidx.room.Room
import com.thepparat.newsclient.data.db.ArticleDao
import com.thepparat.newsclient.data.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideArticleDataBase(application: Application): ArticleDatabase {
        return Room.databaseBuilder(application,ArticleDatabase::class.java,"article_db").fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideNewDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.articleDao()
    }
}