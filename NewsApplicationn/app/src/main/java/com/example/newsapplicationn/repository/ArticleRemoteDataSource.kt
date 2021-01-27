package com.example.newsapplicationn.repository

import android.util.Log
import com.example.newsapplicationn.api.ArticleService
import com.example.newsapplicationn.model.RSSFeed
import com.example.newsapplicationn.model.RssArticle
import com.example.newsapplicationn.utils.RssParseUtil
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

class ArticleRemoteDataSource : ArticleDataSource {

    companion object {
        private val TAG = ArticleRemoteDataSource::class.java.simpleName
    }

    override suspend fun fetchNews(url: String): List<RssArticle> {
        return suspendCancellableCoroutine { cont ->
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(httpClient)
                .build()

            val articleService = retrofit.create(ArticleService::class.java)
            val call: Call<RSSFeed> = articleService.loadRSSFeed()
            call.enqueue(object : Callback<RSSFeed> {
                override fun onFailure(call: Call<RSSFeed>, t: Throwable) {
                    t.printStackTrace()
                    Log.d(TAG, "onFailure - t=${t.message}")
                    cont.resume(arrayListOf())
                }

                override fun onResponse(call: Call<RSSFeed>, response: Response<RSSFeed>) {
                    Log.d(TAG, "onResponse: ${response.isSuccessful}")
                    if (response.isSuccessful) {
                        val rss = response.body()
                        val items = RssParseUtil.parseRSSFeedItems(rss)
                        cont.resume(items)
                    } else {
                        Log.d(TAG, "error: ${response.errorBody()}")
                        cont.resume(arrayListOf())
                    }
                }
            })
        }
    }
}