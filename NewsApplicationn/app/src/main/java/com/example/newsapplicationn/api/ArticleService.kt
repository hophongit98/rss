package com.example.newsapplicationn.api

import com.example.newsapplicationn.model.RSSFeed
import retrofit2.Call
import retrofit2.http.GET

interface ArticleService {
    @GET("tin-moi-nhat.rss/")
    fun loadRSSFeed(): Call<RSSFeed>
}
