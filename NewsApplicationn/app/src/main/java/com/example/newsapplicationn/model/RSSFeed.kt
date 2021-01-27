package com.example.newsapplicationn.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RSSFeed @JvmOverloads constructor(
    @field:Element(name = "channel") var channel: RssChanel? = null
)