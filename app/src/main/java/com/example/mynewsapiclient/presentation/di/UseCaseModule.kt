package com.example.mynewsapiclient.presentation.di

import com.example.mynewsapiclient.domain.repository.NewsRepository
import com.example.mynewsapiclient.domain.usecases.DeleteSavedNewsUsecase
import com.example.mynewsapiclient.domain.usecases.GetNewsHeadlinesUsecase
import com.example.mynewsapiclient.domain.usecases.GetSavedNewsUsecase
import com.example.mynewsapiclient.domain.usecases.GetSearchedNewsUsecase
import com.example.mynewsapiclient.domain.usecases.SaveNewsUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {


    @Provides
    fun provideNewsHeadlineUseCAse(
        newsRepository: NewsRepository
    ) : GetNewsHeadlinesUsecase {
        return GetNewsHeadlinesUsecase(newsRepository)
    }

    @Provides
    fun providesGetSearchedewsuseCase(
        newsRepository: NewsRepository
    ) : GetSearchedNewsUsecase {
        return GetSearchedNewsUsecase(newsRepository)
    }

    @Provides
    fun providesSaveArticleUsecase(
        newsRepository: NewsRepository
    ): SaveNewsUsecase {
        return SaveNewsUsecase(newsRepository)
    }


    @Provides
    fun GetSaveArticleUsecase(
        newsRepository: NewsRepository
    ): GetSavedNewsUsecase {
        return GetSavedNewsUsecase(newsRepository)
    }

    @Provides
    fun ProvidesDeletesArticlesUsecase(
        newsRepository: NewsRepository
    ) : DeleteSavedNewsUsecase {
        return DeleteSavedNewsUsecase(newsRepository)
    }
}