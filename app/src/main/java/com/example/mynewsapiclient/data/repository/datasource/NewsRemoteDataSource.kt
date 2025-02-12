package com.example.mynewsapiclient.data.repository.datasource

import com.example.mynewsapiclient.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {

    suspend fun getTopHeadlines(country : String, page : Int): Response<APIResponse>

    suspend fun getSearchedHeadlines(country : String,seachQuery: String, page : Int): Response<APIResponse>

}