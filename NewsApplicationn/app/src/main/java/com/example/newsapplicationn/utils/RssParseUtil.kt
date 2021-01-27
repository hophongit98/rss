package com.example.newsapplicationn.utils

import com.example.newsapplicationn.model.RSSFeed
import com.example.newsapplicationn.model.RssArticle
import java.util.*

object RssParseUtil {
    fun parseRSSFeedItems(rss: RSSFeed?): List<RssArticle> {
        val itemsList = ArrayList<RssArticle>()
        if (rss == null) return itemsList
        val channel = rss.channel ?: return itemsList
        itemsList.addAll(channel.items)
        return itemsList
    }
}