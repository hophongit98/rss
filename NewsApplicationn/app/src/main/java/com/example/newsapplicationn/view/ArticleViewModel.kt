package com.example.newsapplicationn.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapplicationn.ArticleApplication
import com.example.newsapplicationn.model.RssArticle
import com.example.newsapplicationn.repository.ArticleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleViewModel : ViewModel() {

    companion object {
        const val RSS_URL = "https://vnexpress.net/rss/"
    }

    private val mNewsLiveData: MutableLiveData<UIWrapper> = MutableLiveData()
    val uiStateLiveData = mNewsLiveData

    fun fetchData() {
        emitState(isShowLoading = true)
        CoroutineScope(Dispatchers.IO).launch {
            val newsRepository = ArticleRepository.getInstance(ArticleApplication.instance)
            val items = newsRepository.fetchNews(RSS_URL)
            withContext(Dispatchers.IO) {
                emitState(items, false)
            }
        }
    }

    private fun emitState(listData: List<RssArticle>? = null, isShowLoading: Boolean? = null) {
        val uiWrapper = UIWrapper(listData, isShowLoading)
        mNewsLiveData.postValue(uiWrapper)
    }

    data class UIWrapper(
        val listData: List<RssArticle>?,
        val isShowLoading: Boolean?
    )
}