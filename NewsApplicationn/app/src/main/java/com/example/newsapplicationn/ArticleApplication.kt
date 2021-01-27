package com.example.newsapplicationn

import android.app.Application

class ArticleApplication : Application() {

    companion object {
        lateinit var instance: ArticleApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}