package com.example.newsapplicationn.utils

import android.content.Context
import android.content.res.Configuration

fun Context.isDarkMode() : Boolean {
    val mode = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return mode == Configuration.UI_MODE_NIGHT_YES
}