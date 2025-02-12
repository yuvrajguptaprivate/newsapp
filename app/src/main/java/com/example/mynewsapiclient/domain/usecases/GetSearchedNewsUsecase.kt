package com.example.mynewsapiclient.domain.usecases

import com.example.mynewsapiclient.data.model.APIResponse
import com.example.mynewsapiclient.data.util.Resource
import com.example.mynewsapiclient.domain.repository.NewsRepository

class GetSearchedNewsUsecase( private val newsRepository: NewsRepository) {

    suspend fun execute(country : String , page : Int,searchQuery : String) : Resource<APIResponse>{
        return newsRepository.getSearchedNews(country,page,searchQuery)
    }
}