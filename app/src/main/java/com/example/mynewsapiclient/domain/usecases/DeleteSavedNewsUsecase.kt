package com.example.mynewsapiclient.domain.usecases

import com.example.mynewsapiclient.data.model.Article
import com.example.mynewsapiclient.domain.repository.NewsRepository

class DeleteSavedNewsUsecase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article) = newsRepository.deleteNews(article)
}