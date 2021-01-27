package com.example.newsapplicationn.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.newsapplicationn.ArticleApplication

class SharedPreferencesManager(context: Context) {
    companion object {
        private const val PREFERENCE_NIGHT_MODE = "PREFERENCE_NIGHT_MODE"
        private const val KEY_NIGHT_MODE = "KEY_NIGHT_MODE"


        private var mInstance: SharedPreferencesManager? = null
        fun getInstance(): SharedPreferencesManager {
            if (mInstance == null) {
                mInstance = SharedPreferencesManager(ArticleApplication.instance)
            }
            return mInstance!!
        }
    }

    private val mModeSharedPreferences: SharedPreferences = context.getSharedPreferences(PREFERENCE_NIGHT_MODE, Context.MODE_PRIVATE)


    fun setDarkMode(value: Boolean) {
        mModeSharedPreferences.edit().putBoolean(KEY_NIGHT_MODE, value).apply()
    }

    fun getDarkMode(): Boolean {
        return mModeSharedPreferences.getBoolean(KEY_NIGHT_MODE, false)
    }
}