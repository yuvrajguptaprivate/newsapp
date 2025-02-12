package com.example.mynewsapiclient.presentation.di

import com.example.mynewsapiclient.data.db.NewsDbDao
import com.example.mynewsapiclient.data.repository.datasource.NewsLocalDatasource
import com.example.mynewsapiclient.data.repository.datasourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun providesLocalDataSource(newsDbDao: NewsDbDao): NewsLocalDatasource{
        return NewsLocalDataSourceImpl(newsDbDao)
    }
}