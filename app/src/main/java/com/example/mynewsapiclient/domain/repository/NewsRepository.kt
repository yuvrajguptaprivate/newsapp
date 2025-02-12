package com.example.mynewsapiclient.domain.repository

import com.example.mynewsapiclient.data.model.APIResponse
import com.example.mynewsapiclient.data.model.Article
import com.example.mynewsapiclient.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {



    suspend fun getNewsHeadlines(country : String , page : Int) : Resource<APIResponse>

    suspend fun getSearchedNews(country : String , page : Int,searchQuery : String) : Resource<APIResponse>

    suspend fun saveNews(article: Article)

    suspend fun deleteNews(article: Article)

     suspend fun getSavedNews(): Flow<List<Article>>
}