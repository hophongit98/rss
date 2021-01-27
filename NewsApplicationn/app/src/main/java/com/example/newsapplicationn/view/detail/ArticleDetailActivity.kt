package com.example.newsapplicationn.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.example.newsapplicationn.ArticleApplication
import com.example.newsapplicationn.R
import com.example.newsapplicationn.databinding.ActivityNewsDetailBinding
import com.example.newsapplicationn.utils.SharedPreferencesManager

class ArticleDetailActivity : AppCompatActivity() {

    companion object {
        private val TAG = ArticleDetailActivity::class.java.simpleName
        private const val URL_EXTRA = "URL_EXTRA"
        fun start(context: Context, url: String) {
            val intent = Intent(context, ArticleDetailActivity::class.java)
            intent.putExtra(URL_EXTRA, url)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra(URL_EXTRA)
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(ArticleApplication.instance, "Not found", Toast.LENGTH_SHORT).show()
            finish()
        }

        val webView = WebView(this)
        setContentView(webView)
        val isDarkMode = SharedPreferencesManager.getInstance().getDarkMode()
        if(isDarkMode && WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            WebSettingsCompat.setForceDark(webView.settings, WebSettingsCompat.FORCE_DARK_ON);
        }
        webView.loadUrl(url)
    }
}