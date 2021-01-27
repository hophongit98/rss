package com.example.newsapplicationn.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item")
data class RssArticle @JvmOverloads constructor(
    @field:Element(name = "title") var title: String = "",
    @field:Element(name = "description") var description: String = "",
    @field:Element(name = "pubDate") var pubDate: String = "",
    @field:Element(name = "link") var link: String = "",
    @field:Element(name = "guid") var guid: String = "",
    @field:Element(name = "comments") var comments: String = ""
)