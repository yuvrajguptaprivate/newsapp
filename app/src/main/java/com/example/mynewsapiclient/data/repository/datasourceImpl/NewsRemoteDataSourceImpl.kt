package com.example.mynewsapiclient.data.repository.datasourceImpl

import com.example.mynewsapiclient.data.api.NewsAPIService
import com.example.mynewsapiclient.data.model.APIResponse
import com.example.mynewsapiclient.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl (
    private val newsAPIService: NewsAPIService
): NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country : String, page : Int): Response<APIResponse> {
        return newsAPIService.getTopHeadlines(country,page)
    }

    override suspend fun getSearchedHeadlines(
        country: String,
        seachQuery: String,
        page: Int
    ): Response<APIResponse> {
        return newsAPIService.getSearchedHeadlines(country,seachQuery,page)
    }
}