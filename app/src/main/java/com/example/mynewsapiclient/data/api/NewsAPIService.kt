package com.example.mynewsapiclient.data.api

import com.example.mynewsapiclient.BuildConfig
import com.example.mynewsapiclient.data.model.APIResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {


    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        country : String,
        @Query("page")
        page : Int,
        @Query("apiKey")
        apiKey : String = BuildConfig.API_Key
    ) : Response<APIResponse>



    @GET("v2/top-headlines")
    suspend fun getSearchedHeadlines(
        @Query("country")
        country : String,
        @Query("q")
        searchWuery : String,
        @Query("page")
        page : Int,
        @Query("apiKey")
        apiKey : String = BuildConfig.API_Key
    ) : Response<APIResponse>

}