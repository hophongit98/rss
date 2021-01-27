package com.example.newsapplicationn.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "image", strict = false)
data class RssImage @JvmOverloads constructor(
    @field:Element(name = "url") var url: String = "",
    @field:Element(name = "title") var title: String = "",
    @field:Element(name = "link") var link: String = ""
)