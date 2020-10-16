package br.com.rmso.mesanews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import br.com.rmso.mesanews.R

class DetailsActivity : AppCompatActivity() {
    private val webView: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val url = intent.getStringExtra("url")

        initWebView(url)
    }

    private fun initWebView(url: String?) {
        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
    }
}