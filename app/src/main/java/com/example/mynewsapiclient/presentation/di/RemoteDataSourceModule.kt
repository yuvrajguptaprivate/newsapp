package com.example.mynewsapiclient.presentation.di

import com.example.mynewsapiclient.data.api.NewsAPIService
import com.example.mynewsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.example.mynewsapiclient.data.repository.datasourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {


    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(
        newsAPIService: NewsAPIService
    ) : NewsRemoteDataSource {
        return NewsRemoteDataSourceImpl(newsAPIService)
    }
}