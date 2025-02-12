package com.example.mynewsapiclient.domain.usecases

import com.example.mynewsapiclient.data.model.Article
import com.example.mynewsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUsecase (private val newsRepository: NewsRepository){

    suspend fun execute(): Flow<List<Article>>{
        return newsRepository.getSavedNews()
    }

}