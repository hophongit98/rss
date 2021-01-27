package com.example.newsapplicationn.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name = "channel", strict = true)
data class RssChanel @JvmOverloads constructor(
    @field:Element(name = "title") var title: String = "",
    @field:Element(name = "description") var description: String = "",
    @field:Element(name = "image") var image: RssImage? = null,
    @field:Element(name = "pubDate") var pubDate: String = "",
    @field:Element(name = "generator") var generator: String = "",
    @field:Element(name = "link") var link: String = "",
    @field:ElementList(name="item", inline = true) var items: List<RssArticle> = arrayListOf()
)