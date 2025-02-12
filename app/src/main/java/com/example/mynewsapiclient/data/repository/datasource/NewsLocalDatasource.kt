package com.example.mynewsapiclient.data.repository.datasource

import com.example.mynewsapiclient.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDatasource {

    suspend fun saveArticlestoDB(article: Article)


    suspend fun getArticlesFromDb(): Flow<List<Article>>

    suspend fun deleteArticlesfromDb(article: Article)
}