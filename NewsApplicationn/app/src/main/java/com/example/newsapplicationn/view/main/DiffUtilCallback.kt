package com.example.newsapplicationn.view.main

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.example.newsapplicationn.model.RssArticle

class DiffUtilCallback(newList: ArrayList<RssArticle>, oldList: ArrayList<RssArticle>) : DiffUtil.Callback() {
    private val mNewList = arrayListOf<RssArticle>()
    private val mOldList = arrayListOf<RssArticle>()

    init {
        mNewList.clear()
        mNewList.addAll(newList)
        mOldList.clear()
        mOldList.addAll(oldList)
    }

    override fun getOldListSize(): Int {
        return mOldList.size
    }

    override fun getNewListSize(): Int {
        return mNewList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mNewList[newItemPosition].guid === mOldList[oldItemPosition].guid
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mNewList[newItemPosition] === mOldList[oldItemPosition]
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}