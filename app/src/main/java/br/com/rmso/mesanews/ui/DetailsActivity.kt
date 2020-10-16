package br.com.rmso.mesanews.ui

import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.rmso.mesanews.R
import br.com.rmso.mesanews.model.New
import br.com.rmso.mesanews.ui.filter.FilterViewModel
import kotlinx.android.synthetic.main.activity_details.btn_favorite

class DetailsActivity : AppCompatActivity() {
    private val webView: WebView? = null
    private lateinit var filterViewModel: FilterViewModel
    private lateinit var title: String
    private lateinit var url: String
    private lateinit var description: String
    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        url  = intent.getStringExtra("url")
        title = intent.getStringExtra("title")
        description = intent.getStringExtra("description")
        imageUrl = intent.getStringExtra("image_url")

        initWebView(url)
        initFilterViewModel()

        btn_favorite.setOnClickListener{
            if (btn_favorite.text == resources.getText(R.string.btn_favor)){
                filterViewModel.insert(new = New(null, title, description, "", "", "", false, url, imageUrl))
                btn_favorite.text = resources.getText(R.string.btn_disfavor)
            }else {
                filterViewModel.delete(title)
                btn_favorite.text = resources.getText(R.string.btn_favor)
            }
        }
    }

    private fun initFilterViewModel() {
        filterViewModel = ViewModelProviders.of(this)
            .get(FilterViewModel::class.java)

        filterViewModel.search(title).observe(this, Observer { new ->
            new?.let {
                if (new.title == title){
                    btn_favorite.text = resources.getText(R.string.btn_disfavor)
                }else {
                    btn_favorite.text = resources.getText(R.string.btn_favor)
                }
            }
        })
    }

    private fun initWebView(url: String?) {
        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
    }
}