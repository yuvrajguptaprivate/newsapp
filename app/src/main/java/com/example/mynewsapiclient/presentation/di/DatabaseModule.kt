package com.example.mynewsapiclient.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.mynewsapiclient.data.db.ArticleDatabase
import com.example.mynewsapiclient.data.db.NewsDbDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesNewsDatabase(app : Application): ArticleDatabase{
        return Room.databaseBuilder(app,ArticleDatabase::class.java,"news_db")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun providesNewsDao(articleDatabase: ArticleDatabase) : NewsDbDao{
        return articleDatabase.getArticle()
    }

}