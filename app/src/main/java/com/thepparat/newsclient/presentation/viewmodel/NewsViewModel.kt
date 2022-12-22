package com.thepparat.newsclient.presentation.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.thepparat.newsclient.data.model.APIResponse
import com.thepparat.newsclient.data.model.Article
import com.thepparat.newsclient.data.util.Resource
import com.thepparat.newsclient.domain.usecase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val savedNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deletedSavedNewsUseCase: DeletedSavedNewsUseCase
) : AndroidViewModel(app) {
    val newsHeadlines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadlines(country: String, page: Int) {
        newsHeadlines.postValue(Resource.Loading())

        try {
            if (isInternetAvailable(app)) {
                viewModelScope.launch(Dispatchers.IO) {
                    val apiResult = getNewsHeadlinesUseCase.execute(country, page)
                    newsHeadlines.postValue(apiResult)
                }
            } else {
                newsHeadlines.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            newsHeadlines.postValue(Resource.Error(e.message.toString()))
        }

    }

    @SuppressLint("ObsoleteSdkInt")
    @Suppress("DEPRECATION")
    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }

    //search
    val searchNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun searchNews(searchQuery: String, country: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            searchNews.postValue(Resource.Loading())
            try {
                if (isInternetAvailable(app)) {
                    val response = getSearchedNewsUseCase.execute(searchQuery, country, page)
                    searchNews.postValue(response)
                } else {
                    searchNews.postValue(Resource.Error("No internet connection"))
                }
            } catch (e: Exception) {
                newsHeadlines.postValue(Resource.Error(e.message.toString()))
            }
        }
    }

    //local data
    fun saveArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            savedNewsUseCase.execute(article)
        }
    }

    //get all articles

    fun getSavedNews() = liveData {
        getSavedNewsUseCase.execute().collect {
            emit(it)
        }
    }

    //delete
    fun deleteArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            deletedSavedNewsUseCase.execute(article)
        }
    }


}