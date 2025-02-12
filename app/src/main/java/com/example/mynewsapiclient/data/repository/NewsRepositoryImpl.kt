package com.example.mynewsapiclient.data.repository

import com.example.mynewsapiclient.data.api.NewsAPIService
import com.example.mynewsapiclient.data.model.APIResponse
import com.example.mynewsapiclient.data.model.Article
import com.example.mynewsapiclient.data.repository.datasource.NewsLocalDatasource
import com.example.mynewsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.example.mynewsapiclient.data.util.Resource
import com.example.mynewsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDatasource: NewsLocalDatasource
) : NewsRepository{
    override suspend fun getNewsHeadlines(country : String , page : Int): Resource<APIResponse> {
        return responseTOResource(newsRemoteDataSource.getTopHeadlines(country,page))
    }

    override suspend fun getSearchedNews(
        country: String,
        page: Int,
        searchQuery: String
    ): Resource<APIResponse> {
        return responseTOResource(newsRemoteDataSource.getSearchedHeadlines(country,searchQuery,page))
    }


    private fun responseTOResource(response: Response<APIResponse>) : Resource<APIResponse>{
       if (response.isSuccessful){
           response.body()?.let {
               result ->
               return Resource.Success(result)
           }
       }
         val errormess = response.message()
        return Resource.Error(message = errormess)
    }


    override suspend fun saveNews(article: Article) {
        newsLocalDatasource.saveArticlestoDB(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDatasource.deleteArticlesfromDb(article)

    }

    override suspend fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDatasource.getArticlesFromDb()
    }
}