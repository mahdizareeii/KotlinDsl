package com.webtoapp.app

import android.content.Context
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView

object WebViewHelper {
    fun WebView(
        context: Context,
        block: WebView.() -> Unit
    ): WebView = WebView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        block(this)
    }

    fun WebView.webSetting(block: WebSettings.() -> Unit) {
        this.settings.apply(block)
    }
}