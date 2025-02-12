package com.example.mynewsapiclient.data.repository.datasourceImpl

import com.example.mynewsapiclient.data.db.NewsDbDao
import com.example.mynewsapiclient.data.model.Article
import com.example.mynewsapiclient.data.repository.datasource.NewsLocalDatasource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(
    private val articalDao: NewsDbDao
) : NewsLocalDatasource {
    override suspend fun saveArticlestoDB(article: Article) {
        articalDao.insert(article)
    }

    override suspend fun getArticlesFromDb(): Flow<List<Article>> {
        return articalDao.getAllArticles()
    }

    override suspend fun deleteArticlesfromDb(article: Article) {
        articalDao.deleteArticle(article)
    }
}