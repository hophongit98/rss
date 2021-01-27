package com.example.newsapplicationn.view.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplicationn.R
import com.example.newsapplicationn.databinding.ActivityMainBinding
import com.example.newsapplicationn.model.RssArticle
import com.example.newsapplicationn.utils.SharedPreferencesManager
import com.example.newsapplicationn.utils.isDarkMode
import com.example.newsapplicationn.view.ArticleViewModel
import com.example.newsapplicationn.view.detail.ArticleDetailActivity
import kotlin.math.abs

class MainActivity : AppCompatActivity(), View.OnClickListener, View.OnTouchListener {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var mViewModel: ArticleViewModel
    private lateinit var mBinding : ActivityMainBinding
    private lateinit var mDetector: GestureDetector
    private var mTheme: Int = AppCompatDelegate.MODE_NIGHT_NO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        mBinding = binding
        initView()

        mViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        mViewModel.fetchData()
        observeData()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        mDetector = GestureDetector(this, GestureListener())
        mBinding.rlvNews.setOnTouchListener { _, event ->
            mDetector.onTouchEvent(event)
        }

        mBinding.swDarkmode.setOnClickListener(this)
        val isDarkMode = isDarkMode()
        mBinding.swDarkmode.isChecked = isDarkMode
        saveMode(isDarkMode)

    }

    private fun observeData() {
        mViewModel.uiStateLiveData.observe(this, Observer {
            Log.d(TAG, "isShowLoading=${it.isShowLoading}, listData=${it.listData?.size}")
            it.isShowLoading?.let { visible ->
                mBinding.pbLoading.visibility = if (visible) View.VISIBLE else View.GONE
            }

            it.listData?.let { data ->
                loadData(data)
            }
        })
    }

    private fun loadData(data: List<RssArticle>) {
        val newsAdapter = ArticleAdapter().apply {
            setData(data)

            setOnItemClickListener(object :
                ArticleAdapter.OnItemClickListener {
                override fun onItemClick(item: RssArticle) {
                    ArticleDetailActivity.start(this@MainActivity, item.link)
                }
            })
        }

        with(mBinding.rlvNews) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.swDarkmode -> {
                val isDarkMode = mBinding.swDarkmode.isChecked
                saveMode(isDarkMode)
                mTheme = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
                AppCompatDelegate.setDefaultNightMode(mTheme)
            }
        }
    }

    private fun saveMode(isDarkMode: Boolean) {
        SharedPreferencesManager.getInstance().setDarkMode(isDarkMode)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return mDetector.onTouchEvent(event)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return mDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            try {
                val delY = (e2?.y ?: 0f) - (e1?.y ?: 0f)
                val delV = abs(velocityY)
                if (delY > 100f && delV > 100f) {
                    mViewModel.fetchData()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "onFling - e=$e")
            }
            return false
        }
    }


}