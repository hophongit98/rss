package com.example.newsapplicationn.repository

import android.content.Context
import com.example.newsapplicationn.model.RssArticle

class ArticleRepository(private val remote: ArticleRemoteDataSource) {
    companion object {
        private val TAG = ArticleRepository::class.java.simpleName

        @Volatile
        private var instance: ArticleRepository? = null

        fun getInstance(context: Context): ArticleRepository {
            instance?.let { return it }

            return synchronized(this) {
                var newInstance =
                    instance
                if (newInstance != null) {
                    newInstance
                } else {
                    newInstance = ArticleRepository(ArticleRemoteDataSource())
                    instance = newInstance
                    newInstance
                }
            }
        }
    }

    suspend fun fetchNews(url: String): List<RssArticle> {
        return remote.fetchNews(url)
    }
}