package com.inu.appcenter.inuit

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton

class WebViewActivity : AppCompatActivity() {

    lateinit var mWebView : WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val url = intent.getStringExtra("URL_STRING")

        val closeButton = findViewById<ImageButton>(R.id.ibtn_close_webview)
        closeButton.setOnClickListener {
            finish()
        }

        mWebView = findViewById(R.id.form_webview)
        mWebView.apply {
            webViewClient = MyWebViewClient()
            settings.javaScriptEnabled = true
        }
        mWebView.loadUrl(url!!)
    }

    override fun onBackPressed() {
        if (mWebView.canGoBack())
            mWebView.goBack()
        else
            finish()
    }

    companion object{
        fun newIntent(context:Context, url:String): Intent{
            return Intent(context,WebViewActivity::class.java).apply {
                putExtra("URL_STRING",url)
            }
        }
    }

    inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if(url != null){
                if (url.startsWith("intent://")) {
                    val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                    if (intent != null) {
                        val fallbackUrl = intent.getStringExtra("browser_fallback_url")
                        return if (fallbackUrl != null) {
                            val fallBackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(fallbackUrl))
                            startActivity(fallBackIntent)
                            finish()
                            //view?.loadUrl(fallbackUrl)
                            true
                        } else {
                            false
                        }
                    }
                }
            }
            return false
        }
    }
}