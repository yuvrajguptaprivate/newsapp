package com.example.mynewsapiclient.domain.usecases

import com.example.mynewsapiclient.data.model.APIResponse
import com.example.mynewsapiclient.data.util.Resource
import com.example.mynewsapiclient.domain.repository.NewsRepository

class GetNewsHeadlinesUsecase(private val newsRepository: NewsRepository) {

    suspend fun execute(country : String , page : Int): Resource<APIResponse>{
        return newsRepository.getNewsHeadlines(country , page)
    }
}