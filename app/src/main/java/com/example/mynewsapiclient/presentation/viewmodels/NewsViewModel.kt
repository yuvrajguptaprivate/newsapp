package com.example.mynewsapiclient.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.mynewsapiclient.data.model.APIResponse
import com.example.mynewsapiclient.data.model.Article
import com.example.mynewsapiclient.data.util.Resource
import com.example.mynewsapiclient.domain.usecases.DeleteSavedNewsUsecase
import com.example.mynewsapiclient.domain.usecases.GetNewsHeadlinesUsecase
import com.example.mynewsapiclient.domain.usecases.GetSavedNewsUsecase
import com.example.mynewsapiclient.domain.usecases.GetSearchedNewsUsecase
import com.example.mynewsapiclient.domain.usecases.SaveNewsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewsViewModel  @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getNewsHeadlinesUsecase: GetNewsHeadlinesUsecase,
    private val getSearchedNewsUsecase: GetSearchedNewsUsecase,
    private val saveNewsUsecase: SaveNewsUsecase,
    private val getSavedNewsUsecase: GetSavedNewsUsecase,
    private val deleteSavedNewsUsecase: DeleteSavedNewsUsecase
) : AndroidViewModel(context as Application) {
    val newsheadlines : MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsheadlines(country : String , page : Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (IsNetworkAvailable(context)){
                newsheadlines.postValue(Resource.Loading())
                val apiResult = getNewsHeadlinesUsecase.execute(country,page)
                newsheadlines.postValue(apiResult)
            } else{
                newsheadlines.postValue(Resource.Error(message = "Internet is not available"))
            }
        } catch (e:Exception){
            newsheadlines.postValue(Resource.Error( message = e.message.toString()))

        }


    }

    private fun IsNetworkAvailable(context: Context?): Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }


    //search

    val  searchedNews : MutableLiveData<Resource<APIResponse>> = MutableLiveData()
    fun seachNews(country: String,page: Int,searchedQuery : String)
    = viewModelScope.launch {
        searchedNews.postValue(Resource.Loading())
        try {
            if (IsNetworkAvailable(context)) {
                val response = getSearchedNewsUsecase.execute(country
                    ,page
                    ,searchedQuery)

                searchedNews.postValue(response)
            } else{
                searchedNews.postValue(Resource.Error(message = "No Internet"))
            }
        } catch (e:Exception) {
            searchedNews.postValue(Resource.Error(message = e.message.toString()))
        }

    }

    //local data

    fun saveArticle(article: Article) = viewModelScope.launch {
        saveNewsUsecase.execute(article)
    }

    fun getSavedNews() = liveData{
        getSavedNewsUsecase.execute().collect{
            emit(it)
        }
    }

    fun deleteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO){
        deleteSavedNewsUsecase.execute(article)
    }

}