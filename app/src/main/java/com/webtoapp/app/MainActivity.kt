package com.webtoapp.app

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.webtoapp.app.SocialNetworkUrls.socialNetworks
import com.webtoapp.app.WebViewHelper.WebView
import com.webtoapp.app.WebViewHelper.webSetting


class MainActivity : AppCompatActivity() {

    private val webSiteUrl = "https://google.com"


    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            WebView(this) {
                keepScreenOn = true

                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            CookieManager.getInstance().flush()
                        } else {
                            CookieSyncManager.getInstance().sync()
                        }
                    }


                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        if (socialNetworks().any { it.contains(url.toString(), true) }) {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        } else {
                            return super.shouldOverrideUrlLoading(view, request)
                        }
                        return false
                    }
                }

                webSetting {
                    javaScriptEnabled = true
                    cacheMode = WebSettings.LOAD_DEFAULT
                    allowContentAccess = true
                    allowFileAccess = true
                    domStorageEnabled = true
                }

                setDownloadListener { url, userAgent, contentDisposition, mimeType, contentLength ->
                    startActivity(
                        Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(url)
                        }
                    )
                }

                loadUrl(webSiteUrl)
                webView = this
            }
        )
    }

    override fun onBackPressed() {
        if (webView.canGoBack())
            webView.goBack()
        else
            super.onBackPressed()
    }
}