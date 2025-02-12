package com.example.mynewsapiclient.presentation.di

import com.example.mynewsapiclient.data.repository.NewsRepositoryImpl
import com.example.mynewsapiclient.data.repository.datasource.NewsLocalDatasource
import com.example.mynewsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.example.mynewsapiclient.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {



    @Provides
    fun providesNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDatasource: NewsLocalDatasource
    ) : NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource,newsLocalDatasource)
    }


}