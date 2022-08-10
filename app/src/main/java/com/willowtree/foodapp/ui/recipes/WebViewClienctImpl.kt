package com.willowtree.foodapp.ui.recipes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient


class WebViewClientImpl(activity: Activity?) : WebViewClient() {
    private var activity: Activity? = null

    override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
        //val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        //activity!!.startActivity(intent)
        return false
    }

    init {
        this.activity = activity
    }
}
