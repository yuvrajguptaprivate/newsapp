package com.example.mynewsapiclient.presentation.di

import com.example.mynewsapiclient.presentation.adapters.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {


    @Singleton
    @Provides
    fun providesAdapter() : NewsAdapter{
        return NewsAdapter()
    }
}