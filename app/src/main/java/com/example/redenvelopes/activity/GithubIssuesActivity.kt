package com.example.redenvelopes.activity

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.redenvelopes.R
import com.example.redenvelopes.base.CBaseActivity
import kotlinx.android.synthetic.main.activity_github_issues.*
import kotlinx.android.synthetic.main.include_title.*

class GithubIssuesActivity : CBaseActivity() {

    private val address = "https://github.com/gemgao/AndroidDemo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github_issues)
        tv_title.text ="Github意见反馈"
        ib_back.setOnClickListener {finish()}
        initSettings(webview)
        initWebView(webview)
        webview.loadUrl(address)
    }

    private fun initWebView(webView: WebView?) {
        if (webView != null) {
            webView.webViewClient = object : WebViewClient() {

                @SuppressWarnings("deprecation")
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    if (url != null) {
                        if (url.startsWith("http")) {
                            webView.loadUrl(url)
                        }
                    }
                    return true
                }

                @TargetApi(Build.VERSION_CODES.N)
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    if (request != null) {
                        val url = request.url.toString()
                        if (url.startsWith("http")) {
                            webView.loadUrl(url)
                        }
                    }
                    return true
                }
            }
        }
    }

    private fun initSettings(webview: WebView?) {
        val webSettings = webview?.settings
        if (webSettings != null) {
            webSettings.javaScriptEnabled = true
            webSettings.setSupportZoom(true)//是否可以缩放，默认false
            webSettings.builtInZoomControls = false//是否显示缩放按钮，默认false
            webSettings.useWideViewPort = true//大视图模式
            webSettings.setAppCacheEnabled(true)//是否使用缓存
            webSettings.domStorageEnabled = true
            webSettings.loadWithOverviewMode = true //是否自适应屏幕
        }
    }
}